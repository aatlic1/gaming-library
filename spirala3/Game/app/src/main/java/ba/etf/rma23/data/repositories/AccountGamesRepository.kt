package ba.etf.rma23.projekat.data.repositories

import android.util.Log
import android.widget.Toast
import ba.etf.rma23.projekat.DeleteResponse
import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.GameRequest
import com.google.gson.Gson
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
        private fun convertData(game:Game): Game{
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
                if (game.ageRatings != null && game.ageRatings.isNotEmpty()) {
                    val g =
                        game.ageRatings.find { ageRating -> ageRating.category == 1 || ageRating.category == 2 }
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
                        genre = genre + g.name + ','
                }
                var coverUrl: String = " "
                if (game.cover != null) {
                    coverUrl = game.cover.url
                }
                return Game(
                        game.id, game.title, platform, date, rating, coverUrl, esrb,
                        company, company, genre, game.description, listOf(), game.id
                    )

            }
        suspend fun saveGame(game: Game): Game {
            return withContext(Dispatchers.IO) {
                var response = AccountApiConfig.retrofit.saveGame(achash, GameRequest(convertData(game)))
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
                            response2.body()?.get(0)?.let { convertData(it) }
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
            val favoriteGames = AccountGamesRepository.getSavedGames()
            val games = mutableListOf<Game>()
            for(game in favoriteGames){
                if(game.title.contains(query, ignoreCase = true))
                    games.add(game)
            }
            return games
        }
    }
}