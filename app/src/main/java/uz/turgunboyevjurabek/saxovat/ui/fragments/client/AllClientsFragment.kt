package uz.turgunboyevjurabek.saxovat.ui.fragments.client

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.turgunboyevjurabek.saxovat.R
import uz.turgunboyevjurabek.saxovat.adapters.clientsAdapter.AllClientsAdapter
import uz.turgunboyevjurabek.saxovat.databinding.FragmentAllClientsBinding
import uz.turgunboyevjurabek.saxovat.model.madels.clients.GetAllClients
import uz.turgunboyevjurabek.saxovat.model.madels.clients.ResultX
import uz.turgunboyevjurabek.saxovat.utils.AppObject
import uz.turgunboyevjurabek.saxovat.utils.Girgitton
import uz.turgunboyevjurabek.saxovat.utils.Status
import uz.turgunboyevjurabek.saxovat.vm.clients.GetAllClientsViewModel
import uz.turgunboyevjurabek.saxovat.vm.clients.OneTimeClientViewModel
import javax.inject.Inject


@AndroidEntryPoint
class AllClientsFragment : Fragment(),AllClientsAdapter.OnClick {
    private val binding by lazy { FragmentAllClientsBinding.inflate(layoutInflater) }
    private val getAllClientsViewModel:GetAllClientsViewModel by viewModels()
    private val oneTimeClientViewModel:OneTimeClientViewModel by viewModels()

    lateinit var allClientsAdapter: AllClientsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding.btnOnePay.setOnClickListener {
            findNavController().navigate(R.id.purchaseFragment)
            onePay()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        /**
         * ButtonNavigation ni gone qilish
         */

        AppObject.binding.linerToolbar.visibility=View.VISIBLE
        AppObject.binding.navigationLayout.visibility=View.GONE
        AppObject.binding.thtActionName.text="Klientlar"
        AppObject.binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getApiWorking()


    }

    private fun getApiWorking() {
        getAllClientsViewModel.getDept().observe(requireActivity(), Observer {
            when(it.status){
                Status.LOADING ->{
                    binding.lottiProgress.visibility=View.VISIBLE
                    binding.rvClients.visibility=View.GONE
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), "vay ${it.message}", Toast.LENGTH_SHORT).show()
                    binding.lottiProgress.visibility=View.GONE
                }
                Status.SUCCESS ->{
                    binding.lottiProgress.visibility=View.GONE
                    binding.rvClients.visibility=View.VISIBLE
                    allClientsAdapter= AllClientsAdapter(this)
                    val list=ArrayList<GetAllClients>()
                    list.add(it.data!!)
                    allClientsAdapter.updateData(list)
                    binding.rvClients.adapter=allClientsAdapter
                }
            }
        })
    }


    private fun onePay(){
        oneTimeClientViewModel.getOneTimeClient().observe(requireActivity(), Observer {
            when(it.status){
                Status.LOADING -> {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
                }
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), it.data.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun itemClick(getAllClients: GetAllClients, position: Int) {
        Girgitton.name=getAllClients.results[position].fullName
        Girgitton.getAllClients=getAllClients
        Girgitton.clientId=getAllClients.results[position].id
        findNavController().navigate(R.id.purchaseFragment)
    }
}