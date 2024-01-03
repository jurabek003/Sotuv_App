package uz.turgunboyevjurabek.saxovat.ui.fragments.loginFragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Rect
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.turgunboyevjurabek.saxovat.R
import uz.turgunboyevjurabek.saxovat.databinding.FragmentCodeBinding
import uz.turgunboyevjurabek.saxovat.model.madels.register.RegisterRequest
import uz.turgunboyevjurabek.saxovat.utils.LoginCheck
import uz.turgunboyevjurabek.saxovat.utils.Status
import uz.turgunboyevjurabek.saxovat.vm.register.RegisterViewModel
import javax.annotation.meta.When
@AndroidEntryPoint
class CodeFragment : Fragment() {
    private val binding by lazy { FragmentCodeBinding.inflate(layoutInflater)}
    var count:Int=59
    private lateinit var handler:Handler
    private val registerViewModel:RegisterViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment


        return binding.root
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.pinView.setAnimationEnable(true)
        binding.pinView.isPasswordHidden=true

        edtWork()
        thtInfo()

//        handler= Handler(Looper.getMainLooper())
//        handler.postDelayed(runnable,1000)


        binding.btnContinue.setOnClickListener {
            if (!binding.pinView.text.isNullOrEmpty()){
                LoginCheck.token=binding.pinView.text.toString()
            }
        }

        if (!LoginCheck.token.isNullOrEmpty()){
            findNavController().navigate(R.id.homeFragment)
        }

        keyBoardTest()
        apiWorking()
    }

    private fun apiWorking() {
        val registerRequest=RegisterRequest("abs","a","a","7777","+998902223344")
        registerViewModel.registerWorking(registerRequest).observe(requireActivity(), Observer {
            when(it.status){
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), "essis ${it.message}", Toast.LENGTH_SHORT).show()
                }
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "o'tti ${it.data}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun thtInfo() {
        val textView= binding.thtInfo
        val getNumber=arguments?.getString("key_number")

        val text="sms kod ${getNumber.toString()} raqamiga yuborildi"
        val spannable = SpannableStringBuilder(text)

        val blackColorSpan = ForegroundColorSpan(resources.getColor(R.color.black))
        val boldStyleSpan = StyleSpan(Typeface.BOLD)
        spannable.setSpan(blackColorSpan, 8, 21, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(boldStyleSpan, 8, 21, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text=spannable
    }

    private fun edtWork() {

        binding.pinView.addTextChangedListener {

            if (binding.pinView.text.isNullOrEmpty()){
                binding.btnContinue.setBackgroundColor(resources.getColor(R.color.btn_false))
                binding.pinView.setLineColor(resources.getColor(R.color.btn_false))
            }else{
                binding.btnContinue.setBackgroundColor(resources.getColor(R.color.btn_true))
                binding.pinView.setLineColor(resources.getColor(R.color.btn_true))
            }
        }
    }
//    private fun time(){
//        binding.thtTime
//
//    }

  /*  private var runnable=object :Runnable{
        override fun run() {
            if (count>=10){
                binding.thtTime.text="00:${count.toString()}"
            }else if (count<10){
                binding.thtTime.text="00:0${count.toString()}"
            }
            count--
            handler.postDelayed(this,1000)

            if (count==0){
                binding.thtTime.visibility=View.GONE
                binding.thtAgain.visibility=View.VISIBLE

            }
        }

    }
   */

    private fun keyBoardTest(){
        val edt=binding.pinView
        val button =binding.btnContinue

        edt.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            edt.getWindowVisibleDisplayFrame(rect)
            val screenHeight = edt.rootView.height-240f
            val keypadHeight = screenHeight - rect.bottom

            if (keypadHeight > screenHeight * 0.15) { // Klaviatura ochilgan
                button.translationY = (-keypadHeight).toFloat()
            } else { // Klaviatura yopilgan
                button.translationY = 0f
            }
        }


    }


}