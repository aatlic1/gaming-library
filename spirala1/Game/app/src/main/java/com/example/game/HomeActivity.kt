package com.example.game

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity() {
    private lateinit var listGames: RecyclerView
    private lateinit var listGamesAdapter: GameListAdapter
    private var allGames =  GameData.getAll()
    private lateinit var detailsButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listGames = findViewById(R.id.game_list)
        detailsButton = findViewById(R.id.details_button)

        listGames.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        listGamesAdapter = GameListAdapter(arrayListOf()) {game -> showGameDetails(game)}
        listGames.adapter = listGamesAdapter
        listGamesAdapter.updateGames(allGames)

        detailsButton.setOnClickListener(){
            if(lastGame != null) {
                val intent = Intent(this, GameDetailsActivity::class.java).apply {
                    putExtra("game_title", lastGame?.title)
                }
                startActivity(intent)
            }
        }
    }
    private fun showGameDetails(game: Game){
        val intent = Intent(this, GameDetailsActivity::class.java).apply {
            putExtra("game_title", game.title)
        }
        startActivity(intent)
    }
}
