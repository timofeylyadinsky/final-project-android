package lt.timofey.finalprojectandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import lt.timofey.finalprojectandroid.databinding.ActivityMainBinding
import lt.timofey.finalprojectandroid.livedata.CarViewModel

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController

    private lateinit var binding: ActivityMainBinding

    lateinit var viewModel: CarViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_main)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        viewModel = ViewModelProvider(this).get(CarViewModel::class.java)

        setSupportActionBar(binding.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        //val navView: NavigationView = binding.navView
        setupActionBarWithNavController(navController, drawerLayout)
        binding.toolbar.setupWithNavController(navController, drawerLayout)
        binding.navView.setupWithNavController(navController)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId != navController.currentDestination!!.id){
            navController.navigate(item.itemId)
        }
        return true
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}