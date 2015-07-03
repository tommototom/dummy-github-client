package com.github.app.db;

import android.util.Log;
import com.github.app.App;
import com.github.app.model.Commit;
import io.realm.Realm;
import io.realm.RealmObject;


public class RealmDaoImpl implements RealmDao{

    /**
     * implements create or update action to given entity
     * @param entity either RealmObject or List<RealmObject></RealmObject>
     */
    @Override
    public void save(Object entity) {
        realm().beginTransaction();

        try {
            if (entity instanceof Iterable) {
                realm().copyToRealmOrUpdate((Iterable) entity);
            } else if (entity instanceof RealmObject) {
                realm().copyToRealmOrUpdate((RealmObject) entity);
            } else {
                throw new IllegalArgumentException("Entity must extend RealmObject");
            }
        } catch (ClassCastException e) {
            Log.e("Dao", "Error casting entity with trace: ");
            e.printStackTrace();
        } finally {
            realm().commitTransaction();
        }
    }

    @Override
    public <E extends RealmObject> void delete(E entity) {
        entity.removeFromRealm();
    }

    /**
     * searches for occurrence by id
     * @param modelClass table to search
     * @param id
     * todo remove hardcode
     */
    public RealmObject findById(Class<? extends RealmObject> modelClass, Object id) {
        if (Commit.class == modelClass) { // key is string
            return realm().where(modelClass).equalTo("sha", (String) id).findFirst();
        } else {
            return realm().where(modelClass).equalTo("id", (Long) id).findFirst();
        }
    }

    private static Realm realm() {
        return App.getRealmInstance();
    }
}
