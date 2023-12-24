package uz.turgunboyevjurabek.saxovat.vm.clients

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.turgunboyevjurabek.saxovat.model.madels.clients.GetAllClients

import uz.turgunboyevjurabek.saxovat.model.repo.AppRepository
import uz.turgunboyevjurabek.saxovat.utils.Resource
import javax.inject.Inject

@HiltViewModel
class GetAllClientsViewModel @Inject constructor(private val appRepository: AppRepository) :ViewModel() {

    private val getAllClientsLiveData=MutableLiveData<Resource<GetAllClients>>()

    fun getDept():MutableLiveData<Resource<GetAllClients>>{
        viewModelScope.launch {
            getAllClientsLiveData.postValue(Resource.loading("Loading"))
            try {
                val clients= withContext(Dispatchers.IO){
                    appRepository.getAllClients()
                }
                getAllClientsLiveData.postValue(Resource.success(clients))
            }catch (e:Exception){
                getAllClientsLiveData.postValue(Resource.error(e.message))
            }
        }
        return getAllClientsLiveData
    }
}