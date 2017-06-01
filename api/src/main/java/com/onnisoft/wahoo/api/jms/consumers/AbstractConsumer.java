package com.onnisoft.wahoo.api.jms.consumers;

import com.onnisoft.wahoo.api.jms.Consumer;
import com.onnisoft.wahoo.api.mappers.WahooObjectMapper;
import com.onnisoft.wahoo.model.dao.Dao;

public abstract class AbstractConsumer<T, C> implements Consumer<T, C> {

	protected WahooObjectMapper<C> mapper;

	protected Dao<C> dao;

}
