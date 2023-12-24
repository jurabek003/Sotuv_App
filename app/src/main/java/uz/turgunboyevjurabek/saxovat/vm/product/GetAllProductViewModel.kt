package uz.turgunboyevjurabek.saxovat.vm.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.turgunboyevjurabek.saxovat.model.madels.product.GetAllProduct
import uz.turgunboyevjurabek.saxovat.model.repo.AppRepository
import uz.turgunboyevjurabek.saxovat.utils.Resource
import javax.inject.Inject

@HiltViewModel
class GetAllProductViewModel @Inject constructor(private val appRepository: AppRepository) :ViewModel(){
    private val getAllProductLiveData=MutableLiveData<Resource<GetAllProduct>>()
    fun getApiProduct():MutableLiveData<Resource<GetAllProduct>>{
        viewModelScope.launch {
            try {
                getAllProductLiveData.postValue(Resource.loading("Loading at product viewModel"))
                val getData= withContext(Dispatchers.IO){
                    appRepository.getAllProduct()
                }
                getAllProductLiveData.postValue(Resource.success(getData))
            }catch (e:Exception){
                getAllProductLiveData.postValue(Resource.error(e.message))
            }

        }
        return getAllProductLiveData
    }
}
