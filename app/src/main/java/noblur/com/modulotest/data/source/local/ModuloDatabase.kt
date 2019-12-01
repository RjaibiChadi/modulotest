package noblur.com.modulotest.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import noblur.com.modulotest.data.repository.Device
import noblur.com.modulotest.data.repository.User
import noblur.com.modulotest.data.source.local.deviceLocal.DeviceDao
import noblur.com.modulotest.data.source.local.userLocal.UserDao

@Database(entities = arrayOf(
    User::class,
    Device::class
   ),version = 1)
abstract class ModuloDatabase: RoomDatabase() {


        abstract fun userDao(): UserDao
        abstract fun deviceDao():DeviceDao

        companion object {

            private var INSTANCE : ModuloDatabase?=null

            private val lock = Any()

            fun getInstance(context: Context): ModuloDatabase {
                synchronized(lock){
                    if (INSTANCE ==null){

                            INSTANCE = Room.databaseBuilder(context.applicationContext,
                                ModuloDatabase::class.java,"modulo.db")
                                .fallbackToDestructiveMigration()
                                .build()
                    }

                    return INSTANCE!!


                }
            }
        }

}