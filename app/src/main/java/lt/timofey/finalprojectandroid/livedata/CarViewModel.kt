package lt.timofey.finalprojectandroid.livedata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import lt.timofey.finalprojectandroid.db.Car

class CarViewModel : ViewModel(){
    val liveCar: MutableLiveData<Car> = MutableLiveData(Car(100,"","","","","",""))
    val liveName: MutableLiveData<String> = MutableLiveData()
}