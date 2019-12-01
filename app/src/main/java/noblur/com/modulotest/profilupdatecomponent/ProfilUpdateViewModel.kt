package noblur.com.modulotest.profilupdatecomponent

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import noblur.com.modulotest.data.entity.Address
import noblur.com.modulotest.data.entity.Event
import noblur.com.modulotest.data.repository.User
import noblur.com.modulotest.data.repository.UserDataSource
import noblur.com.modulotest.data.repository.UserRepository
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ProfilUpdateViewModel(

    private val userRepository: UserRepository
) : ViewModel() {
    private val _loadUser = MutableLiveData<User>()
    val loadUser: LiveData<User>
        get() = _loadUser

    private val _profilUpdated = MutableLiveData<Event<Unit>>()
    val profilUpdated: LiveData<Event<Unit>>
        get() = _profilUpdated

    fun start() {

        loadUser()
    }

    private fun loadUser() {

        userRepository.getUser(object : UserDataSource.GetUserCallback{
            override fun onUserLoaded(user: User) {

                _loadUser.postValue(user)
            }

            override fun onDataNotAvailable(code: Int) {


            }


        })
    }


    fun updateProfil(
        last_name: String,
        first_name: String,
        birthday: String,
        street_code: String,
        street: String,
        city: String,
        postal_code: String,
        country: String
    ) {

        val _birthday = SimpleDateFormat("dd/MM/yyyy").parse(birthday)
        val adress = Address(city,postal_code.toInt(),street,street_code,country)

        val user = User(0,first_name,last_name,_birthday.time.toDouble(),adress)

        userRepository.registerUser(user)
        _profilUpdated.value = Event(Unit)


    }

}
