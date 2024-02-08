package uz.turgunboyevjurabek.saxovat

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import uz.turgunboyevjurabek.saxovat.utils.AppObject
import uz.turgunboyevjurabek.saxovat.utils.NetworkConnecting

class MyBroadcastReceiver(view: View) : BroadcastReceiver() {
    val snackbar1=Snackbar.make(view,"Internetga ulanmagansiz :(",Snackbar.LENGTH_INDEFINITE)
    val snackbar2=Snackbar.make(view,"Internetga ulandi",3000)
    override fun onReceive(context: Context, intent: Intent) {
        if (!NetworkConnecting.isNetworkAvailable(context)){
            snackbar1.show()
            snackbar1.setAction("Yopish"){
                snackbar1.dismiss()
            }
        }else{
            snackbar2.dismiss()
        }
    }

}
