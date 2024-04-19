package ba.etf.rma23.projekat

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.data.repositories.*
import java.time.Instant
import java.time.format.DateTimeFormatter

class HomeFragment : Fragment() {
    private lateinit var listGames: RecyclerView
    private lateinit var searchGamesText: EditText
    private lateinit var searchGamesButton: ImageButton
    private lateinit var sortGamesButton: ImageButton
    private lateinit var listGamesAdapter: GameListAdapter
    private var gameListViewModel = GameListViewModel( )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)
        listGames = view.findViewById(R.id.game_list)
        searchGamesText = view.findViewById(R.id.search_query_edittext)
        searchGamesButton = view.findViewById(R.id.search_button)
        sortGamesButton = view.findViewById(R.id.sort_button)

        fun showGameDetails(game: Game){
            val bundle = bundleOf("game_ID" to game.id)
            Navigation.findNavController(view).navigate(R.id.details, bundle)
        }


        searchGamesButton.setOnClickListener {
            onClick()
        }
       sortGamesButton.setOnClickListener{
            gameListViewModel.sortGames(onSuccess = { g ->
                listGamesAdapter.updateGames(g)
            }, onError = {})
        }

        listGames.layoutManager = LinearLayoutManager(activity)
        listGamesAdapter = GameListAdapter(arrayListOf()) { game->showGameDetails(game) }
        listGames.adapter = listGamesAdapter

        return view
    }

    private fun onClick(){
        gameListViewModel.getGamesByName(searchGamesText.text.toString(), onSuccess = ::onSuccess, onError = ::onError)

    }

    fun onSuccess(g: List<Game>) {
        if(Account.getAge() < 18)
            gameListViewModel.getGamesSafe(searchGamesText.text.toString(), onSuccess = {game->
                listGamesAdapter.updateGames(game)
            }, onError = {})
        else {
            listGamesAdapter.updateGames(g)
        }
    }
    fun onError() {
        val toast = Toast.makeText(context, "Search error.", Toast.LENGTH_SHORT)
        toast.show()
    }
}