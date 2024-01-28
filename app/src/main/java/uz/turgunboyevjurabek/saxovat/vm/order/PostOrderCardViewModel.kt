package uz.turgunboyevjurabek.saxovat.vm.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import uz.turgunboyevjurabek.saxovat.model.madels.order.card.PostOrderCardRequest
import uz.turgunboyevjurabek.saxovat.model.madels.order.card.PostOrderCardResponse
import uz.turgunboyevjurabek.saxovat.model.repo.AppRepository
import uz.turgunboyevjurabek.saxovat.utils.Resource
import javax.inject.Inject

@HiltViewModel
class PostOrderCardViewModel @Inject constructor(private val appRepository: AppRepository):ViewModel() {
    private val postOrderCardLiveData=MutableLiveData<Resource<PostOrderCardResponse>>()
    fun postData(postOrderCardRequest: PostOrderCardRequest):MutableLiveData<Resource<PostOrderCardResponse>>{
        viewModelScope.launch {
            postOrderCardLiveData.postValue(Resource.loading("loading at PostOrderCardViewModel"))
            try {

                   val getData= async{
                       appRepository.postOrderCard(postOrderCardRequest)
                   }.await()
                   postOrderCardLiveData.postValue(Resource.success(getData))

            }catch (e:Exception){
                postOrderCardLiveData.postValue(Resource.error(e.message))
            }
        }

        return postOrderCardLiveData
    }
}