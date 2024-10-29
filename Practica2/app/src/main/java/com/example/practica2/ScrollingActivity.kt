package com.example.practica2

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.practica2.databinding.ActivityScrollingBinding
import com.example.practica2.databinding.CardCentroComercialBinding
class ScrollingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScrollingBinding

    private var mediaPlayer: MediaPlayer? = null
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurar el binding principal
        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar el MediaPlayer solo si no está creado
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.musicita)
            mediaPlayer?.isLooping = true
        }

        // Si hay un estado guardado, restauramos la posición
        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("music_position", 0)
            mediaPlayer?.seekTo(position)
        }

        mediaPlayer?.start()

        // Obtener y mostrar los centros comerciales
        val centrosComerciales = obtenerCentrosComerciales()

        for (centro in centrosComerciales) {

            val cardBinding = CardCentroComercialBinding.inflate(layoutInflater)

            // Asignar los valores a los elementos de la tarjeta
            cardBinding.imagenCentro.setImageResource(centro.imagen)
            cardBinding.nombreCentro.text = centro.nombre
            cardBinding.direccionCentro.text = centro.dirrecion
            cardBinding.numTiendasCentro.text = "${centro.numTiendas} tiendas"

            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(0, 0, 0, 100)
            cardBinding.root.layoutParams = layoutParams

            // Configurar el listener para cada CardView
            cardBinding.root.setOnClickListener {
                val intent = Intent(this, TiendaActivity::class.java)
                intent.putExtra("nombreCentro", centro.nombre)
                intent.putExtra("tiendas", ArrayList(centro.tiendas))
                intent.putExtra("music_position", mediaPlayer!!.currentPosition)
                startActivity(intent)
            }


            binding.linearLayout.addView(cardBinding.root)
        }
    }

    override fun onStart() {
        super.onStart()
        mediaPlayer?.start()
    }

    override fun onResume() {
        super.onResume()
        // Verificar si el Intent contiene una posición de la música
        val positionFromIntent = intent.getIntExtra("music_position", -1)

        if (positionFromIntent != -1) {
            // Si la posición se ha enviado desde TiendaActivity, reanudar desde esa posición
            mediaPlayer?.seekTo(positionFromIntent)
            intent.removeExtra("music_position") // Limpia la posición para futuros resumes
        } else {
            // De lo contrario, reanudar desde la posición pausada
            mediaPlayer?.seekTo(position)
        }
        mediaPlayer?.start() // Asegurarse de que la música siga sonando
    }



    override fun onPause() {
        super.onPause()
        mediaPlayer?.pause() // Pausar la música cuando la actividad se pausa
        position = mediaPlayer!!.currentPosition // Guardar la posición
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer?.pause() // Asegurarse de pausar la música al detener la actividad
    }
    /*
    override fun onRestart() {
        super.onRestart()
        mediaPlayer?.seekTo(position)
        mediaPlayer?.start()
    }
    *
     */

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release() // Liberar recursos al destruir la actividad
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Guardar la posición actual de la música
        outState.putInt("music_position", mediaPlayer!!.currentPosition)
    }

    private fun obtenerCentrosComerciales(): List<CentroComercial> {
        return listOf(
            CentroComercial(
                "Centro Aqua",
                "Calle de Menorca, 19, 46023 Valencia",
                10,
                R.drawable.aqua1,
                listOf(
                    Tienda("Tienda A1", "Ropa y accesorios de moda"),
                    Tienda("Tienda A2", "Electrónica y gadgets"),
                    Tienda("Tienda A3", "Juguetería"),
                    Tienda("Tienda A4", "Libros y papelería")
                )
            ),
            CentroComercial(
                "Centro El Saler",
                "Av. del Professor López Piñero, 16, 46013 Valencia",
                12,
                R.drawable.saler,
                listOf(
                    Tienda("Tienda S1", "Zapatos y calzado"),
                    Tienda("Tienda S2", "Artículos para el hogar"),
                    Tienda("Tienda S3", "Moda juvenil"),
                    Tienda("Tienda S4", "Deportes y equipamiento")
                )
            ),
            CentroComercial(
                "Centro Bonaire",
                "Autovía A-3, Km. 345, 46960 Aldaia, Valencia",
                15,
                R.drawable.bonaire,
                listOf(
                    Tienda("Tienda B1", "Electrodomésticos"),
                    Tienda("Tienda B2", "Juguetes y entretenimiento"),
                    Tienda("Tienda B3", "Moda y complementos"),
                    Tienda("Tienda B4", "Supermercado")
                )
            ),
            CentroComercial(
                "Nuevo Centro",
                "Av. de Pío XII, 2, 46009 Valencia",
                8,
                R.drawable.nuevo_centro,
                listOf(
                    Tienda("Tienda N1", "Perfumería"),
                    Tienda("Tienda N2", "Ropa deportiva"),
                    Tienda("Tienda N3", "Tecnología y móviles"),
                    Tienda("Tienda N4", "Decoración y muebles")
                )
            )
        )
    }
}
