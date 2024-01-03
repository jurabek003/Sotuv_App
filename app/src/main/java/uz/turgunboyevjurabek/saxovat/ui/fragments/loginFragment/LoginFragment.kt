package uz.turgunboyevjurabek.saxovat.ui.fragments.loginFragment

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
import uz.turgunboyevjurabek.saxovat.databinding.FragmentLoginBinding
import uz.turgunboyevjurabek.saxovat.model.madels.login.LoginRequest
import uz.turgunboyevjurabek.saxovat.model.madels.register.RegisterRequest
import uz.turgunboyevjurabek.saxovat.utils.MySharedPreference
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



        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /**
         * Token ni tekshirish
         */
        MySharedPreference.init(binding.root.context)
        if (MySharedPreference.token.equals("null") || MySharedPreference.token==null || MySharedPreference.token.isNullOrEmpty() ){
            login()
        }else{
            findNavController().popBackStack()
            findNavController().navigate(R.id.homeFragment)
        }

    }

    private fun login() {



        binding.btnContinue.setOnClickListener {
            if (!binding.edtUserName.text.isNullOrEmpty() && !binding.edtPassword.text.isNullOrEmpty()){
                val loginRequest=LoginRequest(binding.edtUserName.text.toString(),binding.edtPassword.text.toString())

                loginViewModel.loginWorking(loginRequest).observe(requireActivity(), Observer {
                    when(it.status){
                        Status.LOADING -> {
                            Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                            binding.imgLogin.visibility=View.GONE
                            binding.lottiProgressInLogin.visibility=View.VISIBLE

                            binding.btnContinue.isEnabled=false
                            binding.edtPassword.isEnabled=false
                            binding.edtUserName.isEnabled=false
                        }

                        Status.SUCCESS -> {
                            Toast.makeText(requireContext(), "Uraa ${it.data}", Toast.LENGTH_SHORT).show()
                            MySharedPreference.token=it.data?.token!!
                           // findNavController().popBackStack()
                            findNavController().navigate(R.id.homeFragment)

                            binding.imgLogin.visibility=View.VISIBLE
                            binding.lottiProgressInLogin.visibility=View.GONE

                            binding.btnContinue.isEnabled=true
                            binding.edtPassword.isEnabled=true
                            binding.edtUserName.isEnabled=true
                        }

                        Status.ERROR -> {
                            Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()

                            binding.imgLogin.visibility=View.VISIBLE
                            binding.lottiProgressInLogin.visibility=View.GONE


                            binding.btnContinue.isEnabled=true
                            binding.edtPassword.isEnabled=true
                            binding.edtUserName.isEnabled=true
                        }

                    }
                })

            }



        }
    }

}