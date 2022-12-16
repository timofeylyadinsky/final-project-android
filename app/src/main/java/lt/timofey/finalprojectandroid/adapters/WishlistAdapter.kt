package lt.timofey.finalprojectandroid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import lt.timofey.finalprojectandroid.R
import lt.timofey.finalprojectandroid.WishlistFragment
import lt.timofey.finalprojectandroid.db.Car

class WishlistAdapter(val plist: List<Car>, val wactivity: WishlistFragment): RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>() {

    class WishlistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var holder: WishlistAdapter.WishlistViewHolder //= PersonViewHolder(inflater.inflate(R.layout.item_layout, parent, false))
        holder = WishlistViewHolder(inflater.inflate(R.layout.wishlist_item_layout, parent, false))

        holder.itemView.setOnClickListener{
            wactivity.itemClick(holder.adapterPosition)
        }
        val btnDel = holder.itemView.findViewById<ImageView>(R.id.deleteFromFavourite)

        btnDel.setOnClickListener {
            wactivity.deleteItemFromWishList(holder.adapterPosition)
        }
        /*holder.itemView.setOnLongClickListener {
            mactivity.removeItem(holder.adapterPosition)
            return@setOnLongClickListener true
        }*/

        return holder
    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        val carMaker = holder.itemView.findViewById<TextView>(R.id.carMaker)
        carMaker.text = plist[position].maker +"\n"+ plist[position].model
        val year = holder.itemView.findViewById<TextView>(R.id.yearCar)
        year.text = plist[position].year
    }

    override fun getItemCount(): Int {
        return plist.count()
    }
}