package noblur.com.modulotest.homecomponent

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import noblur.com.modulotest.data.entity.Event
import noblur.com.modulotest.data.repository.Device
import noblur.com.modulotest.data.repository.DeviceDataSource
import noblur.com.modulotest.data.repository.DeviceRepository

class HomeViewModel(

    private val deviceRepository: DeviceRepository

): ViewModel() {

    private val _devices = MutableLiveData<List<Device>>().apply { value = emptyList() }
    val devices: LiveData<List<Device>>
        get() = _devices

    private val _newProfilPage = MutableLiveData<Event<Unit>>()
    val newProfilPage: LiveData<Event<Unit>>
        get() = _newProfilPage

    private val _openControlEvent = MutableLiveData<Event<String>>()
    val openControlEvent: LiveData<Event<String>>
        get() = _openControlEvent

    fun start() {

        loadDevices()
    }


    private fun loadDevices() {

        deviceRepository.getDevices(object :DeviceDataSource.LoadDevicesCallback{
            override fun onDevicesLoaded(devices: List<Device>) {

                _devices.postValue(devices)


            }

            override fun onDataNotAvailable(code: Int) {


            }


        })

    }

    fun viewProfil() {

        _newProfilPage.value = Event(Unit)


    }

    fun showControl(deviceId: Int) {

        _openControlEvent.value = Event(deviceId.toString())

    }
}
