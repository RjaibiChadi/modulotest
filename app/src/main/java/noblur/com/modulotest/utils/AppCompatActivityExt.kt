package noblur.com.modulotest.utils

import android.app.Activity

import androidx.annotation.IdRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import noblur.com.modulotest.ViewModelFactory

const val ADD_EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 1
const val DELETE_RESULT_OK = Activity.RESULT_FIRST_USER + 2
const val EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 3

fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, frameId: Int){

    supportFragmentManager.transact {

        replace(frameId,fragment)

    }
}

fun AppCompatActivity.setupActionBar(@IdRes toolbarId: Int, action: ActionBar.() -> Unit) {
    setSupportActionBar(findViewById(toolbarId))
    supportActionBar?.run {
        action()
    }
}


fun <T : ViewModel> AppCompatActivity.obtainViewModel(viewModelClass: Class<T>) =
            ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(viewModelClass)

private inline fun FragmentManager.transact(function: FragmentTransaction.() -> Unit) {

    beginTransaction().apply {
            function()
    }.commit()

}
