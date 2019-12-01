package noblur.com.modulotest.data.repository


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import noblur.com.modulotest.data.entity.Address

@Entity
data class User(

    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var firstName: String,
    var lastName: String,
    var birthDate:Double,
    @Embedded
    var address:Address
)