package com.example.practica02_martinez_chavez

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class Fragment_Registro_Jugador : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var spinnerCountries: Spinner
    private lateinit var editTextPlayerName: EditText
    private lateinit var editTextPlayerPosition: EditText
    private lateinit var editTextPlayerDorsal: EditText
    private lateinit var editTextPlayerImageUrl: EditText
    private lateinit var buttonSavePlayer: Button

    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__registro__jugador, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

        // Initialize views
        spinnerCountries = view.findViewById(R.id.spinnerCountries)
        editTextPlayerName = view.findViewById(R.id.editTextPlayerName)
        editTextPlayerPosition = view.findViewById(R.id.editTextPlayerPosition)
        editTextPlayerDorsal = view.findViewById(R.id.editTextPlayerDorsal)
        editTextPlayerImageUrl = view.findViewById(R.id.editTextPlayerImageUrl)
        buttonSavePlayer = view.findViewById(R.id.buttonSavePlayer)

        // Load countries into spinner
        loadCountriesIntoSpinner()

        // Set button click listener
        buttonSavePlayer.setOnClickListener {
            savePlayer()
        }
    }

    private fun loadCountriesIntoSpinner() {
        firestore.collection("paises").get()
            .addOnSuccessListener { documents ->
                val countries = documents.map { it.getString("nombre") ?: "" }
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, countries)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerCountries.adapter = adapter
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Error loading countries: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun savePlayer() {
        val country = spinnerCountries.selectedItem.toString()
        val playerName = editTextPlayerName.text.toString()
        val playerPosition = editTextPlayerPosition.text.toString()
        val playerDorsal = editTextPlayerDorsal.text.toString()
        val playerImageUrl = editTextPlayerImageUrl.text.toString()

        if (playerName.isNotEmpty() && playerPosition.isNotEmpty() && playerDorsal.isNotEmpty() && playerImageUrl.isNotEmpty()) {
            val player = hashMapOf(
                "nombre" to playerName,
                "posición" to playerPosition,
                "dorsal" to playerDorsal,
                "fotoUrl" to playerImageUrl,
                "pais" to country
            )

            firestore.collection("jugadores").add(player)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Jugador guardado correctamente", Toast.LENGTH_SHORT).show()
                    clearForm()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Error al guardar jugador: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(requireContext(), "Por favor llene los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearForm() {
        editTextPlayerName.text.clear()
        editTextPlayerPosition.text.clear()
        editTextPlayerDorsal.text.clear()
        editTextPlayerImageUrl.text.clear()
        spinnerCountries.setSelection(0) // Reset the spinner to its default position
    }
}
