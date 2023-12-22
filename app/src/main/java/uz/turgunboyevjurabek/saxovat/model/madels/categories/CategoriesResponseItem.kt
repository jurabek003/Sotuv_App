package uz.turgunboyevjurabek.saxovat.model.madels.categories


import com.google.gson.annotations.SerializedName

data class CategoriesResponseItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("create_at")
    val createAt: String,
    @SerializedName("update_at")
    val updateAt: String
)