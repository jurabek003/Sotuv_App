package uz.turgunboyevjurabek.saxovat.vm.Categories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.turgunboyevjurabek.saxovat.model.madels.categories.CategoriesResponse
import uz.turgunboyevjurabek.saxovat.model.network.ApiService
import uz.turgunboyevjurabek.saxovat.utils.Resource
import javax.inject.Inject

@HiltViewModel
class GetAllCategoriesViewModel @Inject constructor(val apiService: ApiService) :ViewModel() {

    val getAllCategoriesLiveData=MutableLiveData<Resource<CategoriesResponse>>()


     fun getAllCategories():MutableLiveData<Resource<CategoriesResponse>>{
         viewModelScope.launch {
             getAllCategoriesLiveData.postValue(Resource.loading("Loading"))
             try {
                 coroutineScope {
                     val categories= withContext(Dispatchers.IO){
                         apiService.getCategories()
                     }
                     getAllCategoriesLiveData.postValue(Resource.success(categories))
                 }
             }catch (e:Exception){
                 getAllCategoriesLiveData.postValue(Resource.error(e.message))
             }


         }
         return getAllCategoriesLiveData
     }

}