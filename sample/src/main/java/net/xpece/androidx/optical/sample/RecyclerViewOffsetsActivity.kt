package net.xpece.androidx.optical.sample

import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_recycler_offsets.*

class RecyclerViewOffsetsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_offsets)

        val insets =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics)
        list.addItemDecoration(MyDecoration(insets))

        list.adapter = MyAdapter()
    }

    class MyDecoration(private val insets: Float) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val card = view as CardView
            val maxCardElevation = if (Build.VERSION.SDK_INT < 21 || card.useCompatPadding) {
                card.maxCardElevation
            } else {
                0f
            }
            outRect.left = (insets - maxCardElevation).toInt()
            outRect.right = (insets - maxCardElevation).toInt()
            outRect.bottom = (insets - maxCardElevation * 1.5f).toInt()
            outRect.top = if (parent.getChildAdapterPosition(view) == 0) {
                (insets - maxCardElevation * 1.5f).toInt()
            } else {
                (0 - maxCardElevation * 1.5f).toInt()
            }
        }
    }

    class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
            return MyViewHolder(view)
        }

        override fun getItemCount(): Int {
            return 5
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
