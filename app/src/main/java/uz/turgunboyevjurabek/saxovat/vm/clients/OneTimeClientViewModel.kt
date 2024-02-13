package uz.turgunboyevjurabek.saxovat.vm.clients

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import uz.turgunboyevjurabek.saxovat.model.madels.clients.oneTime.OneTimeClientResponse
import uz.turgunboyevjurabek.saxovat.model.repo.AppRepository
import uz.turgunboyevjurabek.saxovat.utils.Resource
import javax.inject.Inject

@HiltViewModel
class OneTimeClientViewModel @Inject constructor(private val appRepository: AppRepository) :ViewModel() {
    private val oneTimePayLiveData=MutableLiveData<Resource<OneTimeClientResponse>>()

    fun getOneTimeClient():MutableLiveData<Resource<OneTimeClientResponse>>{
        viewModelScope.launch {
            oneTimePayLiveData.postValue(Resource.loading("Loading at OneTimeClientViewModel "))
            try {
                val getData=async {
                    appRepository.getOneTimeClient()
                }.await()
                oneTimePayLiveData.postValue(Resource.success(getData))
            }catch (e:Exception){
                oneTimePayLiveData.postValue(Resource.error(e.message))
            }
        }


        return oneTimePayLiveData
    }
}