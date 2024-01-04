package uz.turgunboyevjurabek.saxovat.vm.product.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import uz.turgunboyevjurabek.saxovat.model.madels.product.GetSearchProduct
import uz.turgunboyevjurabek.saxovat.model.repo.AppRepository
import uz.turgunboyevjurabek.saxovat.utils.Resource
import javax.inject.Inject

@HiltViewModel
class SearchProductViewModel @Inject constructor(private val appRepository: AppRepository):ViewModel() {
    private val searchLiveData=MutableLiveData<Resource<GetSearchProduct>>()

    fun getSearchData(search:String):MutableLiveData<Resource<GetSearchProduct>> {
        viewModelScope.launch {
            searchLiveData.postValue(Resource.loading("Loading at SearchViewModel"))
            try {
                coroutineScope {
                    val getData=async {
                        appRepository.getSearchProduct(search)
                    }.await()
                    searchLiveData.postValue(Resource.success(getData))
                }
            }catch (e:Exception){
                searchLiveData.postValue(Resource.error(e.message))
            }
        }


        return searchLiveData
    }

}