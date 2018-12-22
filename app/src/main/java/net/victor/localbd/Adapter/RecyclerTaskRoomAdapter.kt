package net.victor.localbd.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import kotlinx.android.synthetic.main.item_list_card.view.*
import net.victor.localbd.Entity.StringEntity
import net.victor.localbd.MainActivity
import net.victor.localbd.R
import java.text.FieldPosition
import javax.security.auth.callback.Callback

class RecyclerTaskRoomAdapter (private val items: List<StringEntity>) :
    RecyclerView.Adapter<RecyclerTaskRoomAdapter.ViewHolder>() {

    private  var mCallback: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(entity: StringEntity?, position: Int)
    }

    fun setOnItemListener(callback: OnItemClickListener){
        mCallback = callback
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val rootView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_card, parent, false)
        return ViewHolder(rootView)
    }

    override fun getItemCount(): Int  = items.size

    override fun onBindViewHolder(holder: ViewHolder, indexPath: Int) {

        val item = items[indexPath]

        holder.value(item)
        holder.view.setOnClickListener{
            if (indexPath != RecyclerView.NO_POSITION){
                mCallback?.onItemClick(item, indexPath)
            }
        }
    }



    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        var txtCell: TextView? = view.tvCellTitle

        fun value(item: StringEntity) {
            txtCell?.text = item.name
        }
    }
}