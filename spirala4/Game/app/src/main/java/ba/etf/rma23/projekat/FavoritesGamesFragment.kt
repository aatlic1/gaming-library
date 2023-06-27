package ba.etf.rma23.projekat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.data.repositories.*


class FavoritesGamesFragment : Fragment() {
    private lateinit var listGames: RecyclerView
    private lateinit var listGamesAdapter: GameListAdapter
    private lateinit var deleteAllGamesButton: Button
    private lateinit var searchEditText: EditText
    private lateinit var searchButton: ImageButton
    private var accountListViewModel = AccountListViewModel( )
    private var favoriteGames: MutableList<Game> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_favorites_games, container, false)
        listGames = view.findViewById(R.id.game_list)
        deleteAllGamesButton = view.findViewById(R.id.delete_all_button)
        searchEditText = view.findViewById(R.id.search_query_edittext)
        searchButton = view.findViewById(R.id.search_button)

        accountListViewModel.getSavedGame(onSuccess = ::onSuccessSaved, onError = ::onError)

        fun showGameDetails(game: Game){
            val bundle = bundleOf("game_ID" to game.id)
            Navigation.findNavController(view).navigate(R.id.details, bundle)
        }

        listGames.layoutManager = LinearLayoutManager(activity)
        listGamesAdapter = GameListAdapter(arrayListOf()) { game->showGameDetails(game) }
        listGames.adapter = listGamesAdapter

        searchButton.setOnClickListener {
            accountListViewModel.getGamesContainingString(searchEditText.text.toString(), onSuccess = {games ->
                listGamesAdapter.updateGames(games)
            }, onError = {})
        }

        deleteAllGamesButton.setOnClickListener{
            accountListViewModel.removeNonSafe(onSuccess = ::onSuccess, onError = ::onError)
        }

        return view
    }

    fun onSuccessSaved(games: List<Game>){
        favoriteGames = games.toMutableList()
        listGamesAdapter.updateGames(games)
    }

    fun onSuccess(resolt: Boolean) {
        val gamesToDelete = mutableListOf<Game>()

        for (game in favoriteGames) {
            val age = Account.getAge()
            val rating = game.rating
            val esrbRating = game.esrbRating

            if (age < 3 && !((esrbRating == "1" && rating == 8.00) || (esrbRating == "2.0" && rating == 1.00))) {
                gamesToDelete.add(game)
            } else if (age >= 3 && age < 10 && esrbRating == "1" &&
                !((esrbRating == "1" && rating == 8.00) || (esrbRating == "1" && rating == 7.00))) {
                gamesToDelete.add(game)
            } else if (age >= 3 && age < 7 && esrbRating == "2" && !(esrbRating == "2" && rating == 1.00)) {
                gamesToDelete.add(game)
            } else if (age >= 3 && age < 13 && esrbRating == "1" &&
                !((esrbRating == "1" && rating == 8.00) || (esrbRating == "1" && rating == 7.00) || (esrbRating == "1" && rating == 9.00))) {
                gamesToDelete.add(game)
            } else if (age >= 3 && age < 12 && esrbRating == "2" &&
                !((esrbRating == "2" && rating == 1.00) || (esrbRating == "2"&& rating == 2.00))) {
                gamesToDelete.add(game)
            } else if (age >= 3 && age < 17 && esrbRating == "1" &&
                !((esrbRating == "1" && rating == 8.00) || (esrbRating == "1" && rating == 7.00) ||
                        (esrbRating == "1" && rating == 9.00) || (esrbRating == "1" && rating == 10.00))) {
                gamesToDelete.add(game)
            } else if (age >= 3 && age < 16 && esrbRating == "2" &&
                !((esrbRating == "2" && rating == 1.00) || (esrbRating == "2" && rating == 2.00) || (esrbRating == "2" && rating == 3.00))) {
                gamesToDelete.add(game)
            } else if (age >= 3 && age < 18 && esrbRating == "1" &&
                !((esrbRating == "1" && rating == 8.00) || (esrbRating == "1" && rating == 7.00) || (esrbRating == "1" && rating == 9.00) ||
                        (esrbRating == "1" && rating == 10.00) || (esrbRating == "1" && rating == 11.00))) {
                gamesToDelete.add(game)
            } else if (age >= 3 && age < 18 && esrbRating == "2" &&
                !((esrbRating == "2" && rating == 1.00) || (esrbRating == "2" && rating == 2.00) || (esrbRating == "2" && rating == 3.00) ||
                        (esrbRating == "2" && rating == 4.00))) {
                gamesToDelete.add(game)
            } else if (age >= 18 && esrbRating == "1" &&
                ((esrbRating == "1" && rating == 8.00) || (esrbRating == "1" && rating == 12.00))) {
                gamesToDelete.add(game)
            } else if (age >= 18 && esrbRating == "2" &&
                ((esrbRating == "2" && rating == 1.00) || (esrbRating == "2" && rating == 5.00))) {
                gamesToDelete.add(game)
            } else if(esrbRating == " "){
                gamesToDelete.add(game)
            }
            else{
                accountListViewModel.saveGame(game, onSuccess ={}, onError = {})
            }
        }

        favoriteGames.removeAll(gamesToDelete)
        listGamesAdapter.updateGames(favoriteGames)
        context?.let { Functions.toastSuccess(it) }
    }
    fun onError() {
        context?.let { Functions.toastError(it) }
    }

}