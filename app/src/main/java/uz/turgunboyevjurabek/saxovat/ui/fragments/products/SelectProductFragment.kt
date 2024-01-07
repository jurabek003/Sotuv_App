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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import uz.turgunboyevjurabek.saxovat.adapters.ProductAdapter.ProductAdapter
import uz.turgunboyevjurabek.saxovat.databinding.DialogOrderBinding
import uz.turgunboyevjurabek.saxovat.databinding.FragmentAllProductBinding
import uz.turgunboyevjurabek.saxovat.databinding.FragmentSelectProductBinding
import uz.turgunboyevjurabek.saxovat.model.madels.product.GetProductOfCategoriya
import uz.turgunboyevjurabek.saxovat.utils.AppObject
import uz.turgunboyevjurabek.saxovat.utils.Girgitton
import uz.turgunboyevjurabek.saxovat.utils.Status
import uz.turgunboyevjurabek.saxovat.vm.product.GetAllProductViewModel
import uz.turgunboyevjurabek.saxovat.vm.product.GetProductAsCategories


@AndroidEntryPoint
class SelectProductFragment : Fragment(),ProductAdapter.ItemSelect {
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
                    productAdapter= ProductAdapter(this)

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
    AppObject.binding.thtActionName.text="Tanlangan mahsulot"

    AppObject.binding.imgBack.setOnClickListener {
        findNavController().popBackStack()
    }

}

    override fun itemClick(getProductOfCategoriya: GetProductOfCategoriya, position: Int) {
        val dialogOrderBinding= DialogOrderBinding.inflate(layoutInflater)
        val view=dialogOrderBinding.root

        dialogOrderBinding.dialogItemName.text=getProductOfCategoriya.name
        dialogOrderBinding.dialogItemNarxi.text=getProductOfCategoriya.lastPrice
        dialogOrderBinding.qoldiq.text=getProductOfCategoriya.amount.toString()

        val sana=getProductOfCategoriya.createAt.substring(0..9)
        val vaqt=getProductOfCategoriya.createAt.substring(11..18)
        dialogOrderBinding.sanaVaqt.text= "$sana/$vaqt"


        val dialog= MaterialAlertDialogBuilder(requireContext())
            .setView(view)
            .setCancelable(false)
            .show()


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
            if (!dialogOrderBinding.editText1.text.isNullOrEmpty()){
                plus1=dialogOrderBinding.editText1.text.toString().toDouble()
                plus1++
                dialogOrderBinding.editText1.setText(plus1.toString())
            }else{
                dialogOrderBinding.editText1.setText("1.0")
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
}