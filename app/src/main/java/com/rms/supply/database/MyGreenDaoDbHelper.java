package com.rms.supply.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.leaf.common.util.XLog;
import com.rms.supply.database.bean.DaoMaster;

import org.greenrobot.greendao.database.Database;


/**
 * Created by WJY.
 * Date: 2020/8/4
 * Time: 14:00
 * Description:
 */
public class MyGreenDaoDbHelper extends DaoMaster.DevOpenHelper {

    public MyGreenDaoDbHelper(Context context, String name) {
        super(context, name);
    }

    public MyGreenDaoDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }


    @Override
    @SuppressWarnings("all")
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        if (oldVersion < newVersion) {
            XLog.INSTANCE.e("MyGreenDaoDbHelper", "进行数据库升级");
            new GreenDaoCompatibleUpdateHelper()
                    .setCallBack(
                            new GreenDaoCompatibleUpdateHelper.GreenDaoCompatibleUpdateCallBack() {

                                @Override
                                public void onFinalSuccess() {
                                    XLog.INSTANCE.e("MyGreenDaoDbHelper", "进行数据库升级 ===> 成功");
                                }

                                @Override
                                public void onFailedLog(String errorMsg) {
                                    XLog.INSTANCE.e("MyGreenDaoDbHelper", "升级失败日志 ===> " + errorMsg);
                                }
                            }
                    )
                    .compatibleUpdate(
                            db);
            XLog.INSTANCE.e("MyGreenDaoDbHelper", "进行数据库升级--完成");
        }
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        // 不要调用父类的，它默认是先删除全部表再创建
        // super.onUpgrade(db, oldVersion, newVersion);
    }
}
