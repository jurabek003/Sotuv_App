package uz.turgunboyevjurabek.saxovat.model.madels.register


import com.google.gson.annotations.SerializedName

data class RegisterRespons(
    @SerializedName("status")
    val status: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("user")
    val user: User
)