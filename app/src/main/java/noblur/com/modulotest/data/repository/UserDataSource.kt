package noblur.com.modulotest.data.repository


interface UserDataSource {



    interface GetUserCallback {

        fun onUserLoaded(user: User)

        fun onDataNotAvailable(code:Int)
    }

    fun getUser(callback: GetUserCallback)

    fun registerUser(user: User)

    fun deleteAllUser()

}