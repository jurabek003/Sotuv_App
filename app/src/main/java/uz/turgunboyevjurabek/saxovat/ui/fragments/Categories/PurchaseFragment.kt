package uz.turgunboyevjurabek.saxovat.ui.fragments.Categories

import android.annotation.SuppressLint
import android.icu.text.StringSearch
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.turgunboyevjurabek.saxovat.R
import uz.turgunboyevjurabek.saxovat.adapters.ProductAdapter.ProductSearchAdapter
import uz.turgunboyevjurabek.saxovat.adapters.categoriesAdapter.PurchaseAdapter
import uz.turgunboyevjurabek.saxovat.databinding.FragmentPurchaseBinding
import uz.turgunboyevjurabek.saxovat.model.madels.categories.CategoriesResponseItem
import uz.turgunboyevjurabek.saxovat.utils.AppObject
import uz.turgunboyevjurabek.saxovat.utils.Girgitton
import uz.turgunboyevjurabek.saxovat.utils.Status
import uz.turgunboyevjurabek.saxovat.utils.Status.*
import uz.turgunboyevjurabek.saxovat.vm.Categories.GetAllCategoriesViewModel
import uz.turgunboyevjurabek.saxovat.vm.product.search.SearchProductViewModel

@AndroidEntryPoint
class PurchaseFragment : Fragment(),PurchaseAdapter.OnClickAtPurchase {
    private val binding by lazy { FragmentPurchaseBinding.inflate(layoutInflater) }
    private val getAllCategoriesViewModel:GetAllCategoriesViewModel by viewModels()
    private lateinit var purchaseAdapter: PurchaseAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Girgitton.boolean=false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefresh()
        getApiWorking()

        binding.searchView.setOnClickListener {
            findNavController().navigate(R.id.allProductFragment2)
        }

    }
    @SuppressLint("ResourceAsColor")
    private fun swipeRefresh() {

        binding.refreshLayout.setColorSchemeColors(R.color.btn_false,R.color.btn_true,R.color.white)
        binding.refreshLayout.setOnRefreshListener {
            getApiWorking()
            Handler(Looper.getMainLooper()).postDelayed({
                binding.refreshLayout.isRefreshing=false
            },1500)
        }
    }

    /**
     * Hamma katego'riyalarni oluvchi funksiya
     */
    private fun getApiWorking() {
        getAllCategoriesViewModel.getAllCategories()
            .observe(requireActivity(), Observer {
                when(it.status){
                    LOADING ->{

                        Toast.makeText(requireContext(), "Loading at Purchase", Toast.LENGTH_SHORT).show()
                        binding.lottiProgressInPurchase.visibility=View.VISIBLE
                        binding.rvCategories.visibility=View.VISIBLE

                    }
                    ERROR ->{
                        Toast.makeText(requireContext(), "Vay essiz ${it.message}", Toast.LENGTH_SHORT).show()
                        binding.lottiProgressInPurchase.visibility=View.GONE
                    }
                    SUCCESS -> {

                        purchaseAdapter= PurchaseAdapter(this)
                        purchaseAdapter.updateData(it.data!!)
                        binding.rvCategories.adapter=purchaseAdapter

                        binding.lottiProgressInPurchase.visibility=View.GONE
                        binding.thtClient.text =Girgitton.name.toString()

                    }
                }
            })

    }


    override fun onResume() {
        super.onResume()
        AppObject.binding.linerToolbar.visibility=View.VISIBLE
        AppObject.binding.navigationLayout.visibility=View.VISIBLE
        AppObject.binding.thtActionName.text="Katego'riyalar"
        AppObject.binding.imgBack.setOnClickListener {
         //   findNavController().navigate(R.id.allClientsFragment)
            findNavController().popBackStack()
        }


    }

    override fun itemClickAtPurchase(
        categoriesResponseItem: CategoriesResponseItem,
        position: Int,
    ) {
        findNavController().navigate(R.id.selectProductFragment)
        Girgitton.category=categoriesResponseItem.id
    }
}