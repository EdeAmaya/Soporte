package edenilson.amaya.soporte

import Modelo.ClaseConexion
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class Registro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtCorreoRegistro = findViewById<EditText>(R.id.txtCorreoRegistro)
        val txtContrasenaRegistro = findViewById<EditText>(R.id.txtContrsenaRegistro)
        val btnRegistro = findViewById<Button>(R.id.btnRegistro)
        val btnIniciarSesion = findViewById<Button>(R.id.btnPantallaInicioSesion)
        val imgeye = findViewById<ImageView>(R.id.imgeye)
        val imgAtras = findViewById<ImageView>(R.id.imgAtras)


        btnRegistro.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {

                val objConexion = ClaseConexion().cadenaConexion()


                val crearUsuario =
                    objConexion?.prepareStatement("INSERT INTO tbUsuarios(UUID_usuario, correoElectronico, clave) VALUES (?, ?, ?)")!!
                crearUsuario.setString(1, UUID.randomUUID().toString())
                crearUsuario.setString(2, txtCorreoRegistro.text.toString())
                crearUsuario.setString(3, txtContrasenaRegistro.text.toString())
                crearUsuario.executeUpdate()
                withContext(Dispatchers.Main) {

                    Toast.makeText(this@Registro, "Usuario creado", Toast.LENGTH_SHORT).show()
                    txtCorreoRegistro.setText("")
                    txtContrasenaRegistro.setText("")

                }
            }

        }
        

        imgeye.setOnClickListener {
            if (txtContrasenaRegistro.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                txtContrasenaRegistro.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                txtContrasenaRegistro.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }



        imgAtras.setOnClickListener {
            val pantallaLogin = Intent(this, InicioSesion::class.java)
            startActivity(pantallaLogin)
        }

        btnIniciarSesion.setOnClickListener {
            val pantallaLogin = Intent(this, InicioSesion::class.java)
            startActivity(pantallaLogin)
        }
    }
}