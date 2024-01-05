package uz.turgunboyevjurabek.saxovat.adapters.ProductAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.turgunboyevjurabek.saxovat.databinding.ItemSearchProductBinding
import uz.turgunboyevjurabek.saxovat.model.madels.product.GetAllProduct
import uz.turgunboyevjurabek.saxovat.model.madels.product.GetAllProductItem

class GetAllProductAdapter:RecyclerView.Adapter<GetAllProductAdapter.Vh>() {
    private val list= GetAllProduct()
     inner class Vh(private val itemProductRvBinding: ItemSearchProductBinding): RecyclerView.ViewHolder(itemProductRvBinding.root){
        fun onBind(getSearchProductItem: GetAllProductItem){
            itemProductRvBinding.itemSearchNomi.text=getSearchProductItem.name
            itemProductRvBinding.itemSearchMiqdor.text=getSearchProductItem.amount.toString()+" ta"

            val sana=getSearchProductItem.createAt.substring(0..9)
            val vaqt=getSearchProductItem.createAt.substring(11..20)

            itemProductRvBinding.itemSearchSana.text= "sana: $sana"
            itemProductRvBinding.itemSearchVaqt.text= "vaqt: $vaqt"

            itemProductRvBinding.itemSearchLastPrice.text=getSearchProductItem.lastPrice+" so'm"

            Glide.with(itemProductRvBinding.root)
                .load(getSearchProductItem.image)
                .into(itemProductRvBinding.itemSearchImg)

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
    fun updateData(newData: ArrayList<GetAllProductItem>){
        if (list.isNotEmpty()){
            list.clear()
        }
        list.addAll(newData)


    }
}