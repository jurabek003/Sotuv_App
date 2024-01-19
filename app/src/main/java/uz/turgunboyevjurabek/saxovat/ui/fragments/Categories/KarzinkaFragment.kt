package uz.turgunboyevjurabek.saxovat.ui.fragments.Categories

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
import uz.turgunboyevjurabek.saxovat.R
import uz.turgunboyevjurabek.saxovat.adapters.categoriesAdapter.karzinka.GetAllOrderByClientIdAdapter
import uz.turgunboyevjurabek.saxovat.databinding.FragmentKarzinkaBinding
import uz.turgunboyevjurabek.saxovat.utils.AppObject
import uz.turgunboyevjurabek.saxovat.utils.Girgitton
import uz.turgunboyevjurabek.saxovat.utils.Status
import uz.turgunboyevjurabek.saxovat.vm.Categories.karzinka.GetAllOrderCardViewModel

@AndroidEntryPoint
class KarzinkaFragment : Fragment() {
    private val getAllOrderCardViewModel:GetAllOrderCardViewModel  by viewModels()
    private val binding by lazy { FragmentKarzinkaBinding.inflate(layoutInflater) }
    private lateinit var getAllOrderByClientIdAdapter: GetAllOrderByClientIdAdapter
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

                    Toast.makeText(requireContext(), "yahshi ${it.data}", Toast.LENGTH_SHORT).show()
                    getAllOrderByClientIdAdapter=GetAllOrderByClientIdAdapter()
                    getAllOrderByClientIdAdapter.updateData(it.data?.results)
                    getAllOrderByClientIdAdapter.notifyDataSetChanged()
                    binding.rvAllOrderCard.adapter=getAllOrderByClientIdAdapter
                }
            }
        })

    }

    override fun onResume() {
        super.onResume()
        //AppObject.binding.btnNavigation.visibility=View.VISIBLE
        AppObject.binding.navigationLayout.visibility=View.VISIBLE
        AppObject.binding.thtActionName.text="Karzinka"
        AppObject.binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }


    }
}