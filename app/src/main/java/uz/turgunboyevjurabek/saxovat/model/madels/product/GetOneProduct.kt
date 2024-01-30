package uz.turgunboyevjurabek.saxovat.model.madels.product


import com.google.gson.annotations.SerializedName

data class GetOneProduct(
    @SerializedName("amount")
    val amount: Int?,
    @SerializedName("category")
    val category: Int?,
    @SerializedName("create_at")
    val createAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("last_price")
    val lastPrice: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("optom_price")
    val optomPrice: String?,
    @SerializedName("other_images")
    val otherImages: List<Any>?,
    @SerializedName("sell_price")
    val sellPrice: String?,
    @SerializedName("update_at")
    val updateAt: String?
)