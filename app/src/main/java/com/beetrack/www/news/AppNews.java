package com.beetrack.www.news;

import android.app.Application;

import com.beetrack.www.news.BuildConfig;
import com.beetrack.www.news.models.DaoMaster;
import com.beetrack.www.news.models.DaoSession;

import org.greenrobot.greendao.database.Database;

public class AppNews extends Application {

    public static final boolean ENCRYPTED = BuildConfig.DEBUG;

    private DaoSession daoSession;

    private static final String DB_ENCRYPTED_NAME = "db-encrypted-".concat(BuildConfig.NAME_DB);
    private static final String DB_NAME = "db-".concat(BuildConfig.NAME_DB);

    private static final String PASSWORD = BuildConfig.PASSWORD_DB;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper( this, ENCRYPTED ? DB_ENCRYPTED_NAME:DB_NAME);
        Database database = ENCRYPTED ? helper.getEncryptedReadableDb(PASSWORD):helper.getWritableDb();
        daoSession = new DaoMaster(database).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
