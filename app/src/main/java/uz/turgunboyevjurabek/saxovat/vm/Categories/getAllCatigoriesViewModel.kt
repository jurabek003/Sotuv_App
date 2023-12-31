
package uz.turgunboyevjurabek.saxovat.vm.Categories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.turgunboyevjurabek.saxovat.model.madels.categories.CategoriesResponseItem
import uz.turgunboyevjurabek.saxovat.model.repo.AppRepository
import uz.turgunboyevjurabek.saxovat.utils.Resource
import javax.inject.Inject

@HiltViewModel
class GetAllCategoriesViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {

    private val getAllCategoriesLiveData = MutableLiveData<Resource<ArrayList<CategoriesResponseItem>>>()

    fun getAllCategories(): MutableLiveData<Resource<ArrayList<CategoriesResponseItem>>> {
        viewModelScope.launch {
            getAllCategoriesLiveData.postValue(Resource.loading("Loading"))
            try {
                val categories = withContext(Dispatchers.IO) {
                    appRepository.getAllCategories()
                }
                Log.d("keldi", "viewModelga keldi")
                getAllCategoriesLiveData.postValue(Resource.success(categories))
            } catch (e: Exception) {
                getAllCategoriesLiveData.postValue(Resource.error(e.message))
            }
        }
        return getAllCategoriesLiveData
    }
}

