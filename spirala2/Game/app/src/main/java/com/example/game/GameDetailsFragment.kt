package com.example.game

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class GameDetailsFragment : Fragment() {
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
    private lateinit var listImpressionsAdapter: GameUserImpressionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_game_details, container, false)
        title = view.findViewById(R.id.item_title_textview)
        coverImage = view.findViewById(R.id.cover_imageview)
        platform = view.findViewById(R.id.platform_textview)
        releaseDate = view.findViewById(R.id.release_date_textview)
        esrb = view.findViewById(R.id.esrb_rating_textview)
        developer = view.findViewById(R.id.developer_textview)
        publisher = view.findViewById(R.id.publisher_textview)
        genre = view.findViewById(R.id.genre_textview)
        description = view.findViewById(R.id.description_textview)
        userimpression = view.findViewById(R.id.user_impression)

        var gameTitle = arguments?.getString("game_title")
        if(gameTitle != null) {
            game = GameData.getDetails(gameTitle)
            lastGame = game
            populateDetails()
        }

        userimpression.layoutManager = LinearLayoutManager(activity)
        listImpressionsAdapter = GameUserImpressionAdapter(listOf())
        userimpression.adapter =  listImpressionsAdapter
        val sortList = game.userImpressions.sortedBy { it.timestamp }

        listImpressionsAdapter.updateImpressions(sortList)

        return view
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
}