package uz.turgunboyevjurabek.saxovat.ui.fragments.homeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import uz.turgunboyevjurabek.saxovat.MainActivity
import uz.turgunboyevjurabek.saxovat.R
import uz.turgunboyevjurabek.saxovat.databinding.ActivityMainBinding
import uz.turgunboyevjurabek.saxovat.databinding.FragmentHomeBinding
import uz.turgunboyevjurabek.saxovat.utils.AppObject
import uz.turgunboyevjurabek.saxovat.utils.LoginCheck
import uz.turgunboyevjurabek.saxovat.utils.MySharedPreference

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginCheck()

        binding.btnSotuv.setOnClickListener {
            findNavController().navigate(R.id.allClientsFragment)
        }

    }

    private fun loginCheck() {
        /**
         * Logindan o'tganligini tekshiradi
         */
        MySharedPreference.init(binding.root.context)
        val token=MySharedPreference.token
        LoginCheck.token=token


    }

    override fun onResume() {
        super.onResume()
        /**
         * ButtonNavigation ni gone qilish
         */

        AppObject.binding.navigationLayout.visibility=View.GONE
        AppObject.binding.linerToolbar.visibility=View.GONE
    }
}