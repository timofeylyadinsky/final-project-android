package lt.timofey.finalprojectandroid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import lt.timofey.finalprojectandroid.MakersFragment
import lt.timofey.finalprojectandroid.R
import lt.timofey.finalprojectandroid.db.Car

class MakersAdapter(val plist: List<String>, val mactivity: MakersFragment): RecyclerView.Adapter<MakersAdapter.MakersViewHolder>() {

    class MakersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MakersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var holder: MakersAdapter.MakersViewHolder
        holder = MakersViewHolder(inflater.inflate(R.layout.makers_layout, parent, false))
        //val btnAdd = holder.itemView.findViewById<ImageView>(R.id.addToFavourite)

        //holder.itemView.setOnClickListener{
            //mactivity.itemClick(holder.adapterPosition)
        //}

        return holder
    }

    override fun onBindViewHolder(holder: MakersViewHolder, position: Int) {
        val carMaker = holder.itemView.findViewById<TextView>(R.id.carMaker)
        carMaker.text = plist[position]
    }

    override fun getItemCount(): Int {
        return plist.count()
    }
}