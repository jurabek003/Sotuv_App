package uz.turgunboyevjurabek.saxovat.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import uz.turgunboyevjurabek.saxovat.databinding.ItemRvPurchaseBinding
import uz.turgunboyevjurabek.saxovat.model.madels.categories.CategoriesResponseItem

class PurchaseAdapter(var list: ArrayList<CategoriesResponseItem>):RecyclerView.Adapter<PurchaseAdapter.Vh>() {
    inner class Vh(val itemRvPurchaseBinding: ItemRvPurchaseBinding):ViewHolder(itemRvPurchaseBinding.root){
        fun onBind(categoriesResponseItem: CategoriesResponseItem){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvPurchaseBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int =list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }
}