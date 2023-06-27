package ba.etf.rma23.data.repositories

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import ba.etf.rma23.projekat.ReviewResponse
import ba.etf.rma23.projekat.data.repositories.AccountApiConfig
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class GameReviewsRepository {
    companion object{
        suspend fun writeDB(context: Context, review: GameReview): String? {
            return withContext(Dispatchers.IO) {
                try{
                    val db = AppDatabase.getInstance(context)
                    db!!.gameReviewDAO().insert(review)
                    return@withContext "Success"
                }
                catch(error: Exception){
                    return@withContext null
                }
            }
        }

        suspend fun getOfflineReviews(context: Context): List<GameReview> {
            return withContext(Dispatchers.IO) {
                    val db = AppDatabase.getInstance(context)
                    val response = db!!.gameReviewDAO().getOfflineReviews()
                    return@withContext response
            }
        }

        suspend fun updateOnline(context: Context, review: GameReview): String?{
            return withContext(Dispatchers.IO) {
                try {
                    val db = AppDatabase.getInstance(context)
                    db!!.gameReviewDAO().updateOnline(review.igdb_id, review.online)
                    return@withContext "Success"
                }
                catch(error: Exception){
                    return@withContext null
                }

            }
        }

        suspend fun sendReview(context: Context, review: GameReview): Boolean{
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            val isInternetAvailable = networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
            if (isInternetAvailable){
            return withContext(Dispatchers.IO){
                val favoriteGames = AccountGamesRepository.getSavedGames()
                if(!favoriteGames.any{it.igdb_id == review.igdb_id}) {
                    GamesRepository.getGameDetails(review.igdb_id)?.get(0)
                        ?.let { AccountGamesRepository.saveGame(it) }
                }
                    val response = AccountApiConfig.retrofit.sendReview(
                    AccountGamesRepository.getHash(), review.igdb_id, review)
                Log.d("test", "response u send: $response")
                    if(response.code() == 200)
                        return@withContext true
                    else {
                        writeDB(context, review)
                        return@withContext false
                    }
                }
            }
        else {
            writeDB(context, review)
            return false
        }
        }

        suspend fun sendOfflineReviews(context: Context): Int{
            return withContext(Dispatchers.IO){
                var counter: Int = 0
                var listOfReview = getOfflineReviews(context)
                for(review in listOfReview){
                    var response: Boolean = false
                    if(review.rating != null && review.review == "")
                        response = sendReview(context, GameReview(rating = review.rating, review = null, igdb_id = review.igdb_id, online = false))
                    else if(review.rating == 0 && review.review != null)
                        response = sendReview(context, GameReview(rating = null, review = review.review, igdb_id = review.igdb_id, online = false))
                    else if(review.rating != null && review.review != null) {
                        response = sendReview(
                            context,
                            GameReview(
                                rating = review.rating,
                                review = review.review,
                                igdb_id = review.igdb_id,
                                online = false
                            )
                        )
                    }
                    if(response) {
                        counter++
                        review.online = true
                        updateOnline(context,review)
                    }
                }

                return@withContext counter
            }
        }

        suspend fun getReviewsForGame(igdb_id: Int): List<GameReview> {
            return withContext(Dispatchers.IO){
                var response = AccountApiConfig.retrofit.getReviewsForGame(igdb_id)
                val responseBody = response.body()
                return@withContext responseBody!!
            }
        }

        suspend fun deleteAllReviews(): ReviewResponse {
            return withContext(Dispatchers.IO){
                var response = AccountApiConfig.retrofit.deleteAllReviews()
                val responseBody = response.body()
                return@withContext responseBody!!
            }
        }
    }
}