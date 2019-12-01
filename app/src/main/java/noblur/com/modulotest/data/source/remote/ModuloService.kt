package noblur.com.modulotest.data.source.remote

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.Observable
import noblur.com.modulotest.data.entity.Data
import noblur.com.modulotest.data.repository.User
import retrofit2.http.*

interface ModuloService {

    @GET("data.json")
    fun getData() : Observable<Data>


}