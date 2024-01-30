package uz.turgunboyevjurabek.saxovat.ui.fragments.Categories

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.turgunboyevjurabek.saxovat.R
import uz.turgunboyevjurabek.saxovat.adapters.categoriesAdapter.karzinka.GetAllOrderByClientIdAdapter
import uz.turgunboyevjurabek.saxovat.databinding.DialogOrderBinding
import uz.turgunboyevjurabek.saxovat.databinding.FragmentKarzinkaBinding
import uz.turgunboyevjurabek.saxovat.model.madels.categories.karzinka.Result
import uz.turgunboyevjurabek.saxovat.model.madels.product.GetAllProductItem
import uz.turgunboyevjurabek.saxovat.model.madels.product.GetOneProduct
import uz.turgunboyevjurabek.saxovat.utils.AppObject
import uz.turgunboyevjurabek.saxovat.utils.Girgitton
import uz.turgunboyevjurabek.saxovat.utils.Status
import uz.turgunboyevjurabek.saxovat.vm.Categories.karzinka.GetAllOrderCardViewModel
import uz.turgunboyevjurabek.saxovat.vm.product.one.GetOneProductViewModel

@OptIn(DelicateCoroutinesApi::class)
@AndroidEntryPoint
class KarzinkaFragment : Fragment(),GetAllOrderByClientIdAdapter.PutItem {
    private val getAllOrderCardViewModel:GetAllOrderCardViewModel  by viewModels()
    private val binding by lazy { FragmentKarzinkaBinding.inflate(layoutInflater) }
    private lateinit var getAllOrderByClientIdAdapter: GetAllOrderByClientIdAdapter
    private val getOneProductViewModel:GetOneProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Girgitton.boolean=true
    }

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
        val id= if (Girgitton.clientId!=null) Girgitton.clientId else 0
        getAllOrderCardViewModel.getAllApiOrder(id!!).observe(requireActivity(), Observer {
            when(it.status){
                Status.LOADING -> {
                    binding.lottiProgressInAllOrder.visibility=View.VISIBLE
                    binding.rvAllOrderCard.visibility=View.GONE
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), "Obbo ${it.message}", Toast.LENGTH_SHORT).show()
                    binding.lottiProgressInAllOrder.visibility=View.GONE
                    binding.rvAllOrderCard.visibility=View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.lottiProgressInAllOrder.visibility=View.GONE
                    binding.rvAllOrderCard.visibility=View.VISIBLE
                    getAllOrderByClientIdAdapter=GetAllOrderByClientIdAdapter(this)
                    getAllOrderByClientIdAdapter.updateData(it.data?.results)
                    getAllOrderByClientIdAdapter.notifyDataSetChanged()
                    binding.rvAllOrderCard.adapter=getAllOrderByClientIdAdapter
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()

        AppObject.binding.navigationLayout.visibility=View.VISIBLE
        AppObject.binding.thtActionName.text="Karzinka"
        AppObject.binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        val binding2=AppObject.binding

        // card 1 uchun
        binding2.tht1.textSize=16f
        binding2.card1.strokeWidth=1
        binding2.card1.strokeColor=resources.getColor(R.color.btn_false)
        // card 2 uchun
        binding2.tht2.textSize=18f
        binding2.card2.strokeWidth=3
        binding2.card2.strokeColor=resources.getColor(R.color.btn_true)


    }

    @SuppressLint("SetTextI18n")
    override fun putItemOrder(result: Result, position: Int) {

        val productItem=getSelectProduct(result.product)
        productItem?.let { showDialogDetail(result, it) }
    }

    @SuppressLint("SetTextI18n")
    private fun showDialogDetail(result: Result, productItem: GetOneProduct) {

        val dialogOrderBinding = DialogOrderBinding.inflate(layoutInflater)
        val view = dialogOrderBinding.root

        // Avvalgi ma'lumotlarni tozalash
        dialogOrderBinding.dialogItemName.text = ""
        dialogOrderBinding.dialogItemNarxi.text = ""
        dialogOrderBinding.qoldiq.text = ""
        dialogOrderBinding.sanaVaqt.text = ""

        val lastSummaConst = result.price.toDouble()
        val formattedNumberConst = formatNumber(lastSummaConst)

        dialogOrderBinding.dialogItemName.text = productItem.name.toString()
        dialogOrderBinding.dialogItemNarxi.text = "$formattedNumberConst so'm"

        dialogOrderBinding.qoldiq.text = "${result.quantity} ta"
        val sana = productItem.createAt?.substring(0..9)
        val vaqt = productItem.createAt?.substring(11..18)
        dialogOrderBinding.sanaVaqt.text = "$sana/$vaqt"

        try {
            val dialog = MaterialAlertDialogBuilder(requireContext())
                .setView(view)
                .show()

            dialogOrderBinding.btnOrtga.setOnClickListener {
                dialog.cancel()
            }

        } catch (e: Exception) {
            Log.d("ushla", e.message.toString())
        }

        var summa = result.price
        var plus1: Double = 0.0
        var minus1: Double = 0.0
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
                val formatNumber = formatNumber(lastSumma.toDouble())
                dialogOrderBinding.thtSumma.text = formatNumber
            } else {
                val formatNumberElse = formatNumber(summa.toDouble())
                dialogOrderBinding.editText1.setText("1.0")
                dialogOrderBinding.thtSumma.text = formatNumberElse

            }
        }
        dialogOrderBinding.dialogMinus.setOnClickListener {
            if (!dialogOrderBinding.editText1.text.isNullOrEmpty()) {
                minus1 = dialogOrderBinding.editText1.text.toString().toDouble()
                if (minus1 != 0.0) {
                    minus1--
                    dialogOrderBinding.editText1.setText(minus1.toString())
                    val lastPrice = minus1 * summa.toDouble()
                    val formattedMinusNumber = formatNumber(lastPrice.toDouble())
                    dialogOrderBinding.thtSumma.text = formattedMinusNumber
                } else {
                    dialogOrderBinding.editText1.text.clear()
                    dialogOrderBinding.thtSumma.text = "0.0"
                }
            }
        }

        //scope finish
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


    private fun getSelectProduct(id: Int):GetOneProduct?{
        var getOneProduct: GetOneProduct? = null
        if (getOneProduct == null){
            getOneProductViewModel.getOneProduct(id.toString()).observe(requireActivity(), Observer {
                when(it.status){
                    Status.LOADING -> {

                    }
                    Status.ERROR -> {
                        Toast.makeText(requireContext(), "essiz one not ${it.message}", Toast.LENGTH_SHORT).show()
                    }
                    Status.SUCCESS -> {
                        it.data?.apply {
                            getOneProduct= GetOneProduct(amount,category,createAt,id,image,lastPrice,name,optomPrice,otherImages,sellPrice,updateAt)
                        }
                    }
                }
            })
        }

        return getOneProduct
    }
//    private val selectedProductLiveData = MutableLiveData<GetOneProduct?>()
//    private val selectedProduct: LiveData<GetOneProduct?> get() = selectedProductLiveData
//
}
