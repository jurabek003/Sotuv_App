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
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
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
import uz.turgunboyevjurabek.saxovat.vm.order.DeleteOrderViewModel
import uz.turgunboyevjurabek.saxovat.vm.product.one.GetOneProductViewModel

@OptIn(DelicateCoroutinesApi::class)
@AndroidEntryPoint
class KarzinkaFragment : Fragment(),GetAllOrderByClientIdAdapter.PutItem {
    private val getAllOrderCardViewModel:GetAllOrderCardViewModel  by viewModels()
    private val deleteOrderViewModel:DeleteOrderViewModel  by viewModels()
    private val binding by lazy { FragmentKarzinkaBinding.inflate(layoutInflater) }
    private lateinit var getAllOrderByClientIdAdapter: GetAllOrderByClientIdAdapter
    private lateinit var snackbar2:Snackbar

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
        forNavigation()
    }

    private fun forNavigation() {
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
        findNavController().navigate(R.id.editOrderFragment, bundleOf("keyData" to result))
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun deleteOrderItem(result: Result, position: Int) {
        snackbar2= Snackbar.make(binding.mainLayoutAtKarzinkaFragment, "Buyurtma o'chirildi",3000)

        deleteOrderViewModel.deleteItem(result.id).observe(requireActivity(), Observer {
            when(it.status){
                Status.LOADING -> {
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                }
                Status.SUCCESS -> {
                    getAllOrderByClientIdAdapter.list.remove(result)
                    getAllOrderByClientIdAdapter.notifyDataSetChanged()
                    snackbar2.show()
                }

            }
        })
    }
//    private val selectedProductLiveData = MutableLiveData<GetOneProduct?>()

//    private val selectedProduct: LiveData<GetOneProduct?> get() = selectedProductLiveData

}
