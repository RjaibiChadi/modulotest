package noblur.com.modulotest.data.entity

import noblur.com.modulotest.data.repository.Device
import noblur.com.modulotest.data.repository.User

data class Data(
    var devices:List<Device>,
    val user:User

)