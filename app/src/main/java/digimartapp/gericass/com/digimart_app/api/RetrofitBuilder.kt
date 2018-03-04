package digimartapp.gericass.com.digimart_app.api

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by keita on 2018/03/04.
 */

class RetrofitBuilder {
    companion object {
        fun build(baseURL: String): Retrofit {
            val moshi = Moshi.Builder().build()
            val retrofit = Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            return retrofit
        }
    }
}