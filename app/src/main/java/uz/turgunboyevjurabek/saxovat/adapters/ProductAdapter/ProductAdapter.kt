package uz.turgunboyevjurabek.saxovat.adapters.ProductAdapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import uz.turgunboyevjurabek.saxovat.databinding.ItemProductRvBinding
import uz.turgunboyevjurabek.saxovat.model.madels.product.GetAllProduct
import uz.turgunboyevjurabek.saxovat.model.madels.product.GetAllProductItem

class ProductAdapter():RecyclerView.Adapter<ProductAdapter.Vh>(){
    private val list= GetAllProduct()
    inner class Vh(val itemProductRvBinding: ItemProductRvBinding):ViewHolder(itemProductRvBinding.root){
        fun onBind(getAllProductItem: GetAllProductItem){
            itemProductRvBinding.itemProductName.text=getAllProductItem.name
            itemProductRvBinding.itemProductAbout.text=getAllProductItem.amount.toString()
            Glide.with(itemView.context)
                .load(getAllProductItem.image)
                .into(itemProductRvBinding.itemProductImg)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
     return Vh(ItemProductRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int =list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }
    fun updateData(newData: ArrayList<GetAllProductItem>){
        if (list.isNotEmpty()){
            list.clear()
        }
            list.addAll(newData)


    }
}