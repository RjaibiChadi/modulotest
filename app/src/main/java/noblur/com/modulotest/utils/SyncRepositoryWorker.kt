package noblur.com.modulotest.utils

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import noblur.com.modulotest.data.source.local.ModuloDatabase
import noblur.com.modulotest.data.source.remote.ModuloService
import noblur.com.modulotest.data.source.remote.RetrofitClient
import retrofit2.HttpException


class SyncRepositoryWorker (appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams)  {

    internal lateinit var jsonApi:ModuloService
    internal  var compositeDisposable: CompositeDisposable? = null
    lateinit var appExecutors: AppExecutors

    val database = ModuloDatabase.getInstance(appContext)
    private val userDao = database.userDao()
    private val deviceDao  = database.deviceDao()




    override fun doWork(): Result {

        val retrofit = RetrofitClient.instance
        compositeDisposable = CompositeDisposable()
        appExecutors = AppExecutors()
        jsonApi = retrofit.create(ModuloService::class.java)


        compositeDisposable?.add(jsonApi.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { data->displayData(data)},
                { error->dislayError(error)}
            )
        )

        return Result.success()
    }

    private fun displayData(data: noblur.com.modulotest.data.entity.Data?) {



        appExecutors.diskIO.execute {
            data?.let {
                userDao.deleteAllUsers()
                userDao.insertUser(data.user)
                deviceDao.deleteAllDevices()
                deviceDao.insertDevices(data.devices)
            }
        }

    }


    private fun dislayError(error: Throwable?) {

        Log.i("MYAPP", "Error while doing something", error)

        if (error is HttpException){

            val httpException = error as HttpException
            if(httpException.code() == 400)
                Log.d("MYAPP", "onError: BAD REQUEST")
            else if(httpException.code() == 401)
                Log.d("MYAPP", "onError: NOT AUTHORIZED")
            else if(httpException.code() == 403)
                Log.d("MYAPP", "onError: FORBIDDEN")
            else if(httpException.code() == 404)
                Log.d("MYAPP", "onError: NOT FOUND")
            else if(httpException.code() == 500)
                Log.d("MYAPP", "onError: INTERNAL SERVER ERROR")
            else if(httpException.code() == 502)
                Log.d("MYAPP", "onError: BAD GATEWAY")

        }

    }



}