package ba.etf.rma23.projekat

import com.google.gson.annotations.SerializedName
import org.junit.Ignore

var lastGame : Game? = null
var searchGames : MutableList<Game> = mutableListOf()

data class DeleteResponse(
    val success: String
)

data class GameRequest(
    val game: Game
)

data class AgeRating(
    val id: Int,
    val category: Int,
    val rating: Int
)

data class Cover(
    val id: Int,
    val url: String
)

data class Genre(
    val id: Int,
    val name: String
)

data class Company(
    val id: Int,
    val name: String
)

data class InvolvedCompany(
    val id: Int,
    val company: Company
)

data class Platform(
    val id: Int,
    val name: String
)

data class ReleaseDate(
    val id: Int,
    val human: String
)

data class Game (
    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val title: String,
    val platform: String,
    val releaseDate: String,
    val rating: Double,
    val coverImage: String,
    val esrbRating: String,
    val developer: String,
    val publisher: String,
    val genre: String,
    @SerializedName("summary") val description: String?,
    val userImpressions: List<UserImpression> = listOf(),
    @SerializedName("igdb_id") val igdb_id: Int = 0,
    @SerializedName("cover") val cover: Cover = Cover(-1, " "),
    @SerializedName("age_ratings") val ageRatings: List<AgeRating> = listOf(),
    @SerializedName("genres") val genres: List<Genre> = listOf(),
    @SerializedName("involved_companies") val involvedCompanies: Array<InvolvedCompany> = arrayOf(),
    @SerializedName("platforms") val platforms: List<Platform> = listOf(),
    @SerializedName("release_dates") val releaseDates: List<ReleaseDate> = listOf(),
)

abstract class UserImpression(
    open val userName: String,
    open val timestamp: Long,
)
data class UserRating(
    override val userName: String,
    override val timestamp: Long,
    val rating: Double
): UserImpression(userName, timestamp)

data class UserReview(
    override val userName: String,
    override val timestamp: Long,
    val review: String
): UserImpression(userName, timestamp)

