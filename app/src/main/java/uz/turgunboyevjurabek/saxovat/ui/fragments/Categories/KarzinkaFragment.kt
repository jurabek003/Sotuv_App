package uz.turgunboyevjurabek.saxovat.ui.fragments.Categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.turgunboyevjurabek.saxovat.R
import uz.turgunboyevjurabek.saxovat.databinding.FragmentKarzinkaBinding
import uz.turgunboyevjurabek.saxovat.utils.AppObject

class KarzinkaFragment : Fragment() {
    private val binding by lazy { FragmentKarzinkaBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        //AppObject.binding.btnNavigation.visibility=View.VISIBLE
        AppObject.binding.navigationLayout.visibility=View.VISIBLE
    }
}