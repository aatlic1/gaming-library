package ba.etf.rma23.projekat

import com.google.gson.annotations.SerializedName

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

data class ReviewResponse(
    @SerializedName("success") var success: Boolean,
    @SerializedName("obrisano") var deleted: Int
)