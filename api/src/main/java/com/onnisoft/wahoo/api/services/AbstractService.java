package com.onnisoft.wahoo.api.services;

public abstract class AbstractService<T> extends GenericServices<T> {

	abstract void inactivateById(String id);
}
