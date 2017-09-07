package com.dao;

public interface BaseDao<T1> {

	Integer add(T1 obj);
	void update(T1 obj);
	void delete(T1 obj);
}
