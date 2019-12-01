package noblur.com.modulotest.controlcomponent

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import noblur.com.modulotest.data.repository.Device
import noblur.com.modulotest.data.repository.DeviceDataSource
import noblur.com.modulotest.data.repository.DeviceRepository
import noblur.com.modulotest.data.repository.User
import noblur.com.modulotest.utils.ProductType

class ControlViewModel(

    private val deviceRepository: DeviceRepository

) : ViewModel() {

    private val _loadDevice = MutableLiveData<Device>()
    val loadUser: LiveData<Device>
        get() = _loadDevice

    private var _device:Device?=null

    fun start(deviceId: String?) {

        deviceRepository.getDevice(deviceId!!.toInt(),object :DeviceDataSource.GetDeviceCallback{
            override fun onDeviceLoaded(device: Device) {

                _device = device
                _loadDevice.postValue(device)

            }

            override fun onDataNotAvailable() {

            }

        })

    }

    fun updateSwithMode(updateSwtchMode: String?) {

        if (_device!=null) {
            _device!!.mode = updateSwtchMode
            deviceRepository.updateDevice(_device!!)
        }
    }

    fun updateSeekBar(seekBarValue: String) {
        if (_device!=null) {

            when (_device!!.productType){
                ProductType.Light.description->_device!!.intensity=seekBarValue.toInt()
                ProductType.Heater.description->_device!!.temperature=seekBarValue.toInt()
                ProductType.RollerShutter.description->_device!!.position=seekBarValue.toInt()
            }

            deviceRepository.updateDevice(_device!!)
        }

    }
}
