
package noblur.com.modulotest.data.repository

import android.location.Location
import noblur.com.modulotest.data.entity.Data


interface DeviceDataSource {

    interface LoadDevicesCallback {

        fun onDevicesLoaded(devices: List<Device>)

        fun onDataNotAvailable(code:Int)
    }

    interface LoadDataCallback {

        fun onDataLoaded(data: Data)

        fun onDataNotAvailable(code:Int)
    }

    interface GetDeviceCallback {

        fun onDeviceLoaded(device: Device)

        fun onDataNotAvailable()
    }



    fun getDevices(callback: LoadDevicesCallback)


    fun getData(callback: LoadDataCallback)


    fun getDevice(deviceId: Int, callback: GetDeviceCallback)

    fun saveDevices(devices: List<Device>)

    fun deleteDevice(deviceId: Int)

    fun updateDevice(device: Device)

}