package uz.turgunboyevjurabek.saxovat.model.repo

import uz.turgunboyevjurabek.saxovat.model.madels.login.LoginRequest
import uz.turgunboyevjurabek.saxovat.model.madels.register.RegisterRequest
import uz.turgunboyevjurabek.saxovat.model.network.ApiService
import javax.inject.Inject

class AppRepository @Inject constructor(val apiService: ApiService) {
    suspend fun login(loginRequest: LoginRequest)=apiService.postLogin(loginRequest)
    suspend fun register(registerRequest: RegisterRequest)=apiService.registerUser(registerRequest)
    suspend fun getAllCategories(token:String)=apiService.getCategories(token)
}