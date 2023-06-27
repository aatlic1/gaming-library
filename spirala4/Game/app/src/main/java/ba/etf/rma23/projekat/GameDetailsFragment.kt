package ba.etf.rma23.projekat

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.data.repositories.GameReview
import ba.etf.rma23.data.repositories.GameReviewListViewModel
import ba.etf.rma23.projekat.data.repositories.*
import com.bumptech.glide.Glide


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
    private lateinit var modalReviewButton: Button
    private lateinit var modalRatingButton: Button
    private lateinit var modalReviewText : EditText
    private lateinit var modalRatingBar: RatingBar
    private var gameListViewModel = GameListViewModel()
    private var accountListViewModel = AccountListViewModel()
    private var gameReviewListViewModel = GameReviewListViewModel()
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
        modalReviewButton = view.findViewById(R.id.modal_review_button)
        modalRatingButton = view.findViewById(R.id.modal_rating_button)

        var gameId = arguments?.getInt("game_ID", -1)

        if (gameId != -1 && gameId != null) {
            gameListViewModel.getGameDetails(gameId, onSuccess = ::onSuccessGame, onError = ::onError)
        }


        favoriteGameButton.setOnClickListener {
            accountListViewModel.saveGame(game, onSuccess = ::onSuccess, onError = ::onError)
        }

        deleteGameButton.setOnClickListener {
            accountListViewModel.removeGame(game.id, onSuccess = ::onSuccess, onError = {
                context?.let { it1 -> Functions.toastError(it1) }
            })
        }

        modalReviewButton.setOnClickListener(View.OnClickListener { v ->
            val builder: AlertDialog.Builder = AlertDialog.Builder(v.context)
            builder.setTitle("Comment")

            val inflater = LayoutInflater.from(v.context)
            val dialogView: View = inflater.inflate(R.layout.modal_review, null)
            builder.setView(dialogView)

            modalReviewText = dialogView.findViewById(R.id.editTextReview)

            builder.setPositiveButton("Submit") { dialog, _ ->
                context?.let {
                    gameReviewListViewModel.sendReview(it,
                        GameReview(rating = null, review = modalReviewText.text.toString(), igdb_id = game.id, online =false),
                        onSuccess = ::onSuccessReview, onError = ::onError)
                    gameReviewListViewModel.sendOfflineReviews(it, onSuccess = { counter->
                        val toast = Toast.makeText(context, "$counter reviews have been sent. ", Toast.LENGTH_SHORT)
                        toast.show()
                    }, onError = {})

                }
            }

            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        })

        modalRatingButton.setOnClickListener(View.OnClickListener { v ->
            val builder: AlertDialog.Builder = AlertDialog.Builder(v.context)
            builder.setTitle("Rate it")

            val inflater = LayoutInflater.from(v.context)
            val dialogView: View = inflater.inflate(R.layout.modal_rating, null)
            builder.setView(dialogView)

            modalRatingBar = dialogView.findViewById(R.id.rating_bar_modal)

            builder.setPositiveButton("Submit") { dialog, _ ->
                context?.let {
                    gameReviewListViewModel.sendReview(it,
                        GameReview(rating = modalRatingBar.rating.toInt(), review = null, igdb_id = game.id, online =  false),
                        onSuccess = ::onSuccessReview, onError = ::onError)
                    //gameReviewListViewModel.writeDB(it, GameReview(modalRatingBar.rating.toInt(), "", game.id, false, "",""), onSuccess = {l->}, onError = {})
                    //gameReviewListViewModel.getOfflineReviews(it, onSuccess = { reviews-> Log.d("review", "review: $reviews")}, onError = {})
                    gameReviewListViewModel.sendOfflineReviews(it, onSuccess = { counter->
                        val toast = Toast.makeText(context, "$counter reviews have been sent. ", Toast.LENGTH_SHORT)
                        toast.show()
                    }, onError = {})
                }
            }

            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        })

        userimpression.layoutManager = LinearLayoutManager(activity)
        listImpressionsAdapter = GameUserImpressionAdapter(listOf())
        userimpression.adapter =  listImpressionsAdapter

        return view
    }

    fun onSuccess(game: Game){
        context?.let { Functions.toastSuccess(it) }
    }

    fun onSuccessGame(games: List<Game>){
        if(games.isNotEmpty()) {
            this.game = Functions.convertData(games[0])
            lastGame = this.game
            populateDetails()
        }
    }

    fun onError() {
        context?.let { Functions.toastError(it) }
    }

    fun onSuccess(resolt: Boolean){
        context?.let { Functions.toastSuccess(it) }
    }

    fun onSuccessReview(resolt: Boolean){
        gameReviewListViewModel.getReviewsForGame(game.igdb_id, onSuccess = ::onSuccess, onError = {})
        context?.let { Functions.toastSuccess(it) }
    }

    fun onSuccess(reviews: List<GameReview>){
        var ui: MutableList<UserImpression> = mutableListOf()
        for(review in reviews){
            if(review.review == null && review.rating != null)
                ui.add(UserRating(review.student, review.timestamp.toLong(), review.rating!!.toDouble()))
            else if(review.rating == null && review.review != null)
                ui.add(UserReview(review.student, review.timestamp.toLong(), review.review!!))
            else if(review.rating != null && review.review != null){
                ui.add(UserRating(review.student, review.timestamp.toLong(), review.rating!!.toDouble()))
                ui.add(UserReview(review.student, review.timestamp.toLong(), review.review!!))
            }
        }
        listImpressionsAdapter.updateImpressions(ui)
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

        gameReviewListViewModel.getReviewsForGame(game.igdb_id, onSuccess = ::onSuccess, onError = {})
    }
}