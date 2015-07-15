package com.github.app.db;

import io.realm.RealmObject;

public interface RealmDao {

    void save(Object entity);

    <E> void delete(E entity);

    void deletePage(Class<? extends RealmObject> modelClass, int page);

    RealmObject findById(Class<? extends RealmObject> modelClass, Object id);

    <E> void saveAsPageable(E entities, int pageNum);

}
