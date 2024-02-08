package uz.turgunboyevjurabek.saxovat.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import androidx.fragment.app.FragmentManager
import uz.turgunboyevjurabek.saxovat.databinding.ActivityMainBinding

object AppObject {
    @SuppressLint("StaticFieldLeak")
    lateinit var binding: ActivityMainBinding
    lateinit var fragmentManager: FragmentManager

}
