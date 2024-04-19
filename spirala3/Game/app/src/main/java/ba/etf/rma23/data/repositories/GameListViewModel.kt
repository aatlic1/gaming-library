package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GameListViewModel(
) {
    val scope = CoroutineScope(Job() + Dispatchers.Main)


    fun getGameDetails(id: Int, onSuccess: (game: List<Game>) -> Unit, onError: () -> Unit){
        scope.launch{
            val result = GamesRepository.getGameDetails(id)
            when(result){
                is List<Game> -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

    fun getGamesByName(name: String, onSuccess: (game: List<Game>)->Unit, onError: () -> Unit){
        scope.launch {
            val result = GamesRepository.getGamesByName(name)
            when(result){
                is List<Game> -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

    fun getGamesSafe(name: String, onSuccess: (game: List<Game>)->Unit, onError: () -> Unit){
        scope.launch {
            val result = GamesRepository.getGamesSafe(name)
            when(result){
                is List<Game> -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }
    fun sortGames(onSuccess: (game: List<Game>)->Unit, onError: () -> Unit){
        scope.launch {
            val result = GamesRepository.sortGames()
            when(result){
                is List<Game> -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }
}