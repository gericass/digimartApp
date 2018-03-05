package digimartapp.gericass.com.digimart_app.api.client

import digimartapp.gericass.com.digimart_app.model.Instrument
import digimartapp.gericass.com.digimart_app.api.newArrival
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Created by keita on 2018/03/04.
 */

interface NewArrivalClient {
    @GET(newArrival)
    fun getNewArrivalInstruments(): Observable<List<Instrument>>
}