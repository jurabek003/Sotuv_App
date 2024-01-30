package uz.turgunboyevjurabek.saxovat.model.madels.order.card.put

import com.google.gson.annotations.SerializedName

data class PutOrderCardRequest (
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("product")
    val product: Int,
    @SerializedName("client")
    val client: Int,
    @SerializedName("price")
    val price: String
)