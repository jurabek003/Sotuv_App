package uz.turgunboyevjurabek.saxovat.model.madels.login


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token")
    val token: String,
    @SerializedName("user")
    val user: User
)