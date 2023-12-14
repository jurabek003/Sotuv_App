package uz.turgunboyevjurabek.saxovat.ui.fragments.loginFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vicmikhailau.maskededittext.MaskedWatcher
import uz.turgunboyevjurabek.saxovat.R
import uz.turgunboyevjurabek.saxovat.databinding.ChackNumberDialogBinding
import uz.turgunboyevjurabek.saxovat.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private val binding by lazy { FragmentLoginBinding.inflate(layoutInflater)}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edtWork()

       checkNumberDialog()


    }

    private fun checkNumberDialog() {
        val checkNumberDialogBinding=ChackNumberDialogBinding.inflate(layoutInflater)
        val dialog =MaterialAlertDialogBuilder(requireContext()).create()
        dialog.setCancelable(false)
        dialog.setView(checkNumberDialogBinding.root)



        binding.btnContinue.setOnClickListener {
            binding.mainLayout.alpha=0.1f
            dialog.show()
            checkNumberDialogBinding.btnYes.setOnClickListener {
                findNavController().navigate(R.id.codeFragment)
                dialog.cancel()
                binding.mainLayout.alpha=1f
            }
            checkNumberDialogBinding.btnNo.setOnClickListener {
                dialog.cancel()
                binding.mainLayout.alpha=1f
            }
            var number=binding.edtMasked.unMaskedText
            var number2="+998"
            var number3=number2+number
            Toast.makeText(requireContext(), "$number3", Toast.LENGTH_SHORT).show()
            checkNumberDialogBinding.thtNumber.text=number3.toString().trim()
        }


    }

    private fun edtWork() {


        binding.edtMasked.addTextChangedListener {

            if (binding.edtMasked.text.isNullOrEmpty()){
                binding.btnContinue.setBackgroundColor(resources.getColor(R.color.btn_false))
                binding.edtMasked.setBackgroundResource(R.drawable.shape_edt_false)
            }else{
                binding.btnContinue.setBackgroundColor(resources.getColor(R.color.btn_true))
                binding.edtMasked.setBackgroundResource(R.drawable.shape_edt_true)
            }
        }
    }
}