package uz.turgunboyevjurabek.saxovat.ui.fragments.products

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.icu.text.DecimalFormat
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import uz.turgunboyevjurabek.saxovat.R
import uz.turgunboyevjurabek.saxovat.adapters.ProductAdapter.GetAllProductAdapter
import uz.turgunboyevjurabek.saxovat.adapters.ProductAdapter.ProductAdapter
import uz.turgunboyevjurabek.saxovat.adapters.ProductAdapter.ProductSearchAdapter
import uz.turgunboyevjurabek.saxovat.databinding.DialogOrderBinding
import uz.turgunboyevjurabek.saxovat.databinding.FragmentAllProductBinding
import uz.turgunboyevjurabek.saxovat.model.madels.product.GetAllProduct
import uz.turgunboyevjurabek.saxovat.model.madels.product.GetAllProductItem
import uz.turgunboyevjurabek.saxovat.model.madels.product.GetProductOfCategoriya
import uz.turgunboyevjurabek.saxovat.utils.AppObject
import uz.turgunboyevjurabek.saxovat.utils.Status
import uz.turgunboyevjurabek.saxovat.vm.product.GetAllProductViewModel
import uz.turgunboyevjurabek.saxovat.vm.product.search.SearchProductViewModel

@AndroidEntryPoint
class AllProductFragment : Fragment(),GetAllProductAdapter.ItemClickOnProduct {
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
                    getAllProductAdapter= GetAllProductAdapter(this)
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
        AppObject.binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
//        try {
//            AppObject.binding.imgBack.setOnClickListener {
//                findNavController().popBackStack()
//            }
//        }catch (e:Exception){
//            Log.d("navXato",e.message.toString())
//            e.printStackTrace()
//            findNavController().popBackStack()
//        }
    }

    @SuppressLint("SetTextI18n")
    override fun selectItem(getAllProductItem: GetAllProductItem, position: Int) {
        val dialogOrderBinding=DialogOrderBinding.inflate(layoutInflater)
        val view=dialogOrderBinding.root

        dialogOrderBinding.dialogItemName.text=getAllProductItem.name
        dialogOrderBinding.dialogItemNarxi.text=getAllProductItem.lastPrice
        dialogOrderBinding.qoldiq.text=getAllProductItem.amount.toString()
        val sana=getAllProductItem.createAt.substring(0..9)
        val vaqt=getAllProductItem.createAt.substring(11..18)
        dialogOrderBinding.sanaVaqt.text= "$sana/$vaqt"

        val dialog=MaterialAlertDialogBuilder(requireContext())
            .setView(view)
            .setCancelable(false)
            .show()
        var summa=getAllProductItem.lastPrice
        var plus1:Double=0.0
        var minus1:Double=0.0
        var plus2:Double=0.0
        var minus2:Double=0.0
        dialogOrderBinding.btnOrtga.setOnClickListener {
            dialog.cancel()
        }

        /**
         * Birinchi edtText uchun
         */
        dialogOrderBinding.dialogPlus.setOnClickListener {
            if (!dialogOrderBinding.editText1.text.isNullOrEmpty()) {
                plus1 = dialogOrderBinding.editText1.text.toString().toDouble()
                plus1++
                dialogOrderBinding.editText1.setText(plus1.toString())
                var multiplier = plus1
                var lastSumma = summa.toDouble() * multiplier

                val formattedNumber = if (lastSumma.toLong() in 10000..9999999999) {
                    val formattedString = String.format("%,d", lastSumma.toLong())
                    formattedString.replace(",", " ") // Vergulni bo'shatish
                }else{
                    lastSumma.toString()
                }

                dialogOrderBinding.thtSumma.text= "$formattedNumber so'm"

            }else{
                dialogOrderBinding.editText1.setText("1.0")
                dialogOrderBinding.thtSumma.text = "$summa so'm"

            }
        }
        dialogOrderBinding.dialogMinus.setOnClickListener {
            if (!dialogOrderBinding.editText1.text.isNullOrEmpty()  ) {
                minus1=dialogOrderBinding.editText1.text.toString().toDouble()
                if (minus1!=0.0){
                    minus1--
                    dialogOrderBinding.editText1.setText(minus1.toString())
                }else{
                    dialogOrderBinding.editText1.text.clear()
                }
            }
        }
        /**
         * ikkinchi edtText uchun
         */
        dialogOrderBinding.dialogPlus2.setOnClickListener {
            if (!dialogOrderBinding.editText2.text.isNullOrEmpty()){
                plus2=dialogOrderBinding.editText2.text.toString().toDouble()
                plus2++
                dialogOrderBinding.editText2.setText(plus2.toString())
            }else{
                dialogOrderBinding.editText2.setText("1.0")
            }
        }
        dialogOrderBinding.dialogMinus2.setOnClickListener {
            if (!dialogOrderBinding.editText2.text.isNullOrEmpty()  ) {
                minus2=dialogOrderBinding.editText2.text.toString().toDouble()
                if (minus2!=0.0){
                    minus2--
                    dialogOrderBinding.editText2.setText(minus2.toString())
                }else{
                    dialogOrderBinding.editText2.text.clear()
                }
            }
        }

    }
//    fun formatNumber(number: Double): String {
//        val formatter = DecimalFormat("0.#########E0")
//        return formatter.format(number)
//    }

}