package uz.turgunboyevjurabek.saxovat.ui.fragments.loginFragment

import android.annotation.SuppressLint
import android.graphics.Color
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
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import uz.turgunboyevjurabek.saxovat.R
import uz.turgunboyevjurabek.saxovat.databinding.FragmentCodeBinding
import uz.turgunboyevjurabek.saxovat.utils.LoginCheck

class CodeFragment : Fragment() {
    private val binding by lazy { FragmentCodeBinding.inflate(layoutInflater)}
    var count:Int=59
    private lateinit var handler:Handler
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

        binding.pinView.setAnimationEnable(true)
        binding.pinView.isPasswordHidden=true

        edtWork()
        thtInfo()

        handler= Handler(Looper.getMainLooper())
        handler.postDelayed(runnable,1000)


        binding.btnContinue.setOnClickListener {
            if (!binding.pinView.text.isNullOrEmpty()){
                LoginCheck.token=binding.pinView.text.toString()
            }
        }

        if (!LoginCheck.token.isNullOrEmpty()){
            findNavController().navigate(R.id.homeFragment)
        }

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
    private fun time(){
        binding.thtTime

    }

    private var runnable=object :Runnable{
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

}