package uz.turgunboyevjurabek.saxovat.ui.fragments.Categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.turgunboyevjurabek.saxovat.R
import uz.turgunboyevjurabek.saxovat.databinding.FragmentPurchaseBinding
import uz.turgunboyevjurabek.saxovat.utils.AppObject

class PurchaseFragment : Fragment() {
    private val binding by lazy { FragmentPurchaseBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        AppObject.binding.btnNavigation.visibility=View.VISIBLE
    }
}