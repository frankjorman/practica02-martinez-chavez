package com.example.practica02_martinez_chavez

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

data class Player(val nombre: String, val posición: String, val dorsal: String, val fotoUrl: String)

class PlayerAdapter(private val players: List<Player>) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val playerName: TextView = itemView.findViewById(R.id.textViewPlayerName)
        val playerPosition: TextView = itemView.findViewById(R.id.textViewPlayerPosition)
        val playerDorsal: TextView = itemView.findViewById(R.id.textViewPlayerDorsal)
        val playerImage: ImageView = itemView.findViewById(R.id.imageViewPlayer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_player, parent, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]
        holder.playerName.text = player.nombre
        holder.playerPosition.text = player.posición
        holder.playerDorsal.text = player.dorsal
        Glide.with(holder.itemView.context).load(player.fotoUrl).into(holder.playerImage)
    }

    override fun getItemCount() = players.size
}