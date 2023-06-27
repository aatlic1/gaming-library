package ba.etf.rma23.data.repositories

import android.content.Context
import ba.etf.rma23.projekat.ReviewResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GameReviewListViewModel() {
    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun writeDB(context: Context, review:GameReview, onSuccess: (message: String) -> Unit, onError: () -> Unit){
        scope.launch{
            val result = GameReviewsRepository.writeDB(context, review)
            when(result){
                is String -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

    fun getOfflineReviews(context: Context, onSuccess: (reviews: List<GameReview>) -> Unit, onError: () -> Unit){
        scope.launch{
            val result = GameReviewsRepository.getOfflineReviews(context)
            when(result){
                is List<GameReview> -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

    fun sendReview(context: Context, review:GameReview, onSuccess: (response: Boolean) -> Unit, onError: () -> Unit){
        scope.launch{
            val result = GameReviewsRepository.sendReview(context, review)
            when(result){
                is Boolean -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

    fun sendOfflineReviews(context:Context, onSuccess: (counter: Int) -> Unit, onError: () -> Unit){
        scope.launch{
            val result = GameReviewsRepository.sendOfflineReviews(context)
            when(result){
                is Int -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

    fun getReviewsForGame(igdb_id: Int, onSuccess: (reviews: List<GameReview>) -> Unit, onError: () -> Unit){
        scope.launch{
            val result = GameReviewsRepository.getReviewsForGame(igdb_id)
            when(result){
                is List<GameReview> -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

    fun deleteAllReviews(onSuccess: (response: ReviewResponse) -> Unit, onError: () -> Unit){
        scope.launch{
            val result = GameReviewsRepository.deleteAllReviews()
            when(result){
                is ReviewResponse -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }
}