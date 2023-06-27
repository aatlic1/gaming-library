package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AccountListViewModel() {
    val scope = CoroutineScope(Job() + Dispatchers.Main)
    fun saveGame(game:Game, onSuccess: (game: Game)->Unit, onError: () -> Unit){
        scope.launch {
            val result = AccountGamesRepository.saveGame(game)
            when(result){
                is Game -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }
    fun getSavedGame(onSuccess: (games: List<Game>)->Unit, onError: () -> Unit){
        scope.launch {
            val result = AccountGamesRepository.getSavedGames()
            when(result){
                is List<Game> -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }
    fun removeGame(gid: Int, onSuccess: (resoult: Boolean)->Unit, onError: () -> Unit){
        scope.launch {
            val result = AccountGamesRepository.removeGame(gid)
            when(result){
                is Boolean -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }
    fun removeNonSafe(onSuccess: (resoult: Boolean)->Unit, onError: () -> Unit){
        scope.launch {
            val result = AccountGamesRepository.removeNonSafe()
            when(result){
                is Boolean -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }
    fun getGamesContainingString(query: String,onSuccess: (games: List<Game>)->Unit, onError: () -> Unit){
        scope.launch {
            val result = AccountGamesRepository.getGamesContainingString(query)
            when(result){
                is List<Game> -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }
}