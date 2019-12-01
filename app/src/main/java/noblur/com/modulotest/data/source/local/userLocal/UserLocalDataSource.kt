package noblur.com.modulotest.data.source.local.userLocal


import androidx.annotation.VisibleForTesting
import noblur.com.modulotest.data.repository.User
import noblur.com.modulotest.data.repository.UserDataSource
import noblur.com.modulotest.utils.AppExecutors

class UserLocalDataSource(
    val appExecutors: AppExecutors,
    val userDao: UserDao
): UserDataSource {

    override fun getUser(callback: UserDataSource.GetUserCallback) {

        appExecutors.diskIO.execute {

            val user = userDao.getUser()


            appExecutors.diskIO.execute {
                if (user!=null){
                    callback.onUserLoaded(user)

                }else{
                    callback.onDataNotAvailable(500)

                }

            }
        }
    }

    override fun registerUser(user: User) {

        appExecutors.diskIO.execute { userDao.insertUser(user) }

    }


    override fun deleteAllUser() {

        appExecutors.diskIO.execute { userDao.deleteAllUsers() }

    }


    companion object {
  private var INSTANCE: UserLocalDataSource? = null

       @JvmStatic
       fun getInstance(appExecutors: AppExecutors, userDao: UserDao): UserLocalDataSource {
        if (INSTANCE == null) {
         synchronized(UserLocalDataSource::javaClass) {
          INSTANCE =
              UserLocalDataSource(appExecutors, userDao)
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