package ba.etf.rma23.data.repositories

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(GameReview::class), version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameReviewDAO(): GameReviewDAO
    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = buildRoomDB(context)
            }
            return INSTANCE!!
        }

        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "game-db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}