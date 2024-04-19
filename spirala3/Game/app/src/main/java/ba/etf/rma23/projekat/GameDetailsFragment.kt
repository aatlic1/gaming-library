package ba.etf.rma23.projekat

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.data.repositories.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions



class GameDetailsFragment : Fragment() {
    private var game: Game = Game(-1, "", "", "",0.0, "", "", "", "", "", "")
    private lateinit var title: TextView
    private lateinit var coverImage: ImageView
    private lateinit var platform: TextView
    private lateinit var releaseDate: TextView
    private lateinit var esrb: TextView
    private lateinit var developer: TextView
    private lateinit var publisher: TextView
    private lateinit var genre: TextView
    private lateinit var description: TextView
    private lateinit var favoriteGameButton: ImageButton
    private lateinit var deleteGameButton:ImageButton
    private var gameListViewModel = GameListViewModel()
    private var accountListViewModel = AccountListViewModel()
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
        favoriteGameButton = view.findViewById(R.id.favorite_button)
        deleteGameButton = view.findViewById(R.id.delete_button)

        var gameId = arguments?.getInt("game_ID", -1)

        if (gameId != -1 && gameId != null) {
            gameListViewModel.getGameDetails(gameId, onSuccess = ::onSuccess, onError = ::onError)
        }

        userimpression.layoutManager = LinearLayoutManager(activity)
        listImpressionsAdapter = GameUserImpressionAdapter(listOf())
        userimpression.adapter =  listImpressionsAdapter

        listImpressionsAdapter.updateImpressions(game.userImpressions)

        favoriteGameButton.setOnClickListener {
            accountListViewModel.saveGame(game, onSuccess = ::onSuccess, onError = ::onError)
        }
        deleteGameButton.setOnClickListener {
            accountListViewModel.removeGame(game.id, onSuccess = ::onSuccess, onError = {
                val toast = Toast.makeText(context, "Error", Toast.LENGTH_SHORT)
                toast.show()
            })
        }
        return view
    }
    fun onSuccess(game: Game){
        val toast = Toast.makeText(context, "The game has been added to favorites.", Toast.LENGTH_SHORT)
        toast.show()
    }
    fun onSuccess(games: List<Game>){
        if(games.isNotEmpty()) {
            val game = games[0]
            var platform: String = ""
            if (game.platforms != null && game.platforms.isNotEmpty()) {
                for (p in game.platforms)
                    platform = platform + p.name + '\n'
            }
            var date: String = " "
            if (game.releaseDates != null && game.releaseDates.isNotEmpty()) {
                date = game.releaseDates[0].human
            }
            var rating: Double = 0.0
            var esrb: String = " "
            if(game.ageRatings != null && game.ageRatings.isNotEmpty()){
                val g = game.ageRatings.find{ageRating -> ageRating.category == 1 || ageRating.category == 2}
                if (g != null) {
                    esrb = g.category.toString()
                    rating = g.rating.toDouble()
                }
            }
            var company: String = " "
            if (game.involvedCompanies != null && game.involvedCompanies.isNotEmpty()) {
                company = game.involvedCompanies[0].company.name
            }
            var genre: String = " "
            if (game.genres != null && game.genres.isNotEmpty()) {
                for (g in game.genres)
                    genre = genre + g.name + "| "
            }
            var coverUrl: String = " "
            if (game.cover != null) {
                coverUrl = game.cover.url
            }
            this.game = Game(game.id, game.title, platform, date, rating, coverUrl, esrb,
                company, company, genre, game.description, listOf(), game.id
            )
            lastGame = this.game
            populateDetails()
        }
    }
    fun onError() {
        val toast = Toast.makeText(context, "Search error", Toast.LENGTH_SHORT)
        toast.show()
    }
    fun onSuccess(resolt: Boolean){
        val toast = Toast.makeText(context, "The game has been successfully deleted.", Toast.LENGTH_SHORT)
        toast.show()
    }
    private fun populateDetails(){

        title.text = game.title
        platform.text = game.platform
        releaseDate.text = game.releaseDate
        esrb.text = game.rating.toString()
        developer.text = game.developer
        publisher.text = game.publisher
        genre.text = game.genre
        description.text = game.description
        val context: Context = coverImage.getContext()
        Glide.with(context)
            .load("https:${game.coverImage}")
            .placeholder(R.drawable.cover)
            .error(R.drawable.cover)
            .fallback(R.drawable.cover)
            .into(coverImage);
    }
}