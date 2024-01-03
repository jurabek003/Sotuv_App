package uz.turgunboyevjurabek.saxovat.ui.fragments.Categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.turgunboyevjurabek.saxovat.R
import uz.turgunboyevjurabek.saxovat.adapters.categoriesAdapter.PurchaseAdapter
import uz.turgunboyevjurabek.saxovat.databinding.FragmentPurchaseBinding
import uz.turgunboyevjurabek.saxovat.model.madels.categories.CategoriesResponseItem
import uz.turgunboyevjurabek.saxovat.utils.AppObject
import uz.turgunboyevjurabek.saxovat.utils.Girgitton
import uz.turgunboyevjurabek.saxovat.utils.Status
import uz.turgunboyevjurabek.saxovat.vm.Categories.GetAllCategoriesViewModel

@AndroidEntryPoint
class PurchaseFragment : Fragment(),PurchaseAdapter.OnClickAtPurchase {
    private val binding by lazy { FragmentPurchaseBinding.inflate(layoutInflater) }
    private val getAllCategoriesViewModel:GetAllCategoriesViewModel by viewModels()
    private lateinit var purchaseAdapter: PurchaseAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getApiWorking()
    }

    private fun getApiWorking() {
        getAllCategoriesViewModel.getAllCategories()
            .observe(requireActivity(), Observer {
                when(it.status){
                    Status.LOADING -> Toast.makeText(requireContext(), "Loading at Purchase", Toast.LENGTH_SHORT).show()
                    Status.ERROR -> Toast.makeText(requireContext(), "Vay essiz ${it.message}", Toast.LENGTH_SHORT).show()
                    Status.SUCCESS -> {

                        purchaseAdapter= PurchaseAdapter(this)
                        purchaseAdapter.updateData(it.data!!)
                        binding.rvCategories.adapter=purchaseAdapter



                        binding.thtClient.text =Girgitton.name.toString()

                    }
                }
            })

    }

    override fun onResume() {
        super.onResume()
        //AppObject.binding.btnNavigation.visibility=View.VISIBLE
        AppObject.binding.linerToolbar.visibility=View.VISIBLE
        AppObject.binding.navigationLayout.visibility=View.VISIBLE
        AppObject.binding.thtActionName.text="Katego'riyalar"


    }

    override fun itemClickAtPurchase(
        categoriesResponseItem: CategoriesResponseItem,
        position: Int,
    ) {
        findNavController().navigate(R.id.allProductFragment)
        Girgitton.category=categoriesResponseItem.id
    }
}