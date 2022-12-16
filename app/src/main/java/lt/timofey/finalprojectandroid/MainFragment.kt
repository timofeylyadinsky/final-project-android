package lt.timofey.finalprojectandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.android.synthetic.main.item_layout.*
import lt.timofey.finalprojectandroid.adapters.CarAdapter
import lt.timofey.finalprojectandroid.api.ApiService
import lt.timofey.finalprojectandroid.db.Car
import lt.timofey.finalprojectandroid.db.CarsWishlistDB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainFragment : Fragment() {
    val apiService = ApiService.create()
    lateinit var navController: NavController
    var cars = listOf<Car>()
    val carList = mutableListOf<Car>()
    lateinit var carsRecyclerViewList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCars()

        /*addToFavourite.setOnClickListener(){
            val intent = Intent(this, WishlistActivity::class.java)
            startActivity(intent)
        }*/
    }
    fun addItemToWishList(position: Int){
        val db = Room.databaseBuilder(
            requireContext().applicationContext,
            CarsWishlistDB::class.java, "car_db"
        ).allowMainThreadQueries()//Делаем в отдельном потоке, в данном случае в главном потоке
            .build()

        val carWishlistDao = db.carWishlistDao()

        if (findById(cars[position].id)) {
            carWishlistDao.addNew(cars[position])
        }


    }

    fun findById(num: Int): Boolean{
        val db = Room.databaseBuilder(
            requireContext().applicationContext,
            CarsWishlistDB::class.java, "car_db"
        ).allowMainThreadQueries()//Делаем в отдельном потоке, в данном случае в главном потоке
            .build()

        val carWishlistDao = db.carWishlistDao()

        val expectedCarsFromDB = carWishlistDao.findByPrimaryKey(num)

        return expectedCarsFromDB.isEmpty()
    }

    fun getCars(){
        val call = apiService.getCars()

        call.enqueue(object: Callback<List<Car>> {

            override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                Log.d("!!!",t.toString())
            }

            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {
                cars = response.body()!!
                //tv.text = cars.toString()
                carsRecyclerViewList = requireActivity().findViewById(R.id.recyclerView)
                carsRecyclerViewList.layoutManager = LinearLayoutManager(requireContext())
                carsRecyclerViewList.adapter = CarAdapter(cars,this@MainFragment)
            }
        })
    }

}