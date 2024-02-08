package uz.turgunboyevjurabek.saxovat.adapters.dialog

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import uz.turgunboyevjurabek.saxovat.databinding.DialogBuyOrderBinding
import uz.turgunboyevjurabek.saxovat.databinding.ItemBuyOrderBinding
import uz.turgunboyevjurabek.saxovat.model.madels.categories.karzinka.Result

class RvDialogBuyOrder(val list: ArrayList<Result>):RecyclerView.Adapter<RvDialogBuyOrder.Vh>() {
    inner class Vh(val itemDialogBuyOrderBinding: ItemBuyOrderBinding):ViewHolder(itemDialogBuyOrderBinding.root){
        @SuppressLint("SetTextI18n")
        fun onBind(result: Result){
            itemDialogBuyOrderBinding.orderName.text=result.product.toString()
            itemDialogBuyOrderBinding.orderAmount.text=result.quantity.toString()+" ta"
            itemDialogBuyOrderBinding.orderPrice.text=result.price+" so'm"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemBuyOrderBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        list[position]?.let { holder.onBind(it) }
    }
}