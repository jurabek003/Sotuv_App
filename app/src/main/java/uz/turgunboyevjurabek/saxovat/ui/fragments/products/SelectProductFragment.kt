package uz.turgunboyevjurabek.saxovat.ui.fragments.products

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
import uz.turgunboyevjurabek.saxovat.adapters.ProductAdapter.ProductAdapter
import uz.turgunboyevjurabek.saxovat.databinding.FragmentAllProductBinding
import uz.turgunboyevjurabek.saxovat.databinding.FragmentSelectProductBinding
import uz.turgunboyevjurabek.saxovat.model.madels.product.GetProductOfCategoriya
import uz.turgunboyevjurabek.saxovat.utils.AppObject
import uz.turgunboyevjurabek.saxovat.utils.Girgitton
import uz.turgunboyevjurabek.saxovat.utils.Status
import uz.turgunboyevjurabek.saxovat.vm.product.GetAllProductViewModel
import uz.turgunboyevjurabek.saxovat.vm.product.GetProductAsCategories


@AndroidEntryPoint
class SelectProductFragment : Fragment() {
    private val binding by lazy { FragmentSelectProductBinding.inflate(layoutInflater) }
    private val getProductAsCategories:GetProductAsCategories by viewModels()

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

    private fun getApiWorking() {

        getProductAsCategories.getData(Girgitton.category.toString()).observe(requireActivity(), Observer {
            when(it.status){
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), it.data.toString(), Toast.LENGTH_SHORT).show()
                    productAdapter= ProductAdapter()

                    val list=ArrayList<GetProductOfCategoriya>()
                    it.data?.let { it1 -> list.add(it1) }
                    productAdapter.updateData(list)
                    productAdapter.notifyDataSetChanged()
                    binding.rvProduct.adapter=productAdapter
                    binding.lottiProgress.visibility=View.GONE
                }
            }
            })

    }

override fun onResume() {
    super.onResume()
    AppObject.binding.linerToolbar.visibility=View.VISIBLE
    AppObject.binding.navigationLayout.visibility=View.GONE
    AppObject.binding.thtActionName.text="Mahsulotlar"

    AppObject.binding.imgBack.setOnClickListener {
        findNavController().popBackStack()
    }
}
}