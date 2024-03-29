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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import uz.turgunboyevjurabek.saxovat.adapters.ProductAdapter.GetAllProductAdapter
import uz.turgunboyevjurabek.saxovat.adapters.ProductAdapter.ProductSearchAdapter
import uz.turgunboyevjurabek.saxovat.databinding.DialogOrderBinding
import uz.turgunboyevjurabek.saxovat.databinding.FragmentAllProductBinding
import uz.turgunboyevjurabek.saxovat.model.madels.order.card.post.PostOrderCardRequest
import uz.turgunboyevjurabek.saxovat.model.madels.product.GetAllProductItem
import uz.turgunboyevjurabek.saxovat.utils.AppObject
import uz.turgunboyevjurabek.saxovat.utils.Girgitton
import uz.turgunboyevjurabek.saxovat.utils.Status
import uz.turgunboyevjurabek.saxovat.vm.order.PostOrderCardViewModel
import uz.turgunboyevjurabek.saxovat.vm.product.GetAllProductViewModel
import uz.turgunboyevjurabek.saxovat.vm.product.search.SearchProductViewModel

@AndroidEntryPoint
class AllProductFragment : Fragment(),GetAllProductAdapter.ItemClickOnProduct {
    private val binding by lazy { FragmentAllProductBinding.inflate(layoutInflater) }
    private val getAllProductViewModel: GetAllProductViewModel by viewModels()
    private val searchProductViewModel: SearchProductViewModel by viewModels()
    private val postOrderCardViewModel:PostOrderCardViewModel by viewModels()
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

        val lastSummaConst=getAllProductItem.lastPrice.toDouble()
        Log.d("no'l",lastSummaConst.toString())
       val formattedNumberConst=formatNumber(lastSummaConst)


        dialogOrderBinding.dialogItemName.text=getAllProductItem.name
        dialogOrderBinding.dialogItemNarxi.text= "$formattedNumberConst so'm"
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
                val formatNumber=formatNumber(lastSumma.toDouble())
                dialogOrderBinding.thtSumma.text= formatNumber
            }else{
                val formatNumberElse=formatNumber(summa.toDouble())
                dialogOrderBinding.editText1.setText("1.0")
                dialogOrderBinding.thtSumma.text =formatNumberElse

            }
        }
        dialogOrderBinding.dialogMinus.setOnClickListener {
            if (!dialogOrderBinding.editText1.text.isNullOrEmpty()  ) {
                minus1=dialogOrderBinding.editText1.text.toString().toDouble()
                if (minus1!=0.0){
                    minus1--
                    dialogOrderBinding.editText1.setText(minus1.toString())
                    val lastPrice=minus1*summa.toDouble()
                    val formattedMinusNumber=formatNumber(lastPrice.toDouble())
                    dialogOrderBinding.thtSumma.text=formattedMinusNumber
                }else{
                    dialogOrderBinding.editText1.text.clear()
                    dialogOrderBinding.thtSumma.text="0.0"
                }
            }
        }

        dialogOrderBinding.btnBuyurtma.setOnClickListener {
            if (!dialogOrderBinding.editText1.text.isNullOrEmpty()){
                dialog.cancel()
                val lastAmount=dialogOrderBinding.editText1.text.toString().toDouble().toInt()
                val productId=getAllProductItem.id
                val clientId=Girgitton.clientId
                val postOrderCardRequest= PostOrderCardRequest(productId,clientId!!,summa,lastAmount)
                postOrderCardWithDialog(postOrderCardRequest)
            }else{
                Toast.makeText(requireContext(), "buyurtma miqdorini kiritmadingiz", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun postOrderCardWithDialog(postOrderCardRequest: PostOrderCardRequest) {

        postOrderCardViewModel.postData(postOrderCardRequest).observe(requireActivity(), Observer {
            when(it.status){
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), "ehh ${it.message}", Toast.LENGTH_SHORT).show()
                }
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "post buldi", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun formatNumber(number: Double): String {
        val formattedNumber = if (number.toLong() in 10000..9999999999) {
            val formattedString = String.format("%,d", number.toLong())
            formattedString.replace(",", " ") // Vergulni bo'shatish
        }else{
            number.toString()
        }
        return formattedNumber
    }

}