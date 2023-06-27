package ba.etf.rma23.projekat

import com.google.gson.annotations.SerializedName

var lastGame : Game? = null
var searchGames : MutableList<Game> = mutableListOf()

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

