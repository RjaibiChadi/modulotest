package noblur.com.modulotest.profilcomponent

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import noblur.com.modulotest.data.entity.Event
import noblur.com.modulotest.data.repository.User
import noblur.com.modulotest.data.repository.UserDataSource
import noblur.com.modulotest.data.repository.UserRepository

class ProfilViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _loadUser = MutableLiveData<User>()
    val loadUser: LiveData<User>
        get() = _loadUser

    private val _newProfilUpdatePage = MutableLiveData<Event<Unit>>()
    val newProfilUpdatePage: LiveData<Event<Unit>>
        get() = _newProfilUpdatePage

    fun start() {

        loadUser()
    }

    private fun loadUser() {

        userRepository.getUser(object :UserDataSource.GetUserCallback{
            override fun onUserLoaded(user: User) {

                _loadUser.postValue(user)
            }

            override fun onDataNotAvailable(code: Int) {


            }


        })
    }

    fun updateProfil() {

        _newProfilUpdatePage.value = Event(Unit)
    }
}
