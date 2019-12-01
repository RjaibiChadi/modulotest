package noblur.com.modulotest.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.content.Context
import java.util.*
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.fragment.app.FragmentActivity
import java.sql.Timestamp
import java.text.SimpleDateFormat


class Util {

    companion object {
        const val BASE_URL = "http://storage42.com/modulotest/"


        fun currentDate(): Date {
            val calendar = Calendar.getInstance()
            return calendar.time
        }


        fun hideKeyBoard(ctx: FragmentActivity?) {

            val imm = ctx?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view = ctx.currentFocus
            if (view == null) {
                view = View(ctx)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)

        }

         fun changeFormatDate(birthday:Double):String{

            val stamp = Timestamp(birthday.toLong())
            val date = java.sql.Date(stamp.getTime())

            val pattern = "dd/MM/yyyy"
            val simpleDateFormat = SimpleDateFormat(pattern)
            return simpleDateFormat.format(date)

        }


        fun isConnectToNetwork(context: Context): Boolean {
            val info = getNetworkInfo(context)

            return info != null && info.isConnected
        }

        /**
         * Get the network info.
         *
         * @param context The current Activity Context
         * @return network info.
         */
        fun getNetworkInfo(context: Context): NetworkInfo? {
            val cm = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            return cm.activeNetworkInfo
        }

    }


}