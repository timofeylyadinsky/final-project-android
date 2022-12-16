package lt.timofey.finalprojectandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
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


        val tv = requireActivity().findViewById<TextView>(R.id.carName)
        viewModel.liveCar.observeForever{
            tv.text = it.toString()
        }
    }
}