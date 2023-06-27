package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Functions
import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.searchGames
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GamesRepository {
    companion object {
        suspend fun getGameDetails(id: Int): List<Game>? {
            return withContext(Dispatchers.IO){
                var response = IGDBApiConfig.retrofit.getGameById(id)
                var responseBody = response.body()
                return@withContext responseBody
            }
        }

        suspend fun getGamesByName(name: String): List<Game>{
            return withContext(Dispatchers.IO) {
                var response = IGDBApiConfig.retrofit.getGamesByName(name)
                val responseBody = response.body()
                searchGames = mutableListOf()
                if (responseBody != null) {
                    for(game in responseBody){
                        if (game.id!= null) {
                            searchGames.add(Functions.convertData(game))
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
                                            listOfGames.add(Functions.convertData(games[i]))
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