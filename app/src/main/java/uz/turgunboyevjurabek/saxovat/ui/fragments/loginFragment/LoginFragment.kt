package uz.turgunboyevjurabek.saxovat.ui.fragments.loginFragment

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vicmikhailau.maskededittext.MaskedWatcher
import dagger.hilt.android.AndroidEntryPoint
import uz.turgunboyevjurabek.saxovat.R
import uz.turgunboyevjurabek.saxovat.databinding.ChackNumberDialogBinding
import uz.turgunboyevjurabek.saxovat.databinding.FragmentLoginBinding
import uz.turgunboyevjurabek.saxovat.model.madels.login.LoginRequest
import uz.turgunboyevjurabek.saxovat.utils.Status
import uz.turgunboyevjurabek.saxovat.vm.login.LoginViewModel
import uz.turgunboyevjurabek.saxovat.vm.register.RegisterViewModel

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val binding by lazy { FragmentLoginBinding.inflate(layoutInflater)}
    private val loginViewModel:LoginViewModel by viewModels()
    private val registerViewModel:RegisterViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

       // login()
        register()

        return binding.root
    }

    private fun register() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

//    private fun login() {
//
//        binding.btnContinue.setOnClickListener {
//            val loginRequest=LoginRequest(binding.edtUserName.text.toString(),binding.edtPassword.text.toString())
//
//            loginViewModel.loginWorking(loginRequest).observe(requireActivity(), Observer {
//                when(it.status){
//                Status.LOADING -> {
//                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
//            }
//
//                Status.SUCCESS -> {
//                    Toast.makeText(requireContext(), "Uraa", Toast.LENGTH_SHORT).show()
//                    Toast.makeText(requireContext(), "${it.data}", Toast.LENGTH_SHORT).show()
//            }
//
//                Status.ERROR -> {
//                Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
//            }
//
//                }
//            })
//
//
//        }
//    }

}