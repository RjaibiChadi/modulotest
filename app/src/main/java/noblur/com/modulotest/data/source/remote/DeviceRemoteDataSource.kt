package noblur.com.modulotest.data.source.remote


import androidx.annotation.VisibleForTesting
import io.reactivex.disposables.CompositeDisposable
import noblur.com.modulotest.data.repository.Device
import noblur.com.modulotest.data.repository.DeviceDataSource


class DeviceRemoteDataSource(
    val compositeDisposable: CompositeDisposable,
    val api: ModuloService
) : DeviceDataSource {

    override fun updateDevice(device: Device) {


    }

    override fun getDevices(callback: DeviceDataSource.LoadDevicesCallback) {


    }

    override fun getDevice(deviceId: Int, callback: DeviceDataSource.GetDeviceCallback) {

    }

    override fun saveDevices(devices: List<Device>) {

    }

    override fun deleteDevice(deviceId: Int) {

    }


    companion object {
        private var INSTANCE: DeviceRemoteDataSource? = null

        @JvmStatic
        fun getInstance(
            compositeDisposable: CompositeDisposable,
            api: ModuloService
        ): DeviceRemoteDataSource {
            if (INSTANCE == null) {
                synchronized(DeviceRemoteDataSource::javaClass) {
                    INSTANCE = DeviceRemoteDataSource(compositeDisposable,api)
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