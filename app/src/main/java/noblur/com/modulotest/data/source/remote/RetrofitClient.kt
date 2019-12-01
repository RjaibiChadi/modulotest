package noblur.com.modulotest.data.source.remote


import noblur.com.modulotest.utils.Util.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.internal.Util
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private var retrofit : Retrofit?=null
    private var httpClient:OkHttpClient?=null



    val instance: Retrofit
            get() {

                if (retrofit == null){

                    httpClient = OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .readTimeout(30, TimeUnit.SECONDS)
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .build()



                    retrofit = Retrofit.Builder()
                        .client(httpClient)
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
                }

                return retrofit!!


            }
}