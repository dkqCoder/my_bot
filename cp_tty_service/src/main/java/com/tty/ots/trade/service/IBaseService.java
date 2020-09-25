package com.tty.ots.trade.service;


import java.io.Serializable;
import java.util.List;

public interface IBaseService<T> {
    void save(T o);

    void update(T o);

    void saveOrUpdate(T o);

    void merge(T o);

    void delete(T o);

    List<T> find(String hql, List<Object> param);

    List<T> find(String hql, Object... param);

    List<T> findPageByListParam(String hql, int page, int rows, List<Object> param);

    List<T> findPage(String hql, int page, int rows, Object... param);

    T get(Class<T> c, Serializable id);

    T get(String hql, Object... param);

    T get(String hql, List<Object> param);

    T load(Class<T> c, Serializable id);

    Long count(String hql, Object... param);

    Long count(String hql, List<Object> param);

    Integer executeHql(String hql);

    Integer executeHql(String hql, Object... param);
}
