 package uz.turgunboyevjurabek.saxovat.adapters.categoriesAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import uz.turgunboyevjurabek.saxovat.databinding.ItemRvPurchaseBinding
import uz.turgunboyevjurabek.saxovat.model.madels.categories.CategoriesResponseItem

class PurchaseAdapter():RecyclerView.Adapter<PurchaseAdapter.Vh>() {
     private var list=ArrayList<CategoriesResponseItem>()

    inner class Vh(val itemRvPurchaseBinding: ItemRvPurchaseBinding):ViewHolder(itemRvPurchaseBinding.root){
        fun onBind(categoriesResponseItem: CategoriesResponseItem){
            itemRvPurchaseBinding.itemName.text=categoriesResponseItem.name
            Glide.with(itemRvPurchaseBinding.root.context)
                .load(categoriesResponseItem.image)
                .into(itemRvPurchaseBinding.itemImg)
           // Picasso.get().load(categoriesResponseItem.image).placeholder(itemRvPurchaseBinding.itemImg.drawable)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvPurchaseBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int =list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }
    fun updateData(newData: ArrayList<CategoriesResponseItem>){
        if (list.isNotEmpty()){
            list.clear()
        }
        list.addAll(newData)
    }
}