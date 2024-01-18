package uz.turgunboyevjurabek.saxovat.vm.Categories.karzinka

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import uz.turgunboyevjurabek.saxovat.model.madels.categories.karzinka.GetAllCardOrder
import uz.turgunboyevjurabek.saxovat.model.repo.AppRepository
import uz.turgunboyevjurabek.saxovat.utils.Resource
import javax.inject.Inject

@HiltViewModel
class GetAllOrderCardViewModel @Inject constructor(val appRepository: AppRepository):ViewModel() {
    private val getAllCardOrder=MutableLiveData<Resource<GetAllCardOrder>>()

    fun getAllApiOrder(id:Int):MutableLiveData<Resource<GetAllCardOrder>>{
        viewModelScope.launch {
            getAllCardOrder.postValue(Resource.loading("Load at GetAllOrderByClientId "))
            try {
                val getData=async {
                    appRepository.getAllOrderByClientId(id)
                }.await()
                getAllCardOrder.postValue(Resource.success(getData))

            }catch (e:Exception){
                getAllCardOrder.postValue(Resource.error(e.message))
            }
        }

        return getAllCardOrder
    }
}