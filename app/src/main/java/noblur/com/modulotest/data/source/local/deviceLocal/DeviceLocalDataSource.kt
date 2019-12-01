package noblur.com.modulotest.data.source.local.deviceLocal


import androidx.annotation.VisibleForTesting
import noblur.com.modulotest.data.repository.Device
import noblur.com.modulotest.data.repository.DeviceDataSource
import noblur.com.modulotest.utils.AppExecutors

class DeviceLocalDataSource(
    val appExecutors: AppExecutors,
    val deviceDao: DeviceDao
): DeviceDataSource {
    override fun updateDevice(device: Device) {

        appExecutors.diskIO.execute { deviceDao.updateDevice(device) }

    }

    override fun getDevices(callback: DeviceDataSource.LoadDevicesCallback) {
        appExecutors.diskIO.execute {

            val devices = deviceDao.getAgences()
            appExecutors.diskIO.execute {
                if (devices.isEmpty()){

                    callback.onDataNotAvailable(500)
                }else{
                    callback.onDevicesLoaded(devices)

                }

            }
        }

    }

    override fun getDevice(deviceId: Int, callback: DeviceDataSource.GetDeviceCallback) {


        appExecutors.diskIO.execute {

            val device = deviceDao.getDeviceById(deviceId)

            appExecutors.diskIO.execute {

                if (device!=null){
                    callback.onDeviceLoaded(device)
                }else{
                    callback.onDataNotAvailable()
                }

            }

        }
    }

    override fun saveDevices(devices: List<Device>) {

    }

    override fun deleteDevice(deviceId: Int) {


    }

    companion object {
        private var INSTANCE: DeviceLocalDataSource? = null

        @JvmStatic
        fun getInstance(appExecutors: AppExecutors, deviceDao: DeviceDao): DeviceLocalDataSource {
            if (INSTANCE == null) {
                synchronized(DeviceLocalDataSource::javaClass) {
                    INSTANCE =
                        DeviceLocalDataSource(appExecutors, deviceDao)
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