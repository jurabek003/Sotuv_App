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
import uz.turgunboyevjurabek.saxovat.utils.Status
import uz.turgunboyevjurabek.saxovat.vm.clients.GetAllClientsViewModel
import javax.inject.Inject


@AndroidEntryPoint
class AllClientsFragment : Fragment(),AllClientsAdapter.OnClick {
    private val binding by lazy { FragmentAllClientsBinding.inflate(layoutInflater) }
    private val getAllClientsViewModel:GetAllClientsViewModel by viewModels()

    lateinit var allClientsAdapter: AllClientsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {



        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
        /**
         * ButtonNavigation ni gone qilish
         */
        AppObject.binding.btnNavigation.visibility=View.GONE
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

                Status.ERROR -> Toast.makeText(requireContext(), "vay ${it.message}", Toast.LENGTH_SHORT).show()
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

    override fun itemClick(getAllClients: GetAllClients, position: Int) {

        findNavController().navigate(R.id.purchaseFragment, bundleOf("keyName" to getAllClients.results[position].fullName))
    }
}