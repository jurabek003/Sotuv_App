package uz.turgunboyevjurabek.saxovat.ui.fragments.products

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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
import uz.turgunboyevjurabek.saxovat.adapters.ProductAdapter.GetAllProductAdapter
import uz.turgunboyevjurabek.saxovat.adapters.ProductAdapter.ProductAdapter
import uz.turgunboyevjurabek.saxovat.adapters.ProductAdapter.ProductSearchAdapter
import uz.turgunboyevjurabek.saxovat.databinding.FragmentAllProductBinding
import uz.turgunboyevjurabek.saxovat.model.madels.product.GetAllProduct
import uz.turgunboyevjurabek.saxovat.model.madels.product.GetProductOfCategoriya
import uz.turgunboyevjurabek.saxovat.utils.AppObject
import uz.turgunboyevjurabek.saxovat.utils.Status
import uz.turgunboyevjurabek.saxovat.vm.product.GetAllProductViewModel
import uz.turgunboyevjurabek.saxovat.vm.product.search.SearchProductViewModel

@AndroidEntryPoint
class AllProductFragment : Fragment() {
    private val binding by lazy { FragmentAllProductBinding.inflate(layoutInflater) }
    private val getAllProductViewModel: GetAllProductViewModel by viewModels()
    private val searchProductViewModel: SearchProductViewModel by viewModels()

    private lateinit var getAllProductAdapter: GetAllProductAdapter
    private lateinit var productSearchAdapter: ProductSearchAdapter


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
        edtSearch()

    }

    private fun getApiWorking() {
        getAllProductViewModel.getApiProduct().observe(requireActivity(), Observer {
            when(it.status){
                Status.LOADING -> {
                    binding.lottiProgressInAllProduct.visibility=View.VISIBLE
                    binding.rvSearch.visibility=View.GONE
                    binding.rvAllProduct.visibility=View.VISIBLE
                }
                Status.ERROR -> {
                    binding.lottiProgressInAllProduct.visibility=View.GONE
                }
                Status.SUCCESS -> {
                    binding.lottiProgressInAllProduct.visibility=View.GONE
                    getAllProductAdapter= GetAllProductAdapter()
                    it.data?.let { it1 -> getAllProductAdapter.updateData(it1) }
                    getAllProductAdapter.notifyDataSetChanged()
                    binding.rvAllProduct.adapter=getAllProductAdapter
                }
            }
        })
    }

    private fun searchResult(search: String){
        searchProductViewModel.getSearchData(search).observe(requireActivity(), Observer {

            when(it.status){
                Status.LOADING -> {
                    binding.lottiProgressInAllProduct.visibility=View.VISIBLE
                    binding.rvAllProduct.visibility=View.GONE
                    binding.rvSearch.visibility=View.VISIBLE
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), "yomon ${it.message}", Toast.LENGTH_SHORT).show()
                    binding.lottiProgressInAllProduct.visibility=View.GONE
                }
                Status.SUCCESS -> {
//                    Toast.makeText(requireContext(), "yashi ${it.data}", Toast.LENGTH_SHORT).show()
                    binding.lottiProgressInAllProduct.visibility=View.GONE
                    productSearchAdapter= ProductSearchAdapter()
                    it.data?.let { it1 -> productSearchAdapter.updateData(it1) }
                    productSearchAdapter.notifyDataSetChanged()
                    binding.rvSearch.adapter=productSearchAdapter
                    Log.d("searchJ",it.data.toString())
                }
            }
        })
    }

    private fun edtSearch(){

        binding.searchView.setOnQueryTextListener(object:androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()){
                    searchResult(newText)
                }else{
                    getApiWorking()
                }
                return true
            }
        })
    }

    override fun onResume() {
        super.onResume()
        AppObject.binding.thtActionName.text="Hamma Mahsullotlar"
        AppObject.binding.linerToolbar.visibility=View.VISIBLE
        AppObject.binding.navigationLayout.visibility=View.GONE
        try {
            AppObject.binding.imgBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }catch (e:Exception){
            Log.d("navXato",e.message.toString())
            e.printStackTrace()
        }
    }
}