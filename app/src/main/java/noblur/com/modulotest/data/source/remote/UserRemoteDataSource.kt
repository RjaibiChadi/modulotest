package noblur.com.modulotest.data.source.remote

import androidx.annotation.VisibleForTesting
import io.reactivex.disposables.CompositeDisposable
import noblur.com.modulotest.data.repository.User
import noblur.com.modulotest.data.repository.UserDataSource


class UserRemoteDataSource(
    val compositeDisposable: CompositeDisposable,
    val api: ModuloService
) : UserDataSource {

    override fun getUser(callback: UserDataSource.GetUserCallback) {


    }

    override fun registerUser(user: User) {


    }

    override fun deleteAllUser() {


    }


    companion object {
        private var INSTANCE: UserRemoteDataSource? = null

        @JvmStatic
        fun getInstance(
            compositeDisposable: CompositeDisposable,
            api: ModuloService
        ): UserRemoteDataSource {
            if (INSTANCE == null) {
                synchronized(UserRemoteDataSource::javaClass) {
                    INSTANCE = UserRemoteDataSource(compositeDisposable,api)
                }
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }
    }
}