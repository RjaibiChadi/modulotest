package noblur.com.modulotest.data.repository


class UserRepository(
    val userRemoteDataSource: UserDataSource,
    val userLocalDataSource: UserDataSource
): UserDataSource {


    override fun getUser(callback: UserDataSource.GetUserCallback) {

        userLocalDataSource.getUser(object:UserDataSource.GetUserCallback{
            override fun onUserLoaded(user: User) {

                callback.onUserLoaded(user)
            }

            override fun onDataNotAvailable(code: Int) {

                callback.onDataNotAvailable(code)
            }


        })

    }

    override fun registerUser(user: User) {

        userLocalDataSource.deleteAllUser()
        userLocalDataSource.registerUser(user)

    }

    override fun deleteAllUser() {


    }


    companion object {

        private var INSTANCE: UserRepository? = null


        @JvmStatic fun getInstance(userRemoteDataSource: UserDataSource,
                                   userLocalDataSource: UserDataSource
        ) =
            INSTANCE
                ?: synchronized(UserRepository::class.java) {
                INSTANCE
                    ?: UserRepository(
                        userRemoteDataSource,
                        userLocalDataSource
                    )
                    .also { INSTANCE = it }
            }

        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }
    }
}