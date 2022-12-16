package lt.timofey.finalprojectandroid

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import lt.timofey.finalprojectandroid.adapters.CarAdapter
import lt.timofey.finalprojectandroid.adapters.MakersAdapter
import lt.timofey.finalprojectandroid.api.ApiService
import lt.timofey.finalprojectandroid.db.Car
import lt.timofey.finalprojectandroid.db.CarsWishlistDB
import lt.timofey.finalprojectandroid.livedata.CarViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class MakersFragment : Fragment() {
    val apiService = ApiService.create()
    lateinit var navController: NavController
    var cars = listOf<Car>()
    var carMakers = listOf<String>()
    var filterMakers = listOf<String>()
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
        return inflater.inflate(R.layout.fragment_makers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCars()
        navController = (activity as MainActivity).navController
        searchView = requireActivity().findViewById<SearchView>(R.id.searchView2)
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
    fun itemClick(position: Int){
        viewModel.liveName.value = filterMakers[position]
        navController.navigate(R.id.sortedMakersFragment)
    }
    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist = ArrayList<String>()

        // running a for loop to compare elements.
        for (item in carMakers) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.toLowerCase().contains(text.lowercase(Locale.getDefault()))) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            carsRecyclerViewList = requireActivity().findViewById(R.id.recyclerView)
            carsRecyclerViewList.layoutManager = LinearLayoutManager(requireContext())
            carsRecyclerViewList.adapter = MakersAdapter(filteredlist,this@MakersFragment)
            Toast.makeText(requireContext(), "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            carsRecyclerViewList = requireActivity().findViewById(R.id.recyclerView)
            carsRecyclerViewList.layoutManager = LinearLayoutManager(requireContext())
            carsRecyclerViewList.adapter = MakersAdapter(filteredlist,this@MakersFragment)
        }
        filterMakers = filteredlist
    }

    fun getCars(){
        val call = apiService.getCars()

        call.enqueue(object: Callback<List<Car>> {

            override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                Log.d("!!!",t.toString())
            }

            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {
                cars = response.body()!!
                val setMakers: MutableSet<String> = mutableSetOf()
                for (item in cars){
                    setMakers.add(item.maker)
                }
                carMakers = setMakers.toList().sorted()
                filterMakers = carMakers
                //tv.text = cars.toString()
                carsRecyclerViewList = requireActivity().findViewById(R.id.recyclerView)
                carsRecyclerViewList.layoutManager = LinearLayoutManager(requireContext())
                carsRecyclerViewList.adapter = MakersAdapter(carMakers,this@MakersFragment)
            }
        })
    }
}