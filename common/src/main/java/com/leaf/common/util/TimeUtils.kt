package com.leaf.common.util

import android.util.Log
import java.math.BigDecimal
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Author: zgYi
 * @CreateDate: 2021/12/28
 * @Description:
 */
class TimeUtils {

    companion object {

        /**
         * 分秒转分
         */
        fun minutesToMinute(second: Long): Long {
            if (second == 0L) {
                return 0L
            }
            val result = BigDecimal(second).divide(BigDecimal(1000L))
                .divide(BigDecimal(60), BigDecimal.ROUND_HALF_UP).toLong()
            if (result == 0L) {
                return 1L
            }
            return result
        }

        fun minutesToHHAndMM(minuteOrg: Long): Array<Long> {
            var hour = 0L
            var minute = 0L
            if (minuteOrg != 0L) {
                if (minuteOrg > 60) {
                    hour = minuteOrg / 60
                    minute = minuteOrg % 60
                } else {
                    minute = minuteOrg
                }
            }

            return arrayOf(hour, minute)
        }

        fun secondToMMAndSS(secondOrg: Long): Array<Long> {
            var second = 0L
            var minute = 0L
            if (secondOrg != 0L) {
                if (secondOrg > 60) {
                    minute = secondOrg / 60
                    second = secondOrg % 60
                } else {
                    second = secondOrg
                }
            }

            return arrayOf(minute, second)
        }


        fun millsSecondToYYYYMMDD(mills: Long?): String {
            if (mills == null || 0L == mills) {
                return ""
            }
            val date = Date(mills)
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm")
            return format.format(date)
        }


        /**
         * 日期格式转换yyyy-MM-dd'T'HH:mm:ss.SSSXXX  (yyyy-MM-dd'T'HH:mm:ss.SSSZ) TO  yyyy-MM-dd HH:mm:ss
         *
         * @throws ParseException
         */
        fun dealTDateFormat(oldDateStr: String?): String? {
            if (oldDateStr.isNullOrEmpty()) {
                return ""
            }
            val df1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            var date1: Date? = null
            date1 = try {
                df1.parse(oldDateStr)
            } catch (e: ParseException) {
                e.printStackTrace()
                return ""
            }
            val df2: DateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm")
            return df2.format(date1)
        }


        fun timeStringCovertToDate(str: String?): Date? {
            if (str.isNullOrEmpty()) {
                return null
            }
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            try {
                val date = format.parse(str)
                return date
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return null
        }

        fun timeStringCovertToDateWitheZone(str: String?): Date? {
            if (str.isNullOrEmpty()) {
                return null
            }
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            format.timeZone = TimeZone.getTimeZone("GMT+:00:00")
            try {
                val date = format.parse(str)
                return date
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return null
        }

        fun timeStringCovertToDateWithZone(str: String?): Date? {
            if (str.isNullOrEmpty()) {
                return null
            }
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            format.timeZone = TimeZone.getTimeZone("GMT+:00:00")
            try {
                val date: Date = format.parse(str)
                return date
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return null
        }


        fun millsToCalendar(time: Long): Calendar {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = time
            return calendar
        }


        /**
         * 判断后一位日期是否大于前一位
         */
        fun sameDayCompare(date: Date?, big: Date?): Int {
            if (date == null && big == null) {
                return 0
            }
            if (date == null) {
                return 1
            }
            if (big == null) {
                return -1
            }

            val cal1 = Calendar.getInstance()
            cal1.time = date
            val cal2 = Calendar.getInstance()
            cal2.time = big
            val day1 = cal1[Calendar.DAY_OF_YEAR]
            val day2 = cal2[Calendar.DAY_OF_YEAR]

            val year1 = cal1[Calendar.YEAR]
            val year2 = cal2[Calendar.YEAR]
            return if (year1 != year2) {  //同一年
                var timeDistance = 0
                for (i in year1 until year2) {
                    timeDistance += if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {  //闰年
                        366
                    } else {  //不是闰年
                        365
                    }
                }
                timeDistance + (day2 - day1)
            } else { //不同年
                day2 - day1
            }
        }

    }
}