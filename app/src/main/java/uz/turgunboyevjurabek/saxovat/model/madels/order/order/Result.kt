package uz.turgunboyevjurabek.saxovat.model.madels.order.order


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("client")
    val client: Int,
    @SerializedName("clientshop")
    val clientshop: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("order_type")
    val orderType: String,
    @SerializedName("payment_type")
    val paymentType: String,
    @SerializedName("price_for_client")
    val priceForClient: String,
    @SerializedName("sell_admin")
    val sellAdmin: Int,
    @SerializedName("total")
    val total: String
)