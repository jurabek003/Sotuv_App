package uz.turgunboyevjurabek.saxovat.model.network

import retrofit2.http.Body
import retrofit2.http.POST
import uz.turgunboyevjurabek.saxovat.model.madels.login.LoginRequest
import uz.turgunboyevjurabek.saxovat.model.madels.login.LoginRespons
import uz.turgunboyevjurabek.saxovat.model.madels.register.RegisterRequest
import uz.turgunboyevjurabek.saxovat.model.madels.register.RegisterRespons

interface ApiService {


    @POST("login/")
    suspend fun getLoginToken(@Body loginRequest: LoginRequest):LoginRespons


    @POST("register/")
    suspend fun getRegisterUser(@Body registerRequest: RegisterRequest):RegisterRespons



}