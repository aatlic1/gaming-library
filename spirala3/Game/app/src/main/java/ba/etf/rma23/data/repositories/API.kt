package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.DeleteResponse
import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.GameRequest
import retrofit2.Response
import retrofit2.http.*

interface API {
    @Headers(
        "Client-ID: dojxpsyffdj3jvm4uiyudeyazumczm",
        "Authorization: Bearer sst699o2b693zbvf7c7zpd9tlkmohz"
    )
    //dobavljanje imena platforme
    @GET("games/{id}")
    suspend fun getGameById(
        @Path("id") id: Int,
        @Query("fields") fields: String = "name,release_dates.human,age_ratings.rating,age_ratings.category, summary, genres.name, platforms.name, cover.url, involved_companies.company.name"
    ): Response<List<Game>>

    //dobavljanje igrica po imenu
    @Headers(
        "Client-ID: dojxpsyffdj3jvm4uiyudeyazumczm",
        "Authorization: Bearer sst699o2b693zbvf7c7zpd9tlkmohz"
    )
    @GET("games")
    suspend fun getGamesByName(
        @Query("search") name: String,
        @Query("fields") fields: String = "name,release_dates.human,age_ratings.rating,age_ratings.category, summary, genres.name, platforms.name, cover.url, involved_companies.company.name"
    ): Response<List<Game>>

    @Headers("Content-Type: application/json")
    @POST("/account/{aid}/game")
    suspend fun saveGame(
        @Path("aid") aid : String = AccountGamesRepository.achash,
        @Body game: GameRequest
    ): Response<Game>

    @Headers("Content-Type: application/json")
    @GET("/account/{aid}/games")
    suspend fun getSavedGames(
        @Path("aid") aid : String = AccountGamesRepository.achash
    ): Response<List<Game>>
    
    @DELETE("/account/{aid}/game/{gid}/")
    suspend fun removeGame(
        @Path("aid") aid: String = AccountGamesRepository.achash,
        @Path("gid") gid: Int
    ): Response<DeleteResponse>

    @DELETE("/account/{aid}/game")
    suspend fun removeNonSafe(
        @Path("aid") aid: String = AccountGamesRepository.achash
    ): Response<DeleteResponse>
}