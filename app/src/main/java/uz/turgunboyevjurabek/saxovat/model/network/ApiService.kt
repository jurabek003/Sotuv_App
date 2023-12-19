package uz.turgunboyevjurabek.saxovat.model.network

import retrofit2.http.Body
import retrofit2.http.POST
import uz.turgunboyevjurabek.saxovat.model.madels.login.LoginRequest
import uz.turgunboyevjurabek.saxovat.model.madels.login.LoginRespons

interface ApiService {
    @POST("login/")
    suspend fun postLogin(
        @Body loginRequest: LoginRequest):LoginRespons

}