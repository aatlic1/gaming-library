package com.example.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GameDetailsActivity : AppCompatActivity() {
    private lateinit var game : Game
    private lateinit var title: TextView
    private lateinit var coverImage: ImageView
    private lateinit var platform: TextView
    private lateinit var releaseDate: TextView
    private lateinit var esrb: TextView
    private lateinit var developer: TextView
    private lateinit var publisher: TextView
    private lateinit var genre: TextView
    private lateinit var description: TextView
    private lateinit var userimpression : RecyclerView
    private lateinit var homeButton : Button
    private lateinit var listImpressionsAdapter: GameUserImpressionAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)
        title = findViewById(R.id.game_title_textview)
        coverImage = findViewById(R.id.cover_imageview)
        platform = findViewById(R.id.platform_textview)
        releaseDate = findViewById(R.id.release_date_textview)
        esrb = findViewById(R.id.esrb_rating_textview)
        developer = findViewById(R.id.developer_textview)
        publisher = findViewById(R.id.publisher_textview)
        genre = findViewById(R.id.genre_textview)
        description = findViewById(R.id.description_textview)
        homeButton = findViewById(R.id.home_button)
        userimpression = findViewById(R.id.user_impression)
        val extras = intent.extras
        if (extras != null) {
            game = GameData.getDetails(extras.getString("game_title", ""))
            lastGame = game
            populateDetails()
        } else {
            finish()
        }

        userimpression.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        listImpressionsAdapter = GameUserImpressionAdapter(listOf())
        userimpression.adapter =  listImpressionsAdapter
        val sortList = game.userImpressions.sortedBy { it.timestamp }

        listImpressionsAdapter.updateImpressions(sortList)

        homeButton.setOnClickListener(){
            goHome()
        }
    }
    private fun populateDetails(){
        title.text = game.title
        platform.text = game.platform
        releaseDate.text = game.releaseDate
        esrb.text = game.esrbRating
        developer.text = game.developer
        publisher.text = game.publisher
        genre.text = game.genre
        description.text = game.description

        val context: Context = coverImage.context
        var id: Int = context.resources
            .getIdentifier(game.coverImage, "drawable", context.packageName)
        if(id===0) id=context.resources
            .getIdentifier("cover_pic", "drawable", context.packageName)
        coverImage.setImageResource(id)
    }
    private  fun goHome(){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}