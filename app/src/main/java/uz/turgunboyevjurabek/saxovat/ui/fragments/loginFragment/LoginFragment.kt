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
import androidx.navigation.fragment.NavHostFragment
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
       // keyBoardTest()

    }

    private fun checkNumberDialog() {
        val checkNumberDialogBinding=ChackNumberDialogBinding.inflate(layoutInflater)
        val dialog =MaterialAlertDialogBuilder(requireContext()).create()
        dialog.setCancelable(false)
        dialog.setView(checkNumberDialogBinding.root)


        binding.btnContinue.setOnClickListener {
            var code =binding.countryCode.selectedCountryCode
            var number=binding.edtMasked.unMaskedText
            var number2=binding.edtTextNumber.text
            var sum=code+number2
            Toast.makeText(requireContext(), "$sum", Toast.LENGTH_SHORT).show()



            binding.mainLayout.alpha=0.1f
            dialog.show()
            checkNumberDialogBinding.btnYes.setOnClickListener {
                findNavController().navigate(R.id.codeFragment, bundleOf())
//                val action=
                dialog.cancel()
                binding.mainLayout.alpha=1f
            }
            checkNumberDialogBinding.btnNo.setOnClickListener {
                dialog.cancel()
                binding.mainLayout.alpha=1f
            }
            //checkNumberDialogBinding.thtNumber.text=number3.toString().trim()
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
    private fun keyBoardTest(){
        val edt=binding.edtMasked
        val button =binding.btnContinue

        edt.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            edt.getWindowVisibleDisplayFrame(rect)
            val screenHeight = edt.rootView.height
            val keypadHeight = screenHeight - rect.bottom

            if (keypadHeight > screenHeight * 0.25) { // Klaviatura ochilgan
                button.translationY = (-keypadHeight).toFloat()
            } else { // Klaviatura yopilgan
                button.translationY = 0f
            }
        }


    }
}