package com.github.app.db;

import io.realm.RealmObject;

public interface RealmDao {

    void save(Object entity);

    <E extends RealmObject> void delete(E entity);

    RealmObject findById(Class<? extends RealmObject> modelClass, Object id);
}
