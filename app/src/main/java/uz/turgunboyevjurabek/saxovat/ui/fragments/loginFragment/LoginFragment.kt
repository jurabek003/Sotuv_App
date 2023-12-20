package uz.turgunboyevjurabek.saxovat.ui.fragments.loginFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import uz.turgunboyevjurabek.saxovat.databinding.FragmentLoginBinding
import uz.turgunboyevjurabek.saxovat.model.madels.login.LoginRequest
import uz.turgunboyevjurabek.saxovat.model.madels.register.RegisterRequest
import uz.turgunboyevjurabek.saxovat.utils.Status
import uz.turgunboyevjurabek.saxovat.vm.login.LoginViewModel
import uz.turgunboyevjurabek.saxovat.vm.register.RegisterViewModel

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val binding by lazy { FragmentLoginBinding.inflate(layoutInflater)}
    private val loginViewModel:LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        login()


        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun login() {

        binding.btnContinue.setOnClickListener {
            val loginRequest=LoginRequest(binding.edtUserName.text.toString(),binding.edtPassword.text.toString())

            loginViewModel.loginWorking(loginRequest).observe(requireActivity(), Observer {
                when(it.status){
                Status.LOADING -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
            }

                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Uraa ${it.data}", Toast.LENGTH_SHORT).show()

            }

                Status.ERROR -> {
                Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
            }

                }
            })


        }
    }

}