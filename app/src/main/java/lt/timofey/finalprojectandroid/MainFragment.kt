package lt.timofey.finalprojectandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
//import android.widget.SearchView
import androidx.appcompat.widget.SearchView
import android.widget.TextView
import android.widget.Toast
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
import lt.timofey.finalprojectandroid.livedata.CarViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class MainFragment : Fragment() {
    val apiService = ApiService.create()
    lateinit var navController: NavController
    var cars = listOf<Car>()
    val carList = mutableListOf<Car>()
    lateinit var carsRecyclerViewList: RecyclerView
    lateinit var viewModel: CarViewModel
    lateinit var searchView: SearchView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(requireActivity()).get(CarViewModel::class.java)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCars()
        navController = (activity as MainActivity).navController
        searchView = requireActivity().findViewById<SearchView>(R.id.searchView)
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText)
                return false
            }
        })
        /*addToFavourite.setOnClickListener(){
            val intent = Intent(this, WishlistActivity::class.java)
            startActivity(intent)
        }*/
    }
    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist = ArrayList<Car>()

        // running a for loop to compare elements.
        for (item in cars) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.maker.toLowerCase().contains(text.lowercase(Locale.getDefault()))) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(requireContext(), "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            carsRecyclerViewList = requireActivity().findViewById(R.id.recyclerView)
            carsRecyclerViewList.layoutManager = LinearLayoutManager(requireContext())
            carsRecyclerViewList.adapter = CarAdapter(filteredlist,this@MainFragment)
        }
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
            Toast.makeText(requireActivity(),"Add to Favourite",Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(requireActivity(),"Already in favourite",Toast.LENGTH_LONG).show()
        }


    }

    fun itemClick(position: Int){
        viewModel.liveCar.value = Car(cars[position].id,cars[position].maker,cars[position].model,cars[position].year,cars[position].engine,cars[position].country,cars[position].image)

        navController.navigate(R.id.carInfoFragment)
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