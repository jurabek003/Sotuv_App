package uz.turgunboyevjurabek.saxovat.model.madels.order.card

import com.google.gson.annotations.SerializedName

data class PostOrderCardRequest (
    @SerializedName("product")
    val product: Int,
    @SerializedName("client")
    val client: Int,
    @SerializedName("price")
    val price: String,
    @SerializedName("quantity")
    val quantity: Int
)