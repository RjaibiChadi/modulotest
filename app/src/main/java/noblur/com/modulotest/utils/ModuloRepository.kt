package noblur.com.modulotest.utils

import androidx.work.*

class ModuloRepository {

    private val contraints: Constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()



    fun syncProgrammesNow(){


        val work = OneTimeWorkRequestBuilder<SyncRepositoryWorker>()
            .setConstraints(contraints)
            .build()

        WorkManager.getInstance()
            .beginUniqueWork("syncProgNow", ExistingWorkPolicy.KEEP,work)
            .enqueue()


    }
}