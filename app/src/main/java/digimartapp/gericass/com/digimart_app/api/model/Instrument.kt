package digimartapp.gericass.com.digimart_app.api.model

import java.io.Serializable

/**
 * Created by keita on 2018/03/04.
 */
data class Instrument(val name: String,
                      val description: String,
                      val price: Int,
                      val condition: String,
                      val status: String,
                      val url: String,
                      val image: String,
                      val RegisterDate: String) : Serializable