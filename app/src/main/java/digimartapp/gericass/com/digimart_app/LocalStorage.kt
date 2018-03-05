package digimartapp.gericass.com.digimart_app

import android.content.SharedPreferences
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import digimartapp.gericass.com.digimart_app.model.Instrument

/**
 * Created by keita on 2018/03/05.
 */

class LocalStorage(private val pref: SharedPreferences) {
    private val fav = "favorite"
    private val keyword = "keyword"

    private val moshi: Moshi = Moshi.Builder().build()
    private var instrumentAdapter: JsonAdapter<List<Instrument>>
    private var keywordAdapter: JsonAdapter<List<String>>

    init {
        val type = Types.newParameterizedType(List::class.java, Instrument::class.java)

        val keywordType = Types.newParameterizedType(List::class.java, String::class.java)

        instrumentAdapter = moshi.adapter(type)
        keywordAdapter = moshi.adapter(keywordType)
    }

    fun getFavoriteInstrument(): List<Instrument>? {
        val instString = pref.getString(fav, "")
        if (instString == "") return null
        return instrumentAdapter.fromJson(instString)
    }

    fun setFavoriteInstrument(inst: Instrument) {
        val favs = getFavoriteInstrument()?.toMutableList()
        favs?.add(inst)
        val json = instrumentAdapter.toJson(favs)
        pref.apply {
            edit().apply {
                putString(fav, json)
            }.apply()
        }
    }

    fun getKeyword(): List<String>? {
        val words = pref.getString(keyword, "")
        if (words == "") return null
        return keywordAdapter.fromJson(words)
    }

    fun setKeyword(keyword: String) {
        val words = getKeyword()?.toMutableList()
        words?.add(keyword)
        val json = keywordAdapter.toJson(words)
        pref.apply {
            edit().apply {
                putString(keyword, json)
            }.apply()
        }
    }
}