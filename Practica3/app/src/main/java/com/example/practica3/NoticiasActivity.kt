package com.example.practica3

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practica3.databinding.ActivityNoticiasBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Element
import java.net.URL
import java.util.prefs.Preferences
import javax.xml.parsers.DocumentBuilderFactory

class NoticiasActivity : AppCompatActivity(), OnClickListener {
    private lateinit var noticiaAdapter: NoticiaAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var binding: ActivityNoticiasBinding

    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityNoticiasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noticiaAdapter = NoticiaAdapter(emptyList(), this)
        linearLayoutManager = LinearLayoutManager(this)
        binding.recyblerview.apply {
            layoutManager = linearLayoutManager
            adapter = noticiaAdapter
        }


        val preferences = getPreferences(Context.MODE_PRIVATE)
        val lastTitle = preferences.getString(getString(R.string.last_new), "")

        if (lastTitle != "") {
            Toast.makeText(this, lastTitle, Toast.LENGTH_SHORT).show()
        }
        loadNews()
    }

    private fun loadNews() {
        // Lanzamos la corutina para obtener las noticias
        GlobalScope.launch(Dispatchers.Main) {
            val noticias = getNews()
            // Actualizar el adaptador con las noticias
            noticiaAdapter.updateData(noticias)
        }
    }


    private suspend fun getNews(): List<Noticia> {
        val noticias = mutableListOf<Noticia>()

        withContext(Dispatchers.IO) {
            // Parsear el feed RSS
            val document = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(URL("https://feeds.elpais.com/mrss-s/pages/ep/site/elpais.com/section/tecnologia/portada").openStream())

            val items = document.getElementsByTagName("item")
            for (i in 0 until items.length) {
                val item = items.item(i) as Element

                // Extraer los datos del item
                val titulo = item.getElementsByTagName("title").item(0)?.textContent ?: ""
                val resumen = item.getElementsByTagName("description").item(0)?.textContent ?: ""
                val fecha = item.getElementsByTagName("pubDate").item(0)?.textContent ?: ""
                val imageUrl = item.getElementsByTagName("media:content")
                    .item(0)?.attributes?.getNamedItem("url")?.textContent
                    ?: "https://upload.wikimedia.org/wikipedia/commons/0/0a/No-image-available.png"

                val noticiaUrl = item.getElementsByTagName("link").item(0)?.textContent ?: ""

                // Agregar la noticia a la lista
                noticias.add(
                    Noticia(
                        id = i,
                        titulo = titulo,
                        resumen = resumen,
                        fecha = fecha,
                        imagenUrl = imageUrl,
                        noticiaUrl = noticiaUrl
                    )
                )
            }
        }
        return noticias


    }


    override fun onClick(new: Noticia, position: Int) {
        val preferences = getPreferences(Context.MODE_PRIVATE)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(new.noticiaUrl)

        preferences.edit().putString(getString(R.string.last_new), new.titulo).apply()

        startActivity(intent)
    }


}