package com.onnisoft.wahoo.model.dao;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.skip;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public abstract class AbstractDao<T> implements Dao<T> {

	protected static final Logger logger = LoggerFactory.getLogger(AbstractDao.class);

	@Autowired
	private MongoTemplate mongoTemplate;

	protected MongoOperations mongoOperation;

	@PostConstruct
	private void init() {
		this.mongoOperation = mongoTemplate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.onnisoft.wahoo.model.dao.Dao#retrieve(java.lang.Object)
	 */
	protected T retrieve(T t, Class<T> clasz) {
		Query query = this.createSearchQuery(t);

		T savedDocument = this.mongoOperation.findOne(query, clasz);
		if (savedDocument == null) {
			logger.warn("No document was found in the retrieve process." + savedDocument);
		}

		return savedDocument;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.onnisoft.ssp.dao.Dao#retrieveById(java.lang.String)
	 */
	protected T retrieveById(String id, Class<T> clasz) {
		return this.mongoOperation.findById(id, clasz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.onnisoft.ssp.model.dao.Dao#retrieve(java.lang.Object)
	 */
	protected List<T> retrieveList(T t, Class<T> clasz) {
		List<T> savedDocuments = null;
		if (t != null) {
			Query query = this.createSearchQuery(t);
			savedDocuments = this.mongoOperation.find(query, clasz);
		} else {
			savedDocuments = this.mongoOperation.findAll(clasz);
		}
		if (savedDocuments == null || savedDocuments.isEmpty()) {
			logger.warn("No document were found in the retrieve process." + savedDocuments);
		}
		return savedDocuments;
	}

	@Override
	public List<T> retrieveSortedListBYInterval(Sort.Direction sortDirection, String sortParam, int index, int amount, Class<T> clasz) {
		List<T> documents = null;

		AggregationOperation sort = sort(sortDirection, sortParam);
		AggregationOperation skip = skip((index - 1) * amount);
		AggregationOperation limit = limit(amount);

		TypedAggregation<T> typedAggregation = newAggregation(clasz, sort, skip, limit);

		AggregationResults<T> results = mongoOperation.aggregate(typedAggregation, clasz);

		documents = results.getMappedResults();

		if (CollectionUtils.isEmpty(documents)) {
			logger.warn("No document were found in the retrieve process.");
		}

		return documents;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.onnisoft.ssp.dao.Dao#create(java.lang.Object)
	 */
	@Override
	public T create(T t) {
		this.mongoOperation.save(t);
		return this.retrieve(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.onnisoft.ssp.model.dao.Dao#update(java.lang.Object)
	 */
	protected boolean update(T t, Criteria criteria, Class<T> clasz) {
		Query query = new Query(criteria);
		return this.mongoOperation.updateFirst(query, this.createUpdateQuery(t), clasz).isUpdateOfExisting();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.onnisoft.ssp.model.dao.Dao#delete(java.lang.Object)
	 */
	protected boolean delete(Query query, Class<T> clasz) {
		return this.mongoOperation.remove(query, clasz).wasAcknowledged();
	}

	/**
	 * Creates query for fetching a document based on one of it's properties.
	 * 
	 * @param t
	 *            - the document from where we extract the search query
	 * @return the query object.
	 */
	protected abstract Query createSearchQuery(T t);

	/**
	 * Creates query for fetching a document based on one of it's properties.
	 * 
	 * @param t
	 *            - the document from where we extract the update query
	 *            properties.
	 * @return the update object.
	 */
	protected abstract Update createUpdateQuery(T t);

	/**
	 * retrieve a list of objects by the creation and update date and the
	 * properties found in the T object
	 * 
	 * @param creationStartDate
	 * @param creationEndDate
	 * @param updateStartDate
	 * @param updateEndDate
	 * @param t
	 * @param clasz
	 * @return
	 */
	@Override
	public List<T> retrieveByDates(Date creationStartDate, Date creationEndDate, Date updateStartDate, Date updateEndDate, T t, Class<T> clasz) {
		List<T> documents = null;
		Query query = new Query();

		if (t != null) {
			query = this.createSearchQuery(t);
		}
		if (creationEndDate != null && creationStartDate != null) {
			query.addCriteria(Criteria.where("creationDate").lte(creationEndDate).gte(creationStartDate));
		} else if (creationStartDate != null) {
			query.addCriteria(Criteria.where("creationDate").gte(creationStartDate));
		}
		if (updateEndDate != null && updateStartDate != null) {
			query.addCriteria(Criteria.where("updateDate").lte(updateEndDate).gte(updateStartDate));
		} else if (updateStartDate != null) {
			query.addCriteria(Criteria.where("updateDate").gte(updateStartDate));
		}

		documents = this.mongoOperation.find(query, clasz);
		return documents;
	}

	/**
	 * Retrieve a list of T objects which correspond to the interval set for the
	 * specified field.
	 * 
	 * @param startDate
	 * @param endDate
	 * @param field
	 * @param clasz
	 * @return
	 */
	@Override
	public List<T> processByDateInterval(Date startDate, Date endDate, String field, Class<T> clasz) {
		List<T> documents = new ArrayList<>();

		Query query = new Query();

		if (startDate != null && endDate != null) {
			query.addCriteria(Criteria.where(field).gte(startDate).lte(endDate));
		} else if (startDate != null) {
			query.addCriteria(Criteria.where(field).gte(startDate));
		} else if (endDate != null) {
			query.addCriteria(Criteria.where(field).lte(endDate));
		} else {
			return null;
		}
		documents = this.mongoOperation.find(query, clasz);
		return documents;
	}

	/**
	 * Retrieve a list of T objects which match the provided regex for the
	 * specified field
	 * 
	 * @param expression
	 * @param field
	 * @return
	 */
	@Override
	public List<T> retireveByRegex(String expression, String field, Class<T> clasz) {
		List<T> documents = new ArrayList<>();

		if (!StringUtils.isEmpty(expression) && !StringUtils.isEmpty(field)) {
			Query query = new Query();
			String regexp = ".*" + expression + ".*";
			Pattern pattern = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);

			query.addCriteria(Criteria.where(field).regex(pattern));
			documents = this.mongoOperation.find(query, clasz);
		}

		if (CollectionUtils.isEmpty(documents)) {
			logger.warn("No real competitors were found in the searching process.");
		}

		return documents;
	}
}
