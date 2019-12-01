package noblur.com.modulotest.data.repository

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Device(

    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var deviceName: String,
    var intensity:Int?,
    var position:Int?,
    var temperature:Int,
    var mode:String?,
    var productType:String

)