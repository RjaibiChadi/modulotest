package noblur.com.modulotest.data.source.local.deviceLocal

import androidx.room.*
import noblur.com.modulotest.data.repository.Device

@Dao
interface DeviceDao {


    @Query("SELECT * FROM device")
    fun getAgences():List<Device>


    @Query("SELECT * FROM device WHERE id = :deviceId")
    fun getDeviceById(deviceId : Int): Device?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDevice(device: Device)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDevices(devices: List<Device>)

    @Query("DELETE FROM device")
    fun  deleteAllDevices()

    @Query("DELETE FROM device WHERE id = :deviceId")
    fun deletedeviceById(deviceId: Int): Int

    @Update
    fun updateDevice(device: Device): Int
}