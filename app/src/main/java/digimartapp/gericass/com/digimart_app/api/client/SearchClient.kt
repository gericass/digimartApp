package digimartapp.gericass.com.digimart_app.api.client

import digimartapp.gericass.com.digimart_app.model.Instrument
import digimartapp.gericass.com.digimart_app.api.search
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Created by keita on 2018/03/05.
 */

interface SearchClient {
    @GET(search)
    fun search(@QueryMap params: Map<String, String>): Observable<List<Instrument>>
}