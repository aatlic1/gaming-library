package com.example.game

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {
    private lateinit var listGames: RecyclerView
    private lateinit var listGamesAdapter: GameListAdapter
    private var allGames =  GameData.getAll()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)
        listGames = view.findViewById(R.id.game_list)

        fun showGameDetails(game: Game){
            val bundle = bundleOf("game_title" to game.title)
            Navigation.findNavController(view).navigate(R.id.details, bundle)
        }

        listGames.layoutManager = LinearLayoutManager(activity)
        listGamesAdapter = GameListAdapter(arrayListOf()) { game->showGameDetails(game) }
        listGames.adapter = listGamesAdapter
        listGamesAdapter.updateGames(allGames)


        return view
    }
}