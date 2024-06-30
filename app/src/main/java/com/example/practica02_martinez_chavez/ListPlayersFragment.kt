package com.example.practica02_martinez_chavez

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ListPlayersFragment : Fragment() {

    private lateinit var recyclerViewPlayers: RecyclerView
    private lateinit var firestore: FirebaseFirestore
    private lateinit var playerAdapter: PlayerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_players, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

        // Initialize RecyclerView
        recyclerViewPlayers = view.findViewById(R.id.recyclerViewPlayers)
        recyclerViewPlayers.layoutManager = LinearLayoutManager(requireContext())

        // Load players into RecyclerView
        loadPlayers()
    }

    private fun loadPlayers() {
        firestore.collection("jugadores").get()
            .addOnSuccessListener { documents ->
                val players = documents.map { doc ->
                    Player(
                        nombre = doc.getString("nombre") ?: "",
                        posición = doc.getString("posición") ?: "",
                        dorsal = doc.getString("dorsal") ?: "",
                        fotoUrl = doc.getString("fotoUrl") ?: ""
                    )
                }
                playerAdapter = PlayerAdapter(players)
                recyclerViewPlayers.adapter = playerAdapter
            }
            .addOnFailureListener { e ->
                // Handle error
            }
    }
}
