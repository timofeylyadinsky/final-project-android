package lt.timofey.finalprojectandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import lt.timofey.finalprojectandroid.livedata.CarViewModel


class CarInfoFragment : Fragment() {

    lateinit var navController: NavController
    lateinit var viewModel: CarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(requireActivity()).get(CarViewModel::class.java)

        return inflater.inflate(R.layout.fragment_car_info, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val carMakerName = requireActivity().findViewById<TextView>(R.id.carMakerName)
        val carModelName = requireActivity().findViewById<TextView>(R.id.carModelName)
        val carYearRelease = requireActivity().findViewById<TextView>(R.id.carYearRelease)
        val carEngineName = requireActivity().findViewById<TextView>(R.id.carEngineName)
        val carCountry = requireActivity().findViewById<TextView>(R.id.carCountry)
        val carImage = requireActivity().findViewById<ImageView>(R.id.carImage)
        viewModel.liveCar.observeForever{
            carMakerName.text = it.maker.toString()
            carModelName.text = it.model
            Picasso.get()
                .load(it.image)
                .into(carImage)
            carYearRelease.text = "Release: " + it.year.toString()
            carEngineName.text = "Engine: " + it.engine.toString()
            carCountry.text = "Country: " + it.country.toString()

        }
    }
}