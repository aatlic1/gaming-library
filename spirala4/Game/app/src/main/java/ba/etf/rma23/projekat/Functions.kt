package ba.etf.rma23.projekat

import android.content.Context
import android.widget.Toast

class Functions {
    companion object{
        fun getFields(): String {
            return "name,release_dates.human,age_ratings.rating,age_ratings.category, summary, genres.name, platforms.name, cover.url, involved_companies.company.name"
        }

        fun convertData(game:Game): Game{
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

        fun toastSuccess(context: Context){
            val toast = Toast.makeText(context, "Success", Toast.LENGTH_SHORT)
            toast.show()
        }

        fun toastError(context: Context){
            val toast = Toast.makeText(context, "Failed", Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}