package com.onnisoft.wahoo.api.mappers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.onnisoft.api.utils.security.dto.GenericResponseDTO;
import com.onnisoft.wahoo.model.dao.Dao;

public abstract class AbstractWahooObjectMapper<T> implements WahooObjectMapper<T> {

	protected static final Logger logger = LoggerFactory.getLogger(AbstractWahooObjectMapper.class);

	private Class<T> clasz;

	protected AbstractWahooObjectMapper(Class<T> clasz) {
		this.clasz = clasz;
	}

	@Autowired
	private MappingJsonFactory mappingJsonFactory;

	@Override
	public List<T> processList(GenericResponseDTO<List<Object>> list) {
		return this.processList(list, mapperFunction(), clasz);
	}

	@Override
	public abstract T objectBuilder(String t, JsonParser jp);

	private List<T> processList(GenericResponseDTO<List<Object>> list, Function<String, T> function, Class<T> clasz) {
		List<Object> response = list.getData();
		List<T> result = new ArrayList<>();

		if (CollectionUtils.isEmpty(response)) {
			logger.warn("There is no data for processing!");
			return result;
		}
		result = response.stream().map(o -> function.apply(stringReplace(o.toString()))).filter(Objects::nonNull).collect(Collectors.toList());

		return result;
	}

	private Function<String, T> mapperFunction() {
		Function<String, T> function = new Function<String, T>() {

			@Override
			public T apply(String t) {
				JsonParser jp = null;
				try {
					jp = mappingJsonFactory.createParser(t);
				} catch (JsonParseException e) {
					logger.error("Couldn't create parser for object:" + t + " error:" + e.getMessage());
					return null;
				} catch (IOException e) {
					logger.error("Couldn't create parser for object:" + t + " error:" + e.getMessage());
					return null;
				}

				T result = objectBuilder(t, jp);

				try {
					jp.close();
				} catch (IOException e) {
					logger.error("Couldn't close the parser for object:" + t + " error:" + e.getMessage());
				}

				return result;
			}

		};
		return function;
	}

	@Override
	public List<T> saveMappedObjects(List<T> list, Dao<T> dao) {
		List<T> result = new ArrayList<>();
		if (CollectionUtils.isEmpty(list)) {
			logger.warn("There is no " + clasz + " to be displayed");
			return null;
		}
		result = list.stream().map(t -> saveMappedObject(t, dao)).filter(Objects::nonNull).collect(Collectors.toList());

		return result;
	}

	@Override
	public T saveMappedObject(T t, Dao<T> dao) {
		T result = null;
		try {
			T dbEntity = dao.retrieveById((String) t.getClass().getMethod("getId").invoke(t));
			if (dbEntity != null) {
				if (!dbEntity.equals(t)) {
					boolean updated = dao.update(t);
					if (!updated) {
						logger.error("An error occurred while updating the object:" + t.toString() + " of class:" + clasz);
					} else {
						result = dbEntity;
					}
				} else {
					result = dbEntity;
				}
			} else {
				dbEntity = dao.create(t);
				if (dbEntity == null) {
					logger.warn("An error occurred while saving the object:" + t.toString() + " of class:" + clasz);
				} else {
					result = dbEntity;
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	private String stringReplace(String toReplace) {
		return toReplace.replace("{", "{\"").replace("=", "\":\"").replace(", ", "\", \"").replace("}", "\"}").replace("[{", "{").replace("}]", "}")
				.replace(":\"{", ":{").replace("}\"", "}").replaceAll("\"null\"", "null");
	}
}
