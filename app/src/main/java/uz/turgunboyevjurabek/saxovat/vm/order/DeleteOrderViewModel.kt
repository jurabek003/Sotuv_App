package uz.turgunboyevjurabek.saxovat.vm.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response
import uz.turgunboyevjurabek.saxovat.model.repo.AppRepository
import uz.turgunboyevjurabek.saxovat.utils.Resource
import javax.inject.Inject

@HiltViewModel
class DeleteOrderViewModel @Inject  constructor(private val appRepository: AppRepository):ViewModel() {
    private val deleteLiveData=MutableLiveData<Resource<Response<String?>>>()
    fun deleteItem(id:Int):MutableLiveData<Resource<Response<String?>>>{
        viewModelScope.launch {
            try {
                deleteLiveData.postValue(Resource.loading("loafing at DeleteOrderViewModel"))
                var data=async {
                    appRepository.deleteOneOrder(id)
                }.await()
                    deleteLiveData.postValue(Resource.success(data))
            }catch (e:Exception){
                deleteLiveData.postValue(Resource.error(e.message))
            }
        }

        return deleteLiveData
    }
}