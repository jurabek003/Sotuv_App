package uz.turgunboyevjurabek.saxovat.model.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import uz.turgunboyevjurabek.saxovat.model.madels.categories.CategoriesResponseItem
import uz.turgunboyevjurabek.saxovat.model.madels.clients.GetAllClients
import uz.turgunboyevjurabek.saxovat.model.madels.login.LoginRequest
import uz.turgunboyevjurabek.saxovat.model.madels.login.LoginResponse
import uz.turgunboyevjurabek.saxovat.model.madels.product.GetAllProduct
import uz.turgunboyevjurabek.saxovat.model.madels.product.GetProductOfCategoriya
import uz.turgunboyevjurabek.saxovat.model.madels.product.GetSearchProduct
import uz.turgunboyevjurabek.saxovat.model.madels.register.RegisterRequest
import uz.turgunboyevjurabek.saxovat.model.madels.register.RegisterRespons
import uz.turgunboyevjurabek.saxovat.utils.ConstItem.TOKEN

interface ApiService {

    /**
     * Login dan o'tish uchun
     */
    @POST("user/login/")
    suspend fun postLogin(@Body loginRequest: LoginRequest):LoginResponse

    /**
     * Registratsiya uchun
     */
    @POST("user/register/")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): RegisterRespons

    /**
     * Hamma katego'riyalarni olish uchun
     */
    @GET("products/categories/")
    suspend fun getCategories(@Header("Authorization") token:String=TOKEN):ArrayList<CategoriesResponseItem>

    /**
     * Hamma klientlarni olish uchun
     */
    @GET("clients/")
    suspend fun getAllClients(@Header("Authorization") token:String= TOKEN):GetAllClients

    /**
     * Hamma Mahsulotlarni olish uchun
     */
    @GET("products/")
    suspend fun getAllProduct(@Header("Authorization") token: String = TOKEN):GetAllProduct

    /**
     * Mahsulotlarni id catego'riya bo'yicha olish
     */
    @GET("products/{id}")
    suspend fun getCategoriesProduct(@Path("id") id:String,@Header("Authorization") token:String= TOKEN ):GetProductOfCategoriya

    /**
     * Mahsulotlarni qidirish uchun
     */
    @GET("products/search?")
    suspend fun searchProduct(@Query("search") search: String, @Header("Authorization") token: String= TOKEN) :GetSearchProduct


}