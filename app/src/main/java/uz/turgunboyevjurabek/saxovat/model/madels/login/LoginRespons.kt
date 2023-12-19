package uz.turgunboyevjurabek.saxovat.model.madels.login


import com.google.gson.annotations.SerializedName

data class LoginRespons(
    @SerializedName("status")
    val status: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("user")
    val user: User
)