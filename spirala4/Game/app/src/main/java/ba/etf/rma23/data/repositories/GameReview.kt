package ba.etf.rma23.data.repositories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "gamereview")
data class GameReview(
    @ColumnInfo(name = "rating") @SerializedName("rating") var rating: Int?,
    @ColumnInfo(name = "review") @SerializedName("review")var review: String?,
    @ColumnInfo(name = "igdb_id") var igdb_id: Int,
    @ColumnInfo(name = "online") var online: Boolean,
    @SerializedName("timestamp") val timestamp: String = "",
    @SerializedName("student") val student: String = "",
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
)
