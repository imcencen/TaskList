package paba.belajar.tasklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import paba.belajar.tasklistnew.Task

class adapterRecView (private val listTask: ArrayList<Task>): RecyclerView
.Adapter<adapterRecView.ListViewHolder> () {
    inner class ListViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _namaTask = itemView.findViewById<TextView>(R.id.tvNama)
        var _tanggalTask = itemView.findViewById<TextView>(R.id.tvTanggal)
        var _descWayang = itemView.findViewById<TextView>(R.id.tvDescription)
        var _btnDelete = itemView.findViewById<Button>(R.id.btnDelete)
        var _btnEdit = itemView.findViewById<Button>(R.id.btnEdit)
        var _btnStart = itemView.findViewById<Button>(R.id.btnStart)
    }

    private lateinit var onItemClickCallback : OnItemClickCallback

    interface OnItemClickCallback {
        fun delData(position: Int)
        fun editData(data : Task, position: Int)
        fun startData(position: Int)
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
        return listTask.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var task = listTask[position]
        holder._namaTask.setText(task.nama)
        holder._tanggalTask.setText(task.tanggal)
        holder._descWayang.setText(task.desc)
        holder._btnDelete.setOnClickListener{
            onItemClickCallback.delData(position)
        }
        holder._btnEdit.setOnClickListener{
            onItemClickCallback.editData(listTask[position], position)
        }
        holder._btnStart.setOnClickListener{
            onItemClickCallback.startData(position)
        }

        if (listTask[position].started) {
            holder._btnEdit.isEnabled = false
            holder._btnStart.setText("Done")
        }
    }

}