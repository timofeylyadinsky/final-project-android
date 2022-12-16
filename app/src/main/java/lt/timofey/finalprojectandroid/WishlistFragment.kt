package lt.timofey.finalprojectandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import lt.timofey.finalprojectandroid.adapters.WishlistAdapter
import lt.timofey.finalprojectandroid.db.Car
import lt.timofey.finalprojectandroid.db.CarsWishlistDB
import lt.timofey.finalprojectandroid.livedata.CarViewModel


class WishlistFragment : Fragment() {
    var carsDB = listOf<Car>()
    val carDBList = mutableListOf<Car>()
    lateinit var carsWishlistRecyclerViewList: RecyclerView
    lateinit var viewModel: CarViewModel
    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(requireActivity()).get(CarViewModel::class.java)
        return inflater.inflate(R.layout.fragment_wishlist, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = (activity as MainActivity).navController
        val db = Room.databaseBuilder(
            requireActivity().applicationContext,
            CarsWishlistDB::class.java, "car_db"
        ).allowMainThreadQueries()//Делаем в отдельном потоке, в данном случае в главном потоке
            .build()

        val carWishlistDao = db.carWishlistDao()

        carsDB = carWishlistDao.getAll()
        carsWishlistRecyclerViewList = requireActivity().findViewById(R.id.recyclerViewWishlist)
        carsWishlistRecyclerViewList.layoutManager = LinearLayoutManager(requireActivity())
        carsWishlistRecyclerViewList.adapter = WishlistAdapter(carsDB,this@WishlistFragment)
    }
    fun deleteItemFromWishList(position: Int){
        val db = Room.databaseBuilder(
            requireContext().applicationContext,
            CarsWishlistDB::class.java, "car_db"
        ).allowMainThreadQueries()//Делаем в отдельном потоке, в данном случае в главном потоке
            .build()

        val carWishlistDao = db.carWishlistDao()

        val expectedCars = carWishlistDao.findByPrimaryKey(carsDB[position].id)
        carWishlistDao.deleteByName(expectedCars[0].id)

        carsDB = carWishlistDao.getAll()
        carsWishlistRecyclerViewList = requireActivity().findViewById(R.id.recyclerViewWishlist)
        carsWishlistRecyclerViewList.layoutManager = LinearLayoutManager(requireActivity())
        carsWishlistRecyclerViewList.adapter = WishlistAdapter(carsDB,this@WishlistFragment)
    }
    fun itemClick(position: Int){
        viewModel.liveCar.value = carsDB[position]

        navController.navigate(R.id.carInfoFragment)
    }
}