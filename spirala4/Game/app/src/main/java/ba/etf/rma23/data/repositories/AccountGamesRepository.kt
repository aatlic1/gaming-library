package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Functions
import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.GameRequest
import kotlinx.coroutines.*

class AccountGamesRepository {
    companion object{
        var achash: String = "d0d31e8e-9b28-47a1-8645-3ade1a3010f1"
        fun getHash(): String{
            return achash
        }
        fun setHash(acHash: String): Boolean{
            if(getHash() == "")
                return false
            else {
                this.achash = acHash
                return true
            }
        }
        fun setAge(age:Int): Boolean{
            if(Account.getAge() != null){
                    Account.setAge(age)
                    return true
            }
            else return false
        }

        suspend fun saveGame(game: Game): Game {
            return withContext(Dispatchers.IO) {
                var response = AccountApiConfig.retrofit.saveGame(achash, GameRequest(Functions.convertData(game)))
                val responseBody = response.body()
                return@withContext responseBody!!
            }
        }
        suspend fun getSavedGames(): List<Game> {
            return withContext(Dispatchers.IO) {
                var response = AccountApiConfig.retrofit.getSavedGames(achash)
                var responseBody = response.body()
                var convertedGames: MutableList<Game> = mutableListOf()
                if(responseBody != null){
                    for(game in responseBody){
                        if (game.igdb_id != -1 && game.igdb_id!= null) {
                            var response2 = IGDBApiConfig.retrofit.getGameById(game.igdb_id)
                            response2.body()?.get(0)?.let { Functions.convertData(it) }
                                ?.let { convertedGames.add(it) }
                        }
                    }
                }
                return@withContext convertedGames
            }
        }
        suspend fun removeGame(gid: Int): Boolean{
            return withContext(Dispatchers.IO){
                var response = AccountApiConfig.retrofit.removeGame(achash,gid)
                val responseBody = response.body()?.toString()
                val successMessage = "Game deleted"
                val success = responseBody?.contains(successMessage) == true
                return@withContext success
            }
        }
        suspend fun removeNonSafe(): Boolean{
            return withContext(Dispatchers.IO){
                var response = AccountApiConfig.retrofit.removeNonSafe()
                val responseBody = response.body()?.toString()
                val successMessage = "Games deleted"
                val success = responseBody?.contains(successMessage) == true
                return@withContext success
            }
        }
        suspend fun getGamesContainingString(query: String): List<Game>{
            val favoriteGames = getSavedGames()
            val games = mutableListOf<Game>()
            for(game in favoriteGames){
                if(game.title.contains(query, ignoreCase = true))
                    games.add(game)
            }
            return games
        }
    }
}