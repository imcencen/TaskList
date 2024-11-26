package paba.belajar.tasklist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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

        val newTask = intent.getParcelableExtra<Task>("NEWTASK", Task::class.java)

        if (newTask != null) {
            arTask.add(newTask)
        }

        val _btnAdd = findViewById<Button>(R.id.btnAdd)
        _btnAdd.setOnClickListener{
            val intent = Intent(this@MainActivity, AddTask::class.java)
            intent.putExtra("TYPE", "ADD")
            startActivity(intent)
        }

        fun tampilkanData() {
            _rvTask.layoutManager = LinearLayoutManager(this)
            val adapterTask = AdapterTaskList(arTask)
            _rvTask.adapter = adapterTask

            adapterTask.setOnItemClickCallback(object : AdapterTaskList.OnItemClickCallback {
                override fun delData(pos: Int) {
                    arTask.removeAt(pos)
                    tampilkanData()
                }

                override fun editData(data: Task) {
                    val intent = Intent(this@MainActivity, AddTask::class.java)
                    intent.putExtra("TYPE", "EDIT")
                    intent.putExtra("DATA", data)
                    startActivity(intent)
                }
            })
        }

        _rvTask = findViewById(R.id.rvTask)
        tampilkanData()
    }

    private lateinit var _rvTask : RecyclerView
    private var arTask = arrayListOf<Task>()
}