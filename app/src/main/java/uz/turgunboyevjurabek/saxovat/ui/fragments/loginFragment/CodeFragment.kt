package uz.turgunboyevjurabek.saxovat.ui.fragments.loginFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.turgunboyevjurabek.saxovat.R
import uz.turgunboyevjurabek.saxovat.databinding.FragmentCodeBinding

class CodeFragment : Fragment() {
    private val binding by lazy { FragmentCodeBinding.inflate(layoutInflater)}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        binding.pinView.setAnimationEnable(true)
        binding.pinView.isPasswordHidden=true

        return binding.root
    }
}