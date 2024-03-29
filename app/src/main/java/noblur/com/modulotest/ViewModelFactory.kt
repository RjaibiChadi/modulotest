/*
 *  Copyright 2017 Google Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package noblur.com.modulotest

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import noblur.com.modulotest.controlcomponent.ControlViewModel
import noblur.com.modulotest.data.repository.DeviceRepository
import noblur.com.modulotest.data.repository.UserRepository
import noblur.com.modulotest.homecomponent.HomeViewModel
import noblur.com.modulotest.profilcomponent.ProfilViewModel
import noblur.com.modulotest.profilupdatecomponent.ProfilUpdateViewModel

/**
 * A creator is used to inject the product ID into the ViewModel
 *
 *
 * This creator is to showcase how to inject dependencies into ViewModels. It's not
 * actually necessary in this case, as the product ID can be passed in a public method.
 */
class ViewModelFactory private constructor(
    private val userRepository: UserRepository,
    private val deviceRepository: DeviceRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
            with(modelClass) {
                when {
                    isAssignableFrom(HomeViewModel::class.java) ->
                        HomeViewModel(deviceRepository)
                    isAssignableFrom(ControlViewModel::class.java) ->
                        ControlViewModel(deviceRepository)
                    isAssignableFrom(ProfilViewModel::class.java) ->
                        ProfilViewModel(userRepository)
                    isAssignableFrom(ProfilUpdateViewModel::class.java) ->
                        ProfilUpdateViewModel(userRepository)

                    else ->
                        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            } as T

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) =
                INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                    INSTANCE ?: ViewModelFactory(
                            Injection.provideUserRepository(application.applicationContext),
                            Injection.provideDeviceRepository(application.applicationContext)
                        )
                            .also { INSTANCE = it }
                }


        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }


}
