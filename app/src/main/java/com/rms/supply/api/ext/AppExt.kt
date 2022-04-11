package com.rms.supply.api.ext

import android.app.Activity
import android.app.ActivityManager
import android.content.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.blankj.utilcode.util.SDCardUtils
import com.leaf.common.base.MyKtx
import com.leaf.common.ext.lifecycle.KtxActivityManger
import com.rms.supply.api.base.MyApp
import com.rms.supply.util.MiuiUtils
import java.io.*
import kotlin.system.exitProcess


fun isAppRunning(packageName: String): Boolean {
    val am = MyApp.instance.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val list = am.getRunningTasks(100)
    if (list.size <= 0) {
        return false
    }
    for (info in list) {
        if (info.baseActivity!!.packageName == packageName) {
            return true
        }
    }
    return false
}

fun FragmentActivity.showNoPremissDialog() {
    showCommonDialog(msg = "无权限，请到设置中开启权限！", isSingleButton = true, sureClick = {
        MiuiUtils.jumpToPermissionsEditorActivity(this)
        this.finishAfterTransition()
    }).show()
}

fun Fragment.showNoPremissDialog() {
    try {
        this.activity?.showCommonDialog(msg = "无权限，请到设置中开启权限！", isSingleButton = true, sureClick = {
            MiuiUtils.jumpToPermissionsEditorActivity(this.activity)
            this.activity?.finishAfterTransition()
        })?.show()
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}


fun <T> List<T>.findWithIndex(func: (t: T) -> Boolean): Int {
    var findIndex = -1
    run foo@{
        forEachIndexed { index, item ->
            if (func.invoke(item)) {
                findIndex = index
                return@foo
            }
        }
    }
    return findIndex
}


fun <T> List<T>.findWithAll(func: (t: T) -> Boolean): List<T> {
    var list = arrayListOf<T>()
    run foo@{
        forEachIndexed { index, item ->
            if (func.invoke(item)) {
                list.add(item)
            }
        }
    }
    return list
}




fun resString(resId: Int): String {
    return MyKtx.app.resources.getString(resId)
}

fun resColor(resId: Int): Int {
    return MyKtx.app.resources.getColor(resId)
}

/**
 * 判断是否有足够容量
 */
fun hasEnoughSDCardSize(file: String?): Boolean {
    val sdSize = SDCardUtils.getInternalAvailableSize()
    if (file == null) {
        return true
    }
    try {
        val newfile = File(file)
        return newfile.length() <= sdSize
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return true
}


/**
 * 退出应用程序
 */
fun appExit(context: Context) {
    try {
        KtxActivityManger.finishAllActivity()
        val manager = context.applicationContext
            .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        manager.killBackgroundProcesses(context.packageName)
        exitProcess(0)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}



/**
 * 复制内容到剪贴板
 *
 * @param content
 * @param context
 */
fun copyContentToClipboard(content: String?, succCall: (succ: Boolean) -> Unit) {
    try {
        //获取剪贴板管理器：
        val cm: ClipboardManager = MyApp.instance.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        // 创建普通字符型ClipData
        val mClipData = ClipData.newPlainText("FSR", content)
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData)
        succCall.invoke(true)
    } catch (e: Exception) {
        e.printStackTrace()
        succCall.invoke(false)
    }
}


fun Activity.buildSerialParams(cls: Class<*>, data: Serializable?): Intent {
    val intent = Intent(this, cls)
    data?.let {
        intent.putExtra("data", data)
    }
    return intent
}

fun Fragment.setSerialParams(data: Serializable?): Fragment {
    data?.let {
        val bundle = Bundle()
        bundle.putSerializable("data", data)
        this.arguments = bundle
    }
    return this
}




fun getJsonFromResource(fileName: String): String {
    //将json数据变成字符串
    val stringBuilder = StringBuilder()
    try {
        //获取assets资源管理器
        val assetManager = MyApp.instance.assets
        //通过管理器打开文件并读取
        val bf = BufferedReader(
            InputStreamReader(
                assetManager.open(fileName)
            )
        )
        var line: String?
        while (bf.readLine().also { line = it } != null) {
            stringBuilder.append(line)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return stringBuilder.toString()
}


fun Activity.onAsyncCallInMainUi(func: () -> Unit) {
    if (!this.isFinishing && !this.isDestroyed) {
        runOnUiThread {
            func.invoke()
        }
    }
}
