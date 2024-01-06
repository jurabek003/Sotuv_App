package uz.turgunboyevjurabek.saxovat.adapters.ProductAdapter

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import uz.turgunboyevjurabek.saxovat.R
import uz.turgunboyevjurabek.saxovat.databinding.ItemProductRvBinding
import uz.turgunboyevjurabek.saxovat.model.madels.product.GetAllProduct
import uz.turgunboyevjurabek.saxovat.model.madels.product.GetAllProductItem
import uz.turgunboyevjurabek.saxovat.model.madels.product.GetProductOfCategoriya

class ProductAdapter(val itemSelect: ItemSelect):RecyclerView.Adapter<ProductAdapter.Vh>(){
    private val list= ArrayList<GetProductOfCategoriya>()
    inner class Vh(val itemProductRvBinding: ItemProductRvBinding):ViewHolder(itemProductRvBinding.root){
        fun onBind(getProductOfCategoriya: GetProductOfCategoriya,position: Int){
            itemProductRvBinding.itemProductName.text=getProductOfCategoriya.name
            itemProductRvBinding.itemProductMiqdori.text=getProductOfCategoriya.amount.toString()+" ta"
            itemProductRvBinding.itemProductPuli.text=getProductOfCategoriya.lastPrice+" so'm"
            itemProductRvBinding.root.setOnClickListener {
                itemSelect.itemClick(getProductOfCategoriya,position)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
     return Vh(ItemProductRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int =list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position],position)
    }
    fun updateData(newData: ArrayList<GetProductOfCategoriya>){
        if (list.isNotEmpty()){
            list.clear()
        }
            list.addAll(newData)


    }
    interface ItemSelect{
        fun itemClick(getProductOfCategoriya: GetProductOfCategoriya,position: Int)
    }
}