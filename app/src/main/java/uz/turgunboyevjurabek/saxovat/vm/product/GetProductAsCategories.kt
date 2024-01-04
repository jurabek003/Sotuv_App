package uz.turgunboyevjurabek.saxovat.vm.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import uz.turgunboyevjurabek.saxovat.model.madels.product.GetProductOfCategoriya
import uz.turgunboyevjurabek.saxovat.model.repo.AppRepository
import uz.turgunboyevjurabek.saxovat.utils.Resource
import javax.inject.Inject

@HiltViewModel
class GetProductAsCategories @Inject constructor( private val appRepository: AppRepository):ViewModel() {
    private val getProductLiveData=MutableLiveData<Resource<GetProductOfCategoriya>>()

    fun getData(id :String):MutableLiveData<Resource<GetProductOfCategoriya>>{
        viewModelScope.launch {
            getProductLiveData.postValue(Resource.loading("Loading at ProOfCate"))
         try {
             coroutineScope {
                 val getApi=async {
                     appRepository.getProductCate(id)
                 }.await()
                 getProductLiveData.postValue(Resource.success(getApi))
             }
         }catch (e:Exception){
             getProductLiveData.postValue(Resource.error(e.message))
         }
        }
        return getProductLiveData
    }
}