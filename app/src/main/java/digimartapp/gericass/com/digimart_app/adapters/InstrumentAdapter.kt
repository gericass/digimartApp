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
import digimartapp.gericass.com.digimart_app.model.Instrument
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation


/**
 * Created by keita on 2018/03/04.
 */


class InstrumentAdapter(var instruments: List<Instrument>, val f: (Instrument) -> Unit) : RecyclerView.Adapter<InstrumentAdapter.ViewHolder>() {

    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val v = LayoutInflater.from(context)
                .inflate(R.layout.card_new_arrival, parent, false)
        return ViewHolder(v = v, mAdapter = this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setUp(instruments[position])
        Picasso.with(context).load(instruments[position].image).fit().centerCrop().transform(RoundedCornersTransformation(2, 0)).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return instruments.size
    }

    fun onItemClick(position: Int) {
        val inst = instruments[position]
        f(inst)
    }

    class ViewHolder(v: View, private val mAdapter: InstrumentAdapter) : RecyclerView.ViewHolder(v), View.OnClickListener {
        val imageView: ImageView = v.findViewById(R.id.instrument_image)
        private val name: TextView = v.findViewById(R.id.instrument_name)
        private val description: TextView = v.findViewById(R.id.instrument_description)

        fun setUp(inst: Instrument) {
            this.name.text = inst.name
            this.description.text = inst.description
            imageView.setOnClickListener(this)
            name.setOnClickListener(this)
            description.setOnClickListener(this)

        }

        override fun onClick(v: View?) {
            mAdapter.onItemClick(adapterPosition)
        }
    }


}