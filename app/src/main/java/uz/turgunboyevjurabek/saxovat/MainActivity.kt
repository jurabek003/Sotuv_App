package uz.turgunboyevjurabek.saxovat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import uz.turgunboyevjurabek.saxovat.databinding.ActivityMainBinding
import uz.turgunboyevjurabek.saxovat.utils.AppObject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        AppObject.binding = binding
        AppObject.fragmentManager = supportFragmentManager
        val navController = findNavController(R.id.my_navigation_host)

        /**
         * Faqat light mode turishi uchun
         */
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        /**
         * navigatsiya uchun
         */
        binding.btnNavigation.setupWithNavController(navController)

//        binding.btnNavigation.setOnItemSelectedListener {
//            when(it.itemId){
//                R.id.purchaseFragment->navController.navigate(R.id.purchaseFragment)
//                R.id.karzinkaFragment->navController.navigate(R.id.karzinkaFragment)
//                else->{}
//            }
//            true
//        }


    }
}