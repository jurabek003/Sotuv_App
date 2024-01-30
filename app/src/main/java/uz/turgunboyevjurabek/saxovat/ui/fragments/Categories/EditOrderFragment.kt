package uz.turgunboyevjurabek.saxovat.ui.fragments.Categories

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import uz.turgunboyevjurabek.saxovat.R
import uz.turgunboyevjurabek.saxovat.databinding.DialogOrderBinding
import uz.turgunboyevjurabek.saxovat.databinding.FragmentEditOrderBinding
import uz.turgunboyevjurabek.saxovat.model.madels.categories.karzinka.Result
import uz.turgunboyevjurabek.saxovat.model.madels.product.GetOneProduct
import uz.turgunboyevjurabek.saxovat.utils.AppObject
import uz.turgunboyevjurabek.saxovat.utils.Status
import uz.turgunboyevjurabek.saxovat.vm.product.one.GetOneProductViewModel

@AndroidEntryPoint
class EditOrderFragment : Fragment() {
    private val binding by lazy { FragmentEditOrderBinding.inflate(layoutInflater) }
    private val getOneProductViewModel: GetOneProductViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSelectProduct()
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
    private fun editData(result: Result, productItem: GetOneProduct) {


        // Avvalgi ma'lumotlarni tozalash
        binding.dialogItemName.text = ""
        binding.dialogItemNarxi.text = ""
        binding.qoldiq.text = ""
        binding.sanaVaqt.text = ""

        val lastSummaConst = result.price.toDouble()
        val formattedNumberConst = formatNumber(lastSummaConst)

        binding.dialogItemName.text = productItem.name.toString()
        binding.dialogItemNarxi.text = "$formattedNumberConst so'm"

        binding.qoldiq.text = "${result.quantity} ta"
        val sana = productItem.createAt?.substring(0..9)
        val vaqt = productItem.createAt?.substring(11..18)
        binding.sanaVaqt.text = "$sana/$vaqt"
        binding.thtSumma.text=result.totalPrice.toString()

        binding.editText1.setText(result.quantity.toDouble().toString())

        var summa = result.price
        var plus1: Double = 0.0
        var minus1: Double = 0.0

        /**
         * Birinchi edtText uchun
         */
        binding.dialogPlus.setOnClickListener {
            if (!binding.editText1.text.isNullOrEmpty()) {
                plus1 = binding.editText1.text.toString().toDouble()
                plus1++
                binding.editText1.setText(plus1.toString())
                var multiplier = plus1
                var lastSumma = summa.toDouble() * multiplier
                val formatNumber = formatNumber(lastSumma.toDouble())
                binding.thtSumma.text = formatNumber
            } else {
                val formatNumberElse = formatNumber(summa.toDouble())
                binding.editText1.setText("1.0")
                binding.thtSumma.text = formatNumberElse

            }
        }
        binding.dialogMinus.setOnClickListener {
            if (!binding.editText1.text.isNullOrEmpty()) {
                minus1 = binding.editText1.text.toString().toDouble()
                if (minus1 != 0.0) {
                    minus1--
                    binding.editText1.setText(minus1.toString())
                    val lastPrice = minus1 * summa.toDouble()
                    val formattedMinusNumber = formatNumber(lastPrice.toDouble())
                    binding.thtSumma.text = formattedMinusNumber
                } else {
                    binding.editText1.text.clear()
                    binding.thtSumma.text = "0.0"
                }
            }
        }

        //scope finish
    }

    private fun getSelectProduct(){
        val getData=arguments?.getSerializable("keyData") as Result

        var getOneProduct: GetOneProduct? = null
        if (getOneProduct == null){
            getOneProductViewModel.getOneProduct(getData.product.toString()).observe(requireActivity(), Observer {
                when(it.status){
                    Status.LOADING -> {
                        binding.shimmerLayout.startShimmer()
                    }
                    Status.ERROR -> {
                        Toast.makeText(requireContext(), "essiz one not ${it.message}", Toast.LENGTH_SHORT).show()
                        binding.shimmerLayout.hideShimmer()
                    }
                    Status.SUCCESS -> {
                        binding.shimmerLayout.hideShimmer()
                        it.data?.apply {
                            getOneProduct = GetOneProduct(amount,category,createAt,id,image,lastPrice,name,optomPrice,otherImages,sellPrice,updateAt)
                            editData(getData,getOneProduct!!)
                        }
                    }
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        AppObject.binding.thtActionName.text="Buyurtmani tahrirlash"
        AppObject.binding.navigationLayout.visibility=View.GONE
    }
}