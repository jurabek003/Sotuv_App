package uz.turgunboyevjurabek.saxovat.ui.fragments.Categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import uz.turgunboyevjurabek.saxovat.R
import uz.turgunboyevjurabek.saxovat.databinding.FragmentPurchaseBinding
import uz.turgunboyevjurabek.saxovat.utils.AppObject
import uz.turgunboyevjurabek.saxovat.utils.MySharedPreference
import uz.turgunboyevjurabek.saxovat.utils.Status
import uz.turgunboyevjurabek.saxovat.vm.Categories.GetAllCategoriesViewModel

@AndroidEntryPoint
class PurchaseFragment : Fragment() {
    private val binding by lazy { FragmentPurchaseBinding.inflate(layoutInflater) }
    private val getAllCategoriesViewModel:GetAllCategoriesViewModel by viewModels()
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

    private fun getApiWorking() {
        getAllCategoriesViewModel.getAllCategories(MySharedPreference.token!!)
            .observe(requireActivity(), Observer {
                when(it.status){
                    Status.LOADING -> Toast.makeText(requireContext(), "Loading at Purchase", Toast.LENGTH_SHORT).show()
                    Status.ERROR -> Toast.makeText(requireContext(), "Vay essiz ${it.message}", Toast.LENGTH_SHORT).show()
                    Status.SUCCESS -> Toast.makeText(requireContext(), "Yess ${it.data}", Toast.LENGTH_SHORT).show()
                }
            })

    }

    override fun onResume() {
        super.onResume()
        AppObject.binding.btnNavigation.visibility=View.VISIBLE

        getApiWorking()

    }
}