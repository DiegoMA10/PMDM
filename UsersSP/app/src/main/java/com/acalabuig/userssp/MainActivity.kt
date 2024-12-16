package com.acalabuig.userssp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.acalabuig.userssp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var userAdapter: UserAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter(getUsers(), this)
        linearLayoutManager = LinearLayoutManager(this)

        binding.recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = userAdapter
        }
    }

    private fun getUsers(): MutableList<User> {
        val users = mutableListOf<User>()

        val claudio = User(1, "Claudio", "López", "https://img.freepik.com/vector-gratis/avatar-personaje-empresario-aislado_24877-60111.jpg")
        val david = User(2, "David", "Villa", "https://img.freepik.com/vector-gratis/avatar-personaje-empresario-aislado_24877-60111.jpg")
        val hugo = User(3, "Hugo", "Duro", "https://img.freepik.com/vector-gratis/avatar-personaje-empresario-aislado_24877-60111.jpg")
        val pepelu = User(4, "José Luis", "García", "https://img.freepik.com/vector-gratis/avatar-personaje-empresario-aislado_24877-60111.jpg")
        val mista = User(5, "Miguel Ángel", "Ferrer Mista", "https://img.freepik.com/vector-gratis/avatar-personaje-empresario-aislado_24877-60111.jpg")
        val ilie = User(6, "Adrian", "Ilie", "https://img.freepik.com/vector-gratis/avatar-personaje-empresario-aislado_24877-60111.jpg")
        val romarioDAldaia = User(7, "Juan", "Sánchez", "https://img.freepik.com/vector-gratis/avatar-personaje-empresario-aislado_24877-60111.jpg")

        users.add(claudio)
        users.add(david)
        users.add(hugo)
        users.add(pepelu)
        users.add(mista)
        users.add(ilie)
        users.add(romarioDAldaia)

        return users
    }

    override fun onClick(user: User, position: Int) {
        Toast.makeText(this, "$position: ${user.getFullName()}", Toast.LENGTH_SHORT).show()
    }

}