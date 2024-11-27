package paba.belajar.tasklist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddOrEditTask : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_or_edit_task)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val _tvLabel = findViewById<TextView>(R.id.tvLabel)
        val _etNama = findViewById<EditText>(R.id.etNama)
        val _etTanggal = findViewById<EditText>(R.id.etTanggal)
        val _etDesc = findViewById<EditText>(R.id.etDesc)
        val _btnDone = findViewById<Button>(R.id.btnDone)

        val index = intent.getIntExtra("INDEX", -1)

        if (index != -1){
            _tvLabel.setText("Edit Task")
            _etNama.setText(intent.getStringExtra("NAMA"))
            _etTanggal.setText(intent.getStringExtra("TANGGAL"))
            _etDesc.setText(intent.getStringExtra("DESC"))
        }

        _btnDone.setOnClickListener {
            val intent = Intent(this@AddOrEditTask, MainActivity::class.java)
            if (intent.getIntExtra("INDEX", -1) != -1){
                intent.putExtra("INDEX", -1)
            } else {
                intent.putExtra("INDEX", index)
            }
            intent.putExtra("NAMA", _etNama.text.toString())
            intent.putExtra("TANGGAL", _etTanggal.text.toString())
            intent.putExtra("DESC", _etDesc.text.toString())
            startActivity(intent)
        }
    }
}