package uz.turgunboyevjurabek.saxovat.model.madels.clients


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("amount")
    val amount: String,
    @SerializedName("client_name")
    val clientName: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String
)