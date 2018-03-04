package digimartapp.gericass.com.digimart_app.decorators

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import digimartapp.gericass.com.digimart_app.R

/**
 * Created by keita on 2018/03/04.
 */

class CustomDecorator(private val space: Int) : RecyclerView.ItemDecoration() {

    companion object {
        fun createDefaultDecoration(context: Context): CustomDecorator {
            val spacingInPixels = context.resources.getDimensionPixelSize(R.dimen.recycler)
            return CustomDecorator(spacingInPixels)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.top = space
        outRect.bottom = space
    }
}