package uz.turgunboyevjurabek.saxovat.vm.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.turgunboyevjurabek.saxovat.model.madels.register.RegisterRequest
import uz.turgunboyevjurabek.saxovat.model.madels.register.RegisterRespons
import uz.turgunboyevjurabek.saxovat.model.repo.AppRepository
import uz.turgunboyevjurabek.saxovat.utils.Resource
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(val appRepository: AppRepository):ViewModel() {
    val getRegisterLiveData=MutableLiveData<Resource<RegisterRespons>>()

    fun registerWorking(registerRequest: RegisterRequest):MutableLiveData<Resource<RegisterRespons>>{
        viewModelScope.launch {
            try {
                getRegisterLiveData.postValue(Resource.loading("Loading"))
                coroutineScope {
                    val token= withContext(Dispatchers.IO){
                        appRepository.register(registerRequest)
                    }
                    getRegisterLiveData.postValue(Resource.success(token))
                }

            }catch (e:Exception){
                getRegisterLiveData.postValue(Resource.error(e.message))
            }

        }
        return getRegisterLiveData
    }

}