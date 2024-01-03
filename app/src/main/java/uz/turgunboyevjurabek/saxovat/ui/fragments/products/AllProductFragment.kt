package uz.turgunboyevjurabek.saxovat.ui.fragments.products

import android.annotation.SuppressLint
import android.net.Uri
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
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import uz.turgunboyevjurabek.saxovat.R
import uz.turgunboyevjurabek.saxovat.adapters.ProductAdapter.ProductAdapter
import uz.turgunboyevjurabek.saxovat.databinding.FragmentAllProductBinding
import uz.turgunboyevjurabek.saxovat.model.madels.product.GetAllProduct
import uz.turgunboyevjurabek.saxovat.utils.AppObject
import uz.turgunboyevjurabek.saxovat.utils.Girgitton
import uz.turgunboyevjurabek.saxovat.utils.Status
import uz.turgunboyevjurabek.saxovat.vm.product.GetAllProductViewModel


@AndroidEntryPoint
class AllProductFragment : Fragment() {
    private val binding by lazy { FragmentAllProductBinding.inflate(layoutInflater) }
    private val getAllProductViewModel:GetAllProductViewModel by viewModels()
    private lateinit var  productAdapter:ProductAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getApiWorking()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getApiWorking() {
        var category=0
        if (Girgitton.category!=null){
            category= Girgitton.category!!
        }
        Toast.makeText(requireContext(), "$category", Toast.LENGTH_SHORT).show()

        getAllProductViewModel.getApiProduct(category).observe(requireActivity(), Observer{
            when(it.status){
                Status.LOADING -> {
                    Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                    binding.lottiProgress.visibility=View.VISIBLE
                }
                Status.ERROR ->{
                    Toast.makeText(requireContext(), "vay ${it.message}", Toast.LENGTH_SHORT).show()
                    binding.lottiProgress.visibility=View.GONE
                }
                Status.SUCCESS ->{
                    Toast.makeText(requireContext(), "ura ${it.data}", Toast.LENGTH_SHORT).show()
                    productAdapter= ProductAdapter()
                    productAdapter.updateData(it.data!!)
                    productAdapter.notifyDataSetChanged()
                    binding.rvProduct.adapter=productAdapter
                    binding.lottiProgress.visibility=View.GONE
                    val list=GetAllProduct()
                    list.addAll(it.data)
                    Log.d("rasms","${list[0].image}")
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        AppObject.binding.btnNavigation.visibility=View.GONE
        AppObject.binding.linerToolbar.visibility=View.VISIBLE
        AppObject.binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}