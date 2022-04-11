package com.leaf.common.util

import java.io.File
import java.text.DecimalFormat

/**
 * @Author: zgYi
 * @CreateDate: 2021/12/30
 * @Description:
 */
class FileMathUtils {
    companion object {
        fun formatByteSizeWithUnit(fileS: Long): String? { //转换文件大小
            if (fileS == 0L) {
                return "0B"
            }
            val df = DecimalFormat("#.00")
            var fileSizeString = ""
            fileSizeString = if (fileS < 1024) {
                subZeroAndDot(df.format(fileS.toDouble())) + "B"
            } else if (fileS < 1048576) {
                subZeroAndDot(df.format(fileS.toDouble() / 1024)) + "K"
            } else if (fileS < 1073741824) {
                subZeroAndDot(df.format(fileS.toDouble() / 1048576)) + "M"
            } else if (fileS < 1099511627776L) {
                subZeroAndDot(df.format(fileS.toDouble() / 1073741824)) + "G"
            } else {
                subZeroAndDot(df.format(fileS.toDouble() / 1099511627776L)) + "T"
            }
            return fileSizeString
        }


        /**
         * 使用java正则表达式去掉多余的.与0
         *
         * @param s
         * @return
         */
        fun subZeroAndDot(s: String): String? {
            var s = s
            if (s.indexOf(".") > 0) {
                s = s.replace("0+?$".toRegex(), "") //去掉多余的0
                s = s.replace("[.]$".toRegex(), "") //如最后一位是.则去掉
            }
            return s
        }

        /**
         *
         */
        fun getFileDirectorySize(file: String?): Long {
            //创建文件对象
            val f = File(file)
            return if (f.exists() && f.isDirectory()) { //文件夹存在
                //获取文件夹的文件的集合
                val files: Array<File> = f.listFiles()
                var count: Long = 0 //用来保存文件的长度
                for (file1 in files) { //遍历文件集合
                    if (file1.isFile()) { //如果是文件
                        count += file1.length() //计算文件的长度
                    } else {
                        count += getFileDirectorySize(file1.toString()) //递归调用
                    }
                }
                count
            } else {
                XLog.d("查询的文件夹有误")
                0
            }
        }
    }




}