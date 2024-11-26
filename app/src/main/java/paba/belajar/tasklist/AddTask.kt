package paba.belajar.tasklist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddTask : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_task)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val _etName = findViewById<EditText>(R.id.etName)
        val _etTanggal = findViewById<EditText>(R.id.etTanggal)
        val _etDesc = findViewById<EditText>(R.id.etDesc)

        val _btnAdd = findViewById<Button>(R.id.btnAdd)

        if (intent.getStringExtra("TYPE") == "EDIT") {
            val dataIntent = intent.getParcelableExtra<Task>("DATA", Task::class.java)
            if (dataIntent!=null) {
                _etName.setText(dataIntent.nama)
                _etTanggal.setText(dataIntent.tanggal)
                _etDesc.setText(dataIntent.desc)
            }
            _btnAdd.setText("Done")
        }
        
        _btnAdd.setOnClickListener{
            val newTask = Task(_etName.text.toString(), _etTanggal.text.toString(), _etDesc.text.toString())
            val intent = Intent(this@AddTask, MainActivity::class.java)
            intent.putExtra("NEWTASK", newTask)
            startActivity(intent)
        }

        val _btnCancel = findViewById<Button>(R.id.btnCancel)
        _btnCancel.setOnClickListener{
            val intent = Intent(this@AddTask, MainActivity::class.java)
            startActivity(intent)
        }
    }
}