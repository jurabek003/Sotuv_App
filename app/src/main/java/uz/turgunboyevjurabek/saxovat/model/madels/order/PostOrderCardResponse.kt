package uz.turgunboyevjurabek.saxovat.model.madels.order


import com.google.gson.annotations.SerializedName

data class PostOrderCardResponse(
    @SerializedName("client")
    val client: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("price")
    val price: String,
    @SerializedName("product")
    val product: Int,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("total_price")
    val totalPrice: Double
)
