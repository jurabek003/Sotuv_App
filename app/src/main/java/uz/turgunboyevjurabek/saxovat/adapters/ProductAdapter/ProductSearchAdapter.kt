package uz.turgunboyevjurabek.saxovat.adapters.ProductAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import uz.turgunboyevjurabek.saxovat.databinding.ItemSearchProductBinding
import uz.turgunboyevjurabek.saxovat.model.madels.product.GetSearchProduct
import uz.turgunboyevjurabek.saxovat.model.madels.product.GetSearchProductItem

class ProductSearchAdapter:RecyclerView.Adapter<ProductSearchAdapter.Vh>() {
    private val list=GetSearchProduct()
    inner class Vh(private val itemSearchProductBinding: ItemSearchProductBinding):ViewHolder(itemSearchProductBinding.root){
        @SuppressLint("SetTextI18n")
        fun onBind(getSearchProductItem: GetSearchProductItem){

            itemSearchProductBinding.itemSearchNomi.text=getSearchProductItem.name
            itemSearchProductBinding.itemSearchMiqdor.text=getSearchProductItem.amount.toString()+" ta"

            val sana=getSearchProductItem.createAt.substring(0..9)
            val vaqt=getSearchProductItem.createAt.substring(11..20)

            itemSearchProductBinding.itemSearchSana.text= "sana: $sana"
            itemSearchProductBinding.itemSearchVaqt.text= "vaqt: $vaqt"

            itemSearchProductBinding.itemSearchLastPrice.text=getSearchProductItem.lastPrice+" so'm"

            Glide.with(itemSearchProductBinding.root)
                .load(getSearchProductItem.image)
                .into(itemSearchProductBinding.itemSearchImg)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemSearchProductBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }
    fun updateData(newData:ArrayList<GetSearchProductItem>){
        if (list.isNotEmpty()){
            list.clear()
        }
        list.addAll(newData)
    }
}