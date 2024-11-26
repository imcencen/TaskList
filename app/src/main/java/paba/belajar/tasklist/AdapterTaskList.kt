package paba.belajar.tasklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterTaskList (private val listTask: ArrayList<Task>) : RecyclerView.Adapter<AdapterTaskList.ListViewHolder> () {
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _namaList = itemView.findViewById<TextView>(R.id.tvNama)
        var _tanggalList = itemView.findViewById<TextView>(R.id.tvTanggal)
        var _descList = itemView.findViewById<TextView>(R.id.tvDesc)
        var _btnDel = itemView.findViewById<Button>(R.id.btnDelete)
        var _btnEdit = itemView.findViewById<Button>(R.id.btnEdit)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun delData(pos: Int)
        fun editData(data:Task)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_item, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  listTask.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val task = listTask[position]

        holder._namaList.setText(task.nama)
        holder._tanggalList.setText(task.tanggal)
        holder._descList.setText(task.desc)

        holder._btnDel.setOnClickListener{
            onItemClickCallback.delData(position)
        }
        holder._btnEdit.setOnClickListener{
            onItemClickCallback.editData(listTask[position])
        }



    }
}