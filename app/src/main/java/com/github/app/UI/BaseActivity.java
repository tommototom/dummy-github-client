package com.github.app.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.github.app.db.RealmDao;
import com.github.app.db.RealmDaoImpl;

public class BaseActivity extends AppCompatActivity {

    private RealmDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = new RealmDaoImpl(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        dao.close();
    }

    protected RealmDao dao() {
        return dao;
    }
}
