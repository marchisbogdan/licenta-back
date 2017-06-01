package com.onnisoft.wahoo.api.jms;

import java.util.List;

public interface Consumer<T, C> {

	T createConsumer(List<String> destinations, String groupsId);

	List<Object> receive();

	void process(List<Object> values);

	void setMessageListener(Listener listener);

	void close();

}
