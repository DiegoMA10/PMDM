package com.example.practica2

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.practica2.databinding.ActivityTiendaBinding
import com.example.practica2.databinding.ItemTiendaBinding

class TiendaActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityTiendaBinding
    private var mediaPlayer: MediaPlayer? = null
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTiendaBinding.inflate(layoutInflater)

        setContentView(binding.root)

        mediaPlayer = MediaPlayer.create(this, R.raw.musicita)
        mediaPlayer?.isLooping = true

        // Recuperar la posición de la música desde el intent
        position = intent.getIntExtra("music_position", 0)

        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("music_position", 0)
            mediaPlayer?.seekTo(position)
        }


        val nombreCentro = intent.getStringExtra("nombreCentro")
        val tiendas = intent.getSerializableExtra("tiendas") as ArrayList<Tienda>

        binding.nombreCentroTitulo.text = nombreCentro


      
        tiendas.forEach { tienda ->
            val tiendaBinding = ItemTiendaBinding.inflate(layoutInflater)
            tiendaBinding.nombreTienda.text = tienda.nombre
            tiendaBinding.descripcionTienda.text = tienda.descripcion
            binding.listaTiendasLayout.addView(tiendaBinding.root)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i("lifeCycle2", "onStart")
        mediaPlayer?.start()
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer?.seekTo(position)  // Restaurar posición
        mediaPlayer?.start()  // Reanudar música
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.pause()  // Pausar música
        position = mediaPlayer?.currentPosition ?: 0  // Guardar posición actual
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()  // Liberar recursos de MediaPlayer
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("music_position", position)  // Guardar posición para rotación
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.i("life2", "onBackPressed")
        val intent = Intent(this, ScrollingActivity::class.java).apply {
            putExtra("music_position", mediaPlayer!!.currentPosition)
        }
        startActivity(intent)
        finish()
    }


}
