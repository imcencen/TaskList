package paba.belajar.tasklist

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import paba.belajar.tasklistnew.Task

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        _rvTask = findViewById<RecyclerView>(R.id.rvTask)

        sp = getSharedPreferences("dataSP", MODE_PRIVATE)

        fun saveSP() {
            val gson = Gson()
            val editor = sp.edit()
            val json = gson.toJson(arTask)
            editor.putString("spTask", json)
            editor.apply()
        }

        val gson = Gson()
        val isiSP = sp.getString("spTask", null)
        val type = object : TypeToken<ArrayList<Task>>() {}.type
        if (isiSP != null) {
            arTask = gson.fromJson(isiSP, type)
        }



        val index = intent.getIntExtra("INDEX", -2)

        if (index == -1) {
            _nama.add(intent.getStringExtra("NAMA").toString())
            _tanggal.add(intent.getStringExtra("TANGGAL").toString())
            _desc.add(intent.getStringExtra("DESC").toString())
            _started.add(false)
        } else if (index >= 0) {
            val position = index
            arTask[position].nama = intent.getStringExtra("NAMA").toString()
            arTask[position].tanggal = intent.getStringExtra("TANGGAL").toString()
            arTask[position].desc = intent.getStringExtra("DESC").toString()
            saveSP()
        }

        val _btnTambah = findViewById<Button>(R.id.btnTambah)
        _btnTambah.setOnClickListener {
            val intent = Intent(this@MainActivity, AddOrEditTask::class.java)
            intent.putExtra("INDEX", -1)
            startActivity(intent)
        }

        fun siapkanData() {
            _nama.add("Temp")
            _tanggal.add("Temp")
            _desc.add("Temp")
            _started.add(false)
        }

        fun tambahData() {
            arTask.clear()
            for (position in _nama.indices) {
                val data = Task(
                    _nama[position],
                    _tanggal[position],
                    _desc[position],
                    _started[position]
                )
                arTask.add(data)
            }
        }

        fun tampilkanData() {
            _rvTask.layoutManager = LinearLayoutManager(this)

            val adapterTask = adapterRecView(arTask)
            _rvTask.adapter = adapterTask

            adapterTask.setOnItemClickCallback(object : adapterRecView.OnItemClickCallback {
                override fun delData(position: Int) {
                    arTask.removeAt(position)
                    saveSP()
                    adapterTask.notifyItemRemoved(position)
                    adapterTask.notifyItemRangeChanged(position, arTask.size)
                }

                override fun editData(data: Task, position: Int) {
                    val intent = Intent(this@MainActivity, AddOrEditTask::class.java)
                    intent.putExtra("INDEX", position)
                    intent.putExtra("NAMA", data.nama)
                    intent.putExtra("TANGGAL", data.tanggal)
                    intent.putExtra("DESC", data.desc)
                    startActivity(intent)
                }

                override fun startData(position: Int) {
                    arTask[position].started = true
                    saveSP()
                    adapterTask.notifyDataSetChanged()
                }
            })
        }

        if (arTask.size == 0){
//            siapkanData()
        } else {
            arTask.forEach {
                _nama.add(it.nama)
                _tanggal.add(it.tanggal)
                _desc.add(it.desc)
                _started.add(it.started)
            }
            arTask.clear()
        }
        tambahData()
        saveSP()
        tampilkanData()

    }
    private var _nama : MutableList<String> = emptyList<String>().toMutableList()
    private var _tanggal : MutableList<String> = emptyList<String>().toMutableList()
    private var _desc : MutableList<String> = emptyList<String>().toMutableList()
    private var _started : MutableList<Boolean> = emptyList<Boolean>().toMutableList()
    private var arTask = arrayListOf<Task>()

    private lateinit var _rvTask : RecyclerView

    lateinit var sp : SharedPreferences
}