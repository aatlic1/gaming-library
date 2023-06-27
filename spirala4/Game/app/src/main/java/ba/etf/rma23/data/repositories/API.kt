package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.data.repositories.GameReview
import ba.etf.rma23.projekat.*
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
        @Query("fields") fields: String = Functions.getFields()
    ): Response<List<Game>>

    //dobavljanje igrica po imenu
    @Headers(
        "Client-ID: dojxpsyffdj3jvm4uiyudeyazumczm",
        "Authorization: Bearer sst699o2b693zbvf7c7zpd9tlkmohz"
    )
    @GET("games")
    suspend fun getGamesByName(
        @Query("search") name: String,
        @Query("fields") fields: String = Functions.getFields()
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

    @Headers("Content-Type: application/json")
    @POST("/account/{aid}/game/{gid}/gamereview")
    suspend fun sendReview(
        @Path("aid") aid: String = AccountGamesRepository.achash,
        @Path("gid") gid: Int,
        @Body review: GameReview
    ): Response<GameReview>

    @Headers("Content-Type: application/json")
    @GET("/game/{gid}/gamereviews")
    suspend fun getReviewsForGame(
        @Path("gid") gid: Int
    ): Response<List<GameReview>>

    @DELETE("/account/{aid}/gamereviews")
    suspend fun deleteAllReviews(
        @Path("aid") aid: String = AccountGamesRepository.achash
    ): Response<ReviewResponse>
}