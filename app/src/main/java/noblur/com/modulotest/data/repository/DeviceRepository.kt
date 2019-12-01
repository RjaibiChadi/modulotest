package noblur.com.modulotest.data.repository

import noblur.com.modulotest.data.entity.Data


class DeviceRepository(
    private val answerRemoteDataSource: DeviceDataSource,
    private val answerLocalDataSource: DeviceDataSource
): DeviceDataSource {

    override fun getData(callback: DeviceDataSource.LoadDataCallback) {

        answerRemoteDataSource.getData(object :DeviceDataSource.LoadDataCallback{
            override fun onDataLoaded(data: Data) {

                callback.onDataLoaded(data)
            }

            override fun onDataNotAvailable(code: Int) {

                callback.onDataNotAvailable(code)

            }


        })
    }


    override fun updateDevice(device: Device) {
        answerLocalDataSource.updateDevice(device)

    }

    override fun getDevices(callback: DeviceDataSource.LoadDevicesCallback) {

        answerLocalDataSource.getDevices(object :DeviceDataSource.LoadDevicesCallback{
            override fun onDevicesLoaded(devices: List<Device>) {

                callback.onDevicesLoaded(devices)
            }

            override fun onDataNotAvailable(code: Int) {

                callback.onDataNotAvailable(code)
                answerRemoteDataSource.getData(object :DeviceDataSource.LoadDataCallback{
                    override fun onDataLoaded(data: Data) {

                        callback.onDevicesLoaded(data.devices)
                    }

                    override fun onDataNotAvailable(code: Int) {

                        callback.onDataNotAvailable(500)

                    }


                })
            }


        })

    }

    override fun getDevice(deviceId: Int, callback: DeviceDataSource.GetDeviceCallback) {


        answerLocalDataSource.getDevice(deviceId,object :DeviceDataSource.GetDeviceCallback{
            override fun onDeviceLoaded(device: Device) {

                callback.onDeviceLoaded(device)

            }

            override fun onDataNotAvailable() {

                callback.onDataNotAvailable()
            }

        })
    }

    override fun saveDevices(devices: List<Device>) {


    }

    override fun deleteDevice(deviceId: Int) {


    }


    companion object {

        private var INSTANCE: DeviceRepository? = null


        @JvmStatic fun getInstance(deviceRemoteDataSource: DeviceDataSource,
                                   deviceLocalDataSource: DeviceDataSource
        ) =
            INSTANCE
                ?: synchronized(DeviceRepository::class.java) {
                INSTANCE
                    ?: DeviceRepository(
                        deviceRemoteDataSource,
                        deviceLocalDataSource
                    )
                    .also { INSTANCE = it }
            }

        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }
    }


}