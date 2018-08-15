package com.filemanager.lib.bean;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by CWJ on 2017/3/20.
 */

public class FileDao {
    private static FileDao INSTANCE;
    private DaoSession daoSession;

    public static FileDao getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new FileDao(context);
        }
        return INSTANCE;
    }

    private FileDao(Context context) {
        //创建数据库shop.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context.getApplicationContext(), "file.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    /**
     * 添加数据，如果有重复则覆盖
     */
    public void insertFile(FileInfo fileInfo) {
        daoSession.getFileInfoDao().insertOrReplace(fileInfo);
    }

    /**
     * 删除数据
     */
    public void deleteFile(FileInfo fileInfo) {
        daoSession.getFileInfoDao().delete(fileInfo);
    }

    /**
     * 更新数据
     */
    public void updateFile(FileInfo fileInfo) {
        daoSession.getFileInfoDao().update(fileInfo);
    }


    /**
     * 查询全部数据
     */
    public List<FileInfo> queryAll() {
        return daoSession.getFileInfoDao().loadAll();

    }

    /**
     * 删除全部数据
     */
    public void deleteAll1() {
        daoSession.getFileInfoDao().deleteAll();
    }

    public boolean isContain(String ID) {
        QueryBuilder<FileInfo> qb = daoSession.getFileInfoDao().queryBuilder();
        qb.where(FileInfoDao.Properties.FileName.eq(ID));
        qb.buildCount().count();
        return qb.buildCount().count() > 0 ? true : false;
    }
}
