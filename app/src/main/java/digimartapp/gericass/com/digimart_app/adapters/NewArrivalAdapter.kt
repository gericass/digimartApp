package digimartapp.gericass.com.digimart_app.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import digimartapp.gericass.com.digimart_app.R
import digimartapp.gericass.com.digimart_app.api.model.Instrument
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation

/**
 * Created by keita on 2018/03/04.
 */


class NewArrivalAdapter(private val instruments: List<Instrument>) : RecyclerView.Adapter<NewArrivalAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val v = LayoutInflater.from(context)
                .inflate(R.layout.card_new_arrival, parent, false)
        return ViewHolder(v = v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setUp(instruments[position])
        Picasso.with(context).load(instruments[position].image).fit().centerCrop().transform(RoundedCornersTransformation(2,0)).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return instruments.size
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val imageView: ImageView = v.findViewById(R.id.instrument_image)
        private val name: TextView = v.findViewById(R.id.instrument_name)
        private val description: TextView = v.findViewById(R.id.instrument_description)

        fun setUp(inst: Instrument) {
            this.name.text = inst.name
            this.description.text = inst.description
        }
    }
}