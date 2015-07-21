package com.github.app.db;

import android.content.Context;
import android.util.Log;
import com.github.app.App;
import com.github.app.model.Commit;
import com.github.app.model.Repository;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

import java.util.ArrayList;
import java.util.List;


public class RealmDaoImpl implements RealmDao{

    private Realm mRealmInstance;

    public RealmDaoImpl(Context context) {
        this.mRealmInstance = Realm.getInstance(context);
    }

    /**
     * implements create or update action to given entity
     * @param entity either RealmObject or List<RealmObject>
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
    public <E> void delete(E entity) {
        realm().beginTransaction();

        if (entity instanceof Iterable) {
            deleteAll((Iterable) entity);
        } else {
            deleteOne(entity);
        }

        realm().commitTransaction();
    }

    private <E> void deleteOne(E entity) {
        if (entity instanceof RealmObject) {
            realm().copyToRealmOrUpdate((RealmObject) entity);
        } else {
            throw new IllegalArgumentException("Entity must extend RealmObject");
        }
    }

    @Override
    public void deletePage(Class<? extends RealmObject> modelClass, int page) {
        realm().beginTransaction();
        realm().where(modelClass).equalTo("pageNum", page)
                .findAll()
                .clear();

        realm().commitTransaction();
    }

    public void deleteAll(Iterable entities) {
        throw new IllegalStateException("Stub");
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

    //todo replace with smth common for entity classes
    @Override
    public <E> void saveAsPageable(E entities, int pageNum) {
        // assign page number to objects of applicable types
        if (entities instanceof Iterable) {
            Iterable it = (Iterable) entities;
            for (Object e : it) {
                if (e.getClass().equals(Repository.class)) {
                    Repository repo = (Repository) e;
                    repo.setPageNum(pageNum);
                } else if (e.getClass().equals(Commit.class)) {
                    Commit c = (Commit) e;
                    c.setPageNum(pageNum);
                }
            }
        }

        save(entities);
    }

    @Override
    public List<Repository> findReposAtPage(int page) {
        return realm()
                .where(Repository.class)
                .equalTo("pageNum", page)
                .findAll();
    }

    @Override
    public List<Commit> findCommitsAtPage(int page, String repoName) {
        return realm()
                .where(Commit.class)
                .equalTo("pageNum", page)
                .equalTo("repoName", repoName)
                .findAll();
    }

    @Override
    public void close() {
        realm().close();
    }

    private Realm realm() {
        return mRealmInstance;
    }
}
