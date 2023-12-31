package uz.turgunboyevjurabek.saxovat.vm.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.turgunboyevjurabek.saxovat.model.madels.login.LoginRequest
import uz.turgunboyevjurabek.saxovat.model.madels.login.LoginResponse
import uz.turgunboyevjurabek.saxovat.model.repo.AppRepository
import uz.turgunboyevjurabek.saxovat.utils.MySharedPreference
import uz.turgunboyevjurabek.saxovat.utils.Resource
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val appRepository: AppRepository) :ViewModel() {
    private val getTokenLiveData=MutableLiveData<Resource<LoginResponse>>()

     fun loginWorking(loginRequest: LoginRequest):MutableLiveData<Resource<LoginResponse>> {
        viewModelScope.launch {
            getTokenLiveData.postValue(Resource.loading("loading"))
            try {
                coroutineScope {
                    val token= withContext(Dispatchers.IO){
                        appRepository.login(loginRequest)
                    }
                    getTokenLiveData.postValue(Resource.success(token))
                }
            }catch (e:Exception){
                getTokenLiveData.postValue(Resource.error(e.message))
            }
        }
         return getTokenLiveData
    }
}