package uz.turgunboyevjurabek.saxovat.adapters.clientsAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.turgunboyevjurabek.saxovat.databinding.ItemRvAllClientsBinding
import uz.turgunboyevjurabek.saxovat.model.madels.clients.GetAllClients



class AllClientsAdapter(val onClick: OnClick): RecyclerView.Adapter<AllClientsAdapter.Vh>() {
        private var list=ArrayList<GetAllClients>()

        inner class Vh(val itemAllClients: ItemRvAllClientsBinding): RecyclerView.ViewHolder(itemAllClients.root){
            fun onBind(getAllClients: GetAllClients,position: Int){
                itemAllClients.itemThtLastName.text=getAllClients.results[position].fullName

                itemAllClients.mainLayout.setOnClickListener {
                    onClick.itemClick(getAllClients,position)
                }

            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
            return Vh(ItemRvAllClientsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }

        override fun getItemCount(): Int =list.size

        override fun onBindViewHolder(holder: Vh, position: Int) {
            holder.onBind(list[position],position)
        }
        fun updateData(newData: ArrayList<GetAllClients>){
            if (list.isNotEmpty()){
                list.clear()
            }
            list.addAll(newData)
        }
    interface OnClick{
        fun itemClick(getAllClients: GetAllClients,position: Int)
    }

}
