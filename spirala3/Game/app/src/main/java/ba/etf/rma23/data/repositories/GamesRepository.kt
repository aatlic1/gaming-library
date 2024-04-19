package ba.etf.rma23.projekat.data.repositories

import android.util.Log
import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.searchGames
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class GamesRepository {
    companion object {
        suspend fun getGameDetails(id: Int): List<Game>? {
            return withContext(Dispatchers.IO){
                var response = IGDBApiConfig.retrofit.getGameById(id)
                var responseBody = response.body()
                return@withContext responseBody
            }
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
        suspend fun getGamesByName(name: String): List<Game>{
            return withContext(Dispatchers.IO) {
                var response = IGDBApiConfig.retrofit.getGamesByName(name)
                val responseBody = response.body()
                searchGames = mutableListOf()
                if (responseBody != null) {
                    for(game in responseBody){
                        if (game.id!= null) {
                            searchGames.add(convertData(game))
                        }
                    }
                }
                return@withContext searchGames
            }
        }
        suspend fun getGamesSafe(name: String): List<Game>{
            return withContext(Dispatchers.IO) {
                var response = IGDBApiConfig.retrofit.getGamesByName(name)
                var games = response.body()
                var listOfGames: MutableList<Game> = mutableListOf()
                if (games != null) {
                    if(games.isNotEmpty())  {
                            for(i in 0 until games.size){
                                if(games[i]?.ageRatings != null && games[i].ageRatings.isNotEmpty()){
                                    for(j in 0 until games[i].ageRatings.size)
                                        if((games[i].ageRatings[j].category == 1 && games[i].ageRatings[j].rating in 7..10) ||
                                            (games[i].ageRatings[j].category == 2 && games[i].ageRatings[j].rating in 1..4))
                                            listOfGames.add(convertData(games[i]))
                                }
                            }
                    }
                }
                games = listOfGames
                return@withContext games!!
            }
        }

        suspend fun sortGames(): List<Game>{
            var favorite = AccountGamesRepository.getSavedGames() as MutableList<Game>
            var fGames: MutableList<Game> = mutableListOf()
            var sGames: MutableList<Game> = mutableListOf()

            for(searched in searchGames){
                var isAdd: Boolean = false
                for(favorites in favorite){
                    if(favorites.id == searched.id){
                        fGames.add(favorites)
                        isAdd = true
                    }
                }
                if(!isAdd)
                    sGames.add(searched)
            }

            return (fGames.sortedBy{it.title} + sGames.sortedBy{it.title})
        }

    }
}