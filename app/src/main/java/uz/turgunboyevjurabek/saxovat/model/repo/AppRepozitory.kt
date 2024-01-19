package uz.turgunboyevjurabek.saxovat.model.repo

import android.icu.text.StringSearch
import uz.turgunboyevjurabek.saxovat.model.madels.login.LoginRequest
import uz.turgunboyevjurabek.saxovat.model.madels.order.PostOrderCardRequest
import uz.turgunboyevjurabek.saxovat.model.madels.register.RegisterRequest
import uz.turgunboyevjurabek.saxovat.model.network.ApiService
import javax.inject.Inject

class AppRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun login(loginRequest: LoginRequest)=apiService.postLogin(loginRequest)
    suspend fun register(registerRequest: RegisterRequest)=apiService.registerUser(registerRequest)
    suspend fun getAllCategories()=apiService.getCategories()
    suspend fun getAllClients()=apiService.getAllClients()
    suspend fun getAllProduct()=apiService.getAllProduct()
    suspend fun getProductCate(id:String)=apiService.getCategoriesProduct(id)
    suspend fun getSearchProduct(search: String)=apiService.searchProduct(search)
    suspend fun getAllOrderByClientId(id:Int)=apiService.getAllOrderByClientId(id)
    suspend fun postOrderCard(postOrderCardRequest: PostOrderCardRequest)=apiService.postOrderByCard(postOrderCardRequest)

}