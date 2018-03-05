package digimartapp.gericass.com.digimart_app.fragments


import android.content.Intent
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindToLifecycle
import digimartapp.gericass.com.digimart_app.R
import digimartapp.gericass.com.digimart_app.activities.InstrumentActivity_
import digimartapp.gericass.com.digimart_app.adapters.InstrumentAdapter
import digimartapp.gericass.com.digimart_app.api.RetrofitBuilder
import digimartapp.gericass.com.digimart_app.api.baseURL
import digimartapp.gericass.com.digimart_app.api.client.NewArrivalClient
import digimartapp.gericass.com.digimart_app.api.client.SearchClient
import digimartapp.gericass.com.digimart_app.api.model.Instrument
import digimartapp.gericass.com.digimart_app.decorators.CustomDecorator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment
import org.androidannotations.annotations.ViewById
import java.net.URLEncoder

@EFragment(R.layout.fragment_home)
class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    @ViewById(R.id.home_recycler)
    protected lateinit var recycler: RecyclerView

    @ViewById(R.id.home_swipe)
    protected lateinit var swipeView: SwipeRefreshLayout

    @ViewById(R.id.search_button)
    protected lateinit var searchButton: Button

    @ViewById(R.id.search_edit_text)
    protected lateinit var searchText: EditText

    @ViewById(R.id.home_progress)
    protected lateinit var progressBar: ProgressBar

    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    private lateinit var mAdapter: InstrumentAdapter

    private var page = 0

    private var state: String = "new"

    private var word: String = ""

    @AfterViews
    fun init() {
        setList()
        refresh()
        newArrival()
        initButton()
    }

    fun initButton() {
        searchButton.setOnClickListener {
            page = 1
            search(page)
        }
    }

    fun search(page: Int) {
        state = "search"
        val keyword: String
        if (searchText.text.isBlank()) {
            keyword = this.word
        } else {
            keyword = URLEncoder.encode(searchText.text.toString(), "UTF-8")
            word = keyword
        }
        val params = mapOf("keyword" to keyword, "page" to page.toString())
        val request = RetrofitBuilder.build(baseURL)
        val client = request.create(SearchClient::class.java)
        progressBar.visibility = View.VISIBLE
        client.search(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate {
                    mAdapter.notifyDataSetChanged()
                    searchText.text.clear()
                    progressBar.visibility = View.GONE
                }
                .bindToLifecycle(this)
                .subscribe({
                    mAdapter.instruments = it
                }, {
                    Toast.makeText(activity, "timeout", Toast.LENGTH_SHORT).show()
                })
    }

    fun refresh() {
        swipeView.setColorSchemeResources(R.color.swipe_blue,
                R.color.swipe_green,
                R.color.swipe_orange,
                R.color.swipe_red)
        swipeView.setOnRefreshListener(this)

    }

    override fun onRefresh() {
        if (state == "new") {
            newArrival()
        } else {
            search(++page)
        }
        swipeView.isRefreshing = false

    }

    fun newArrival() {
        val request = RetrofitBuilder.build(baseURL)
        val client = request.create(NewArrivalClient::class.java)
        progressBar.visibility = View.VISIBLE
        client.getNewArrivalInstruments()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .bindToLifecycle(this)
                .doAfterTerminate {
                    mAdapter.notifyDataSetChanged()
                    progressBar.visibility = View.GONE
                }
                .subscribe({
                    mAdapter.instruments = it
                }, {
                    Toast.makeText(activity, "timeout", Toast.LENGTH_SHORT).show()
                })
    }

    private fun setList() {
        val i: List<Instrument> = emptyList()
        recycler.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(activity)
        recycler.layoutManager = mLayoutManager
        mAdapter = InstrumentAdapter(i) { inst: Instrument ->
            val intent = Intent(activity, InstrumentActivity_::class.java)
            intent.putExtra("instrument", inst)
            startActivity(intent)
        }
        recycler.adapter = mAdapter
        recycler.addItemDecoration(CustomDecorator.createDefaultDecoration(activity!!.baseContext))
    }
}// Required empty public constructor
