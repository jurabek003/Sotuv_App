package uz.turgunboyevjurabek.saxovat

import android.content.IntentFilter
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import uz.turgunboyevjurabek.saxovat.databinding.ActivityMainBinding
import uz.turgunboyevjurabek.saxovat.utils.AppObject
import uz.turgunboyevjurabek.saxovat.utils.Girgitton
import uz.turgunboyevjurabek.saxovat.utils.NetworkConnecting

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    var boolean:Boolean=false
    private lateinit var myBroadcastReceiver: MyBroadcastReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        cardWorkingPurchase()
        cardWorkingKarzinka()

         myBroadcastReceiver=MyBroadcastReceiver(binding.mainConstraintLayout)
        val intentFilter=IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(myBroadcastReceiver,intentFilter)

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(myBroadcastReceiver)
    }
    override fun onStart() {
        super.onStart()
        /**
         * Faqat light mode turishi uchun
         */
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
    override fun onResume() {
        super.onResume()
        AppObject.binding = binding
        AppObject.fragmentManager = supportFragmentManager
        val navController = findNavController(R.id.my_navigation_host)


//        if(!NetworkConnecting.isNetwork){
//
//            binding.lottiNoInternet.visibility=View.VISIBLE
//            binding.myNavigationHost.visibility=View.GONE
//            snackbar1.show()
//        }else{
//            binding.lottiNoInternet.visibility=View.GONE
//            binding.myNavigationHost.visibility=View.VISIBLE
//            snackbar2.show()
//        }


        binding.card1.setOnClickListener {
            navController.popBackStack()
            navController.navigate(R.id.purchaseFragment)
            Girgitton.boolean=false
            cardWorkingPurchase()
        }
        binding.card2.setOnClickListener {
            navController.popBackStack()
            navController.navigate(R.id.karzinkaFragment)
            Girgitton.boolean=true
            cardWorkingKarzinka()
        }





        //cardWorking()
    }

     fun cardWorkingPurchase() {

        if (!Girgitton.boolean){
            // card 1 uchun
            binding.tht1.textSize=18f
            binding.card1.strokeWidth=3
            binding.card1.strokeColor = resources.getColor(R.color.btn_true)

            // card 2 uchun
            binding.tht2.textSize=16f
            binding.card2.strokeWidth=1
            binding.card2.strokeColor=resources.getColor(R.color.btn_false)

        }
    }
    fun cardWorkingKarzinka(){
        if (Girgitton.boolean){
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

