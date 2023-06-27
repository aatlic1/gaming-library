package ba.etf.rma23.data.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface GameReviewDAO {
    @Query("SELECT * FROM gamereview WHERE online = 0")
    suspend fun getOfflineReviews(): List<GameReview>
    @Insert
    suspend fun insert(vararg review: GameReview)
    @Query("UPDATE gamereview SET online = :newOnline WHERE igdb_id = :id")
    suspend fun updateOnline(id: Int, newOnline: Boolean)
}