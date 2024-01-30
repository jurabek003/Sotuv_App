package uz.turgunboyevjurabek.saxovat.vm.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import uz.turgunboyevjurabek.saxovat.model.madels.order.card.put.PutOrderCardRequest
import uz.turgunboyevjurabek.saxovat.model.madels.order.card.put.PutOrderCardResponse
import uz.turgunboyevjurabek.saxovat.model.repo.AppRepository
import uz.turgunboyevjurabek.saxovat.utils.Resource
import javax.inject.Inject

@HiltViewModel
class PutOrderCardViewModel @Inject constructor(private val appRepository: AppRepository):ViewModel() {

    private  val  putOrderCardLiveData=MutableLiveData<Resource<PutOrderCardResponse>>()

    fun putOrderCardApi(putOrderCardRequest: PutOrderCardRequest,id:String):MutableLiveData<Resource<PutOrderCardResponse>>{
        viewModelScope.launch {
            putOrderCardLiveData.postValue(Resource.loading("Loading at PutOrderCardViewModel"))
            try {
                val getData=async {
                    appRepository.putItemOrderCard(putOrderCardRequest,id)
                }.await()
                putOrderCardLiveData.postValue(Resource.success(getData))
            }catch (e:Exception){
                putOrderCardLiveData.postValue(Resource.error(e.message))
            }
        }
        return putOrderCardLiveData
    }
}