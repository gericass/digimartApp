package digimartapp.gericass.com.digimart_app.activities

import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import android.webkit.WebViewClient
import digimartapp.gericass.com.digimart_app.R
import digimartapp.gericass.com.digimart_app.model.Instrument
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.Extra
import org.androidannotations.annotations.ViewById


@EActivity(R.layout.activity_instrument)
class InstrumentActivity : AppCompatActivity() {

    @ViewById(R.id.web_view)
    protected lateinit var webView: WebView

    @Extra
    protected lateinit var instrument: Instrument

    @AfterViews
    fun init() {

        webView.webViewClient = WebViewClient()
        webView.loadUrl(instrument.url)
    }
}
