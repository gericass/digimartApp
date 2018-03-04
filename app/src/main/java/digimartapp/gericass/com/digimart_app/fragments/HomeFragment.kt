package digimartapp.gericass.com.digimart_app.fragments


import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindToLifecycle
import digimartapp.gericass.com.digimart_app.R
import digimartapp.gericass.com.digimart_app.adapters.NewArrivalAdapter
import digimartapp.gericass.com.digimart_app.api.RetrofitBuilder
import digimartapp.gericass.com.digimart_app.api.baseURL
import digimartapp.gericass.com.digimart_app.api.client.NewArrivalClient
import digimartapp.gericass.com.digimart_app.api.model.Instrument
import digimartapp.gericass.com.digimart_app.decorators.CustomDecorator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment
import org.androidannotations.annotations.ViewById

@EFragment(R.layout.fragment_home)
class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    @ViewById(R.id.home_recycler)
    protected lateinit var recycler: RecyclerView

    @ViewById(R.id.home_swipe)
    protected lateinit var swipeView: SwipeRefreshLayout

    private lateinit var mLayoutManager: RecyclerView.LayoutManager


    @AfterViews
    fun init() {
        refresh()
        apiRequest()
    }

    fun refresh() {
        swipeView.setColorSchemeResources(R.color.swipe_blue,
                R.color.swipe_green,
                R.color.swipe_orange,
                R.color.swipe_red)
        swipeView.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        apiRequest()
        swipeView.isRefreshing = false
    }

    fun apiRequest() {
        val request = RetrofitBuilder.build(baseURL)
        val client = request.create(NewArrivalClient::class.java)
        client.getNewArrivalInstruments()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .bindToLifecycle(this)
                .subscribe({
                    setList(it)
                }, {
                    Toast.makeText(activity, "timeout", Toast.LENGTH_SHORT).show()
                    apiRequest()
                })
    }


    private fun setList(instruments: List<Instrument>) {
        recycler.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(activity)
        recycler.layoutManager = mLayoutManager
        val mAdapter = NewArrivalAdapter(instruments)
        recycler.adapter = mAdapter
        recycler.addItemDecoration(CustomDecorator.createDefaultDecoration(activity!!.baseContext))
    }
}// Required empty public constructor
