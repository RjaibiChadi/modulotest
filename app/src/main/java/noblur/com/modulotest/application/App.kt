package noblur.com.modulotest.application

import android.app.Application
import noblur.com.modulotest.utils.ModuloRepository

class App : Application() {

    companion object{
        lateinit var repository: ModuloRepository

    }

    override fun onCreate() {
        super.onCreate()

        repository = ModuloRepository()

        repository.syncProgrammesNow()

    }
}