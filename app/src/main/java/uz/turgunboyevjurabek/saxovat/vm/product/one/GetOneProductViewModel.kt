package uz.turgunboyevjurabek.saxovat.vm.product.one

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import uz.turgunboyevjurabek.saxovat.model.repo.AppRepository
import uz.turgunboyevjurabek.saxovat.model.madels.product.GetOneProduct
import uz.turgunboyevjurabek.saxovat.utils.Resource
import javax.inject.Inject

@HiltViewModel
class GetOneProductViewModel @Inject constructor(private val appRepository: AppRepository):ViewModel() {

    private val getOneProductLiveData=MutableLiveData<Resource<GetOneProduct>>()

    fun getOneProduct(id:String):MutableLiveData<Resource<GetOneProduct>>{
        viewModelScope.launch {
            getOneProductLiveData.postValue(Resource.loading("Loading at GetOneProductViewModel"))
            try {
                val getData=async {
                    appRepository.getOneProduct(id)
                }.await()
                getOneProductLiveData.postValue(Resource.success(getData))
            }catch (e:Exception){
                getOneProductLiveData.postValue(Resource.error(e.message))
            }
        }

        return getOneProductLiveData
    }

}