package com.rms.supply.database

import org.greenrobot.greendao.identityscope.IdentityScopeType
import android.content.Context
import com.leaf.common.util.XLog
import com.rms.supply.api.base.MyApp
import org.greenrobot.greendao.query.QueryBuilder

class DataBaseUtils private constructor() {
//    var daoSession: DaoSession? = null
//
//    val userDao: UserDao
//        get() = getAndIntDaoSession().userDao
//
//    companion object {
//        private var instance: DataBaseUtils? = null
//            get() {
//                if (field == null) {
//                    field = DataBaseUtils()
//                }
//                return field
//            }
//
//        @Synchronized
//        fun get(): DataBaseUtils {
//            return instance!!
//        }
//    }
//
//
//    fun getAndIntDaoSession(): DaoSession {
//        if (daoSession == null) {
//            if (!UserCacheExt.getDbName().isNullOrEmpty()) {
//                setupDatabase(MyApp.instance)
//            }
//        }
//        return daoSession!!
//    }
//
//    /**
//     * 配置数据库
//     */
//    fun setupDatabase(context: Context?) {
//        try {
//            //创建数据库shop.db
//            val helper = MyGreenDaoDbHelper(context, "oceanshipping_${UserCacheExt.getDbName()}.db", null)
//            //获取数据库对象
//            val daoMaster = DaoMaster(helper.writableDatabase)
//            QueryBuilder.LOG_SQL = true
//            QueryBuilder.LOG_VALUES = true
//            //获取dao对象管理者
//            daoSession = daoMaster.newSession(IdentityScopeType.None)
//        } catch (e: Exception) {
//            daoSession = null
//            e.printStackTrace()
//            XLog.d("greendao 创建失败")
//        }
//    }
//
//    fun clear() {
//        try {
//            daoSession?.clear()
//            daoSession?.database?.close()
//            daoSession = null
//        } catch (e: Exception) {
//            e.printStackTrace()
//            XLog.d("greendao 清除失败")
//        }
//    }


}