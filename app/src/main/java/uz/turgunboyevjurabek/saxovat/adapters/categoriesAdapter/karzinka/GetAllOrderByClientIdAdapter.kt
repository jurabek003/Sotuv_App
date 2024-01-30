package uz.turgunboyevjurabek.saxovat.adapters.categoriesAdapter.karzinka

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import uz.turgunboyevjurabek.saxovat.databinding.ItemAllOrderRvBinding
import uz.turgunboyevjurabek.saxovat.model.madels.categories.karzinka.Result

class GetAllOrderByClientIdAdapter(val putItem: PutItem):RecyclerView.Adapter<GetAllOrderByClientIdAdapter.Vh>() {
    private val list=ArrayList<Result>()
    inner class Vh(private val itemAllOrderRvBinding: ItemAllOrderRvBinding):ViewHolder(itemAllOrderRvBinding.root){
        @SuppressLint("SetTextI18n")
        fun onBind(result: Result,position: Int){
            itemAllOrderRvBinding.itemOrderMiqdori.text=result.quantity.toString()+" ta"
            itemAllOrderRvBinding.itemOrderPuli.text=result.price +" so'm"
            val date=result.createdAt.substring(0..9)
            val time=result.createdAt.substring(11..18)
            itemAllOrderRvBinding.itemOrderSana.text=date
            itemAllOrderRvBinding.itemOrderVaqt.text=time


            itemAllOrderRvBinding.constraintRoot.setOnClickListener {
                putItem.putItemOrder(result,position)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemAllOrderRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int =list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position],position)
    }

    fun updateData(newList: ArrayList<Result>?){
        if(list.isNotEmpty()){
            list.clear()
        }
        newList?.let { list.addAll(it) }
    }
    interface PutItem{
        fun putItemOrder(result: Result,position: Int)
    }

}
