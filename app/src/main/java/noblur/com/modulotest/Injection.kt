package noblur.com.modulotest

import android.content.Context
import io.reactivex.disposables.CompositeDisposable
import noblur.com.modulotest.data.repository.DeviceRepository
import noblur.com.modulotest.data.repository.UserRepository
import noblur.com.modulotest.data.source.local.ModuloDatabase
import noblur.com.modulotest.data.source.local.deviceLocal.DeviceLocalDataSource
import noblur.com.modulotest.data.source.local.userLocal.UserLocalDataSource
import noblur.com.modulotest.data.source.remote.DeviceRemoteDataSource
import noblur.com.modulotest.data.source.remote.ModuloService
import noblur.com.modulotest.data.source.remote.RetrofitClient
import noblur.com.modulotest.data.source.remote.UserRemoteDataSource
import noblur.com.modulotest.utils.AppExecutors

object Injection {



    fun provideDeviceRepository(context: Context): DeviceRepository {
        val database = ModuloDatabase.getInstance(context)
        val api = RetrofitClient.instance.create(ModuloService::class.java)

        return DeviceRepository.getInstance(
            DeviceRemoteDataSource.getInstance(CompositeDisposable(),api),
            DeviceLocalDataSource.getInstance(AppExecutors(), database.deviceDao()))
    }

    fun provideUserRepository(context: Context): UserRepository {
        val database = ModuloDatabase.getInstance(context)
        val api = RetrofitClient.instance.create(ModuloService::class.java)

        return UserRepository.getInstance(
            UserRemoteDataSource.getInstance(CompositeDisposable(),api),
            UserLocalDataSource.getInstance(AppExecutors(), database.userDao()))
    }

}