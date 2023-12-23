package uz.turgunboyevjurabek.saxovat.model.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import uz.turgunboyevjurabek.saxovat.model.madels.categories.CategoriesResponseItem
import uz.turgunboyevjurabek.saxovat.model.madels.login.LoginRequest
import uz.turgunboyevjurabek.saxovat.model.madels.login.LoginResponse
import uz.turgunboyevjurabek.saxovat.model.madels.register.RegisterRequest
import uz.turgunboyevjurabek.saxovat.model.madels.register.RegisterRespons
import uz.turgunboyevjurabek.saxovat.utils.MySharedPreference

interface ApiService {
    @POST("user/login/")
    suspend fun postLogin(@Body loginRequest: LoginRequest):LoginResponse

    @POST("user/register/")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): RegisterRespons

    @GET("products/categories/")
    suspend fun getCategories(@Header("Authorization") token:String="Token 287fbe4ea8ba04f6718e0d72ecde7ef96f669bdd"):ArrayList<CategoriesResponseItem>



}