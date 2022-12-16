package lt.timofey.finalprojectandroid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import lt.timofey.finalprojectandroid.MainFragment
import lt.timofey.finalprojectandroid.R
import lt.timofey.finalprojectandroid.SortedMakersFragment
import lt.timofey.finalprojectandroid.db.Car

class SortedAdapter(val plist: List<Car>, val mactivity: SortedMakersFragment): RecyclerView.Adapter<SortedAdapter.CarViewHolder>() {

    class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var holder: SortedAdapter.CarViewHolder
        holder = CarViewHolder(inflater.inflate(R.layout.item_layout, parent, false))
        val btnAdd = holder.itemView.findViewById<ImageView>(R.id.addToFavourite)

        btnAdd.setOnClickListener {
            mactivity.addItemToWishList(holder.adapterPosition)
        }
        holder.itemView.setOnClickListener{
            mactivity.itemClick(holder.adapterPosition)
        }

        return holder
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val carMaker = holder.itemView.findViewById<TextView>(R.id.carMaker)
        carMaker.text = plist[position].maker +"\n"+ plist[position].model
        val year = holder.itemView.findViewById<TextView>(R.id.yearCar)
        year.text = plist[position].year
    }

    override fun getItemCount(): Int {
        return plist.count()
    }
}