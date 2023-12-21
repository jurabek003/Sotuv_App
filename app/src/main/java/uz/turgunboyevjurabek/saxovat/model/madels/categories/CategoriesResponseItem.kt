package uz.turgunboyevjurabek.saxovat.model.madels.categories


import com.google.gson.annotations.SerializedName

data class CategoriesResponseItem(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String
)