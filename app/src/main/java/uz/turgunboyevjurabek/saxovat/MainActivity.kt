package uz.turgunboyevjurabek.saxovat

import android.graphics.Color
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
    private var boolean:Boolean =false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

cardWorking()


    }

    override fun onResume() {
        super.onResume()

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

        binding.card1.setOnClickListener {
            navController.popBackStack()
            navController.navigate(R.id.purchaseFragment)
            boolean=false
            cardWorking()
        }
        binding.card2.setOnClickListener {
            navController.popBackStack()
            navController.navigate(R.id.karzinkaFragment)
            boolean=true
            cardWorking()
        }






        cardWorking()
    }

    private fun cardWorking() {
        if (!boolean){
            // card 1 uchun
            binding.tht1.textSize=18f
            binding.card1.strokeWidth=3
            binding.card1.strokeColor = resources.getColor(R.color.btn_true)

            // card 2 uchun
            binding.tht2.textSize=16f
            binding.card2.strokeWidth=1
            binding.card2.strokeColor=resources.getColor(R.color.btn_false)

        }else{
            // card 1 uchun
            binding.tht1.textSize=16f
            binding.card1.strokeWidth=1
            binding.card1.strokeColor=resources.getColor(R.color.btn_false)
            // card 2 uchun
            binding.tht2.textSize=18f
            binding.card2.strokeWidth=3
            binding.card2.strokeColor=resources.getColor(R.color.btn_true)

        }
    }
}