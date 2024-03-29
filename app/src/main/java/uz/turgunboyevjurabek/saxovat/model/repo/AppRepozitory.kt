package uz.turgunboyevjurabek.saxovat.model.repo

import uz.turgunboyevjurabek.saxovat.model.madels.login.LoginRequest
import uz.turgunboyevjurabek.saxovat.model.madels.order.card.post.PostOrderCardRequest
import uz.turgunboyevjurabek.saxovat.model.madels.order.card.put.PutOrderCardRequest
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
    suspend fun putItemOrderCard(putOrderCardRequest: PutOrderCardRequest,id: String)=apiService.putOrderCard(id,putOrderCardRequest)
    suspend fun getOneProduct(id:String)=apiService.getOneProduct(id)
    suspend fun deleteOneOrder(id:Int)=apiService.deleteOneOrder(id)
    suspend fun getOneTimeClient()=apiService.oneTimeClient()

}