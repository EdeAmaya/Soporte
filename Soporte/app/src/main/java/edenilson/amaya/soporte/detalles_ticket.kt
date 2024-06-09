package edenilson.amaya.soporte

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class detalles_ticket : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalles_ticket)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtUUID = findViewById<TextView>(R.id.txtUUID)
        val txttitulo = findViewById<TextView>(R.id.txttitulo)
        val txtdescripcion = findViewById<TextView>(R.id.txtdescripcion)
        val txtemail = findViewById<TextView>(R.id.txtemail)
        val txtfechaC = findViewById<TextView>(R.id.txtfechaC)
        val txtautor = findViewById<TextView>(R.id.txtautor)
        val txtestado = findViewById<TextView>(R.id.txtestado)
        val txtfechaF = findViewById<TextView>(R.id.txtfechaF)
        val image = findViewById<ImageView>(R.id.img1)

        val uuid = intent.getStringExtra("uuid")
        val titulo = intent.getStringExtra("titulo")
        val descripcion = intent.getStringExtra("descripcion")
        val autor = intent.getStringExtra("autor")
        val email = intent.getStringExtra("email")
        val fechaC = intent.getStringExtra("fechaCreacion")
        val estado = intent.getStringExtra("estado")
        val fechaF = intent.getStringExtra("fechaFinalizacion")

        txtUUID.text = uuid
        txttitulo.text = titulo
        txtdescripcion.text = descripcion
        txtautor.text = autor
        txtemail.text = email
        txtfechaC.text = fechaC
        txtestado.text = estado
        txtfechaF.text = fechaF

        image.setOnClickListener {
            val pantallaAtras = Intent(this,MainActivity::class.java)
            startActivity(pantallaAtras)
        }

    }
}