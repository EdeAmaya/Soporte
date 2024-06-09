package edenilson.amaya.soporte

import Modelo.ClaseConexion
import Modelo.tbTickets
import RecyclerViewHelper.Adaptador
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

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

        val txtTitulo = findViewById<EditText>(R.id.txtTitulo)
        val txtDescripcion = findViewById<EditText>(R.id.txtDescripcion)
        val txtAutor = findViewById<EditText>(R.id.txtAutor)
        val txtEmail = findViewById<EditText>(R.id.txtEmail)
        val txtFechaCreacion = findViewById<EditText>(R.id.txtFechaC)
        val txtEstado = findViewById<EditText>(R.id.txtEstado)
        val txtFechaFinalizacion = findViewById<EditText>(R.id.txtFechaF)
        val btnEnviar = findViewById<Button>(R.id.btnEnviar)
        val rcvTicket = findViewById<RecyclerView>(R.id.rcvTicket)

        rcvTicket.layoutManager = LinearLayoutManager(this)

        fun obtenerTickets():List<tbTickets>{
          val objConexion = ClaseConexion().cadenaConexion()

            val statement = objConexion?.createStatement()
            val resultSet = statement?.executeQuery("select * from tbTickets")

            val listaTickets = mutableListOf<tbTickets>()
            while (resultSet?.next() == true){
                val uuid = resultSet.getString("uuid")
                val Titulo = resultSet.getString("Titulo")
                val Descripcion = resultSet.getString("Descripcion")
                val Autor = resultSet.getString("Autor")
                val Email = resultSet.getString("Email")
                val FechaCreacion = resultSet.getString("FechaCreacion")
                val Estado = resultSet.getString("Estado")
                val FechaFinalizacion = resultSet.getString("FechaFinalizacion")

                val valoresJuntos = tbTickets(uuid,Titulo,Descripcion,Autor,Email,FechaCreacion,Estado,FechaFinalizacion)
                listaTickets.add(valoresJuntos)
            }
            return listaTickets

        }

        CoroutineScope(Dispatchers.IO).launch {
            val ticketDB = obtenerTickets()
            withContext(Dispatchers.Main){
                val adapter = Adaptador(ticketDB)
                rcvTicket.adapter = adapter
            }
        }





        btnEnviar.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val claseConexion = ClaseConexion().cadenaConexion()

                val addTicket =
                    claseConexion?.prepareStatement("insert into tbTickets(uuid,Titulo,Descripcion,Autor,Email,FechaCreacion,Estado,FechaFinalizacion)values(?,?,?,?,?,?,?,?)")!!
                addTicket.setString(1, UUID.randomUUID().toString())
                addTicket.setString(2, txtTitulo.text.toString())
                addTicket.setString(3, txtDescripcion.text.toString())
                addTicket.setString(4, txtAutor.text.toString())
                addTicket.setString(5, txtEmail.text.toString())
                addTicket.setString(6, txtFechaCreacion.text.toString())
                addTicket.setString(7, txtEstado.text.toString())
                addTicket.setString(8, txtFechaFinalizacion.text.toString())
                addTicket.executeUpdate()

                val nuevosTickets = obtenerTickets()

                withContext(Dispatchers.Main){
                    (rcvTicket.adapter as? Adaptador)?.actualizarLista(nuevosTickets)
                }


            }
        }

    }
}