package com.tilek.youtubeparser.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tilek.youtubeparser.converter.ContentDetailsConverter
import com.tilek.youtubeparser.converter.ImageInfoConverter
import com.tilek.youtubeparser.converter.MediumConverter
import com.tilek.youtubeparser.converter.SnippetConverter
import com.tilek.youtubeparser.data.models.PlaylistItem

@Database(entities = [PlaylistItem::class], version = 1, exportSchema = false)
@TypeConverters(
    ContentDetailsConverter::class,
    ImageInfoConverter::class,
    MediumConverter::class,
    SnippetConverter::class
)
abstract class YoutubeDataBase : RoomDatabase() {

    abstract fun wordDao(): YoutubeDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: YoutubeDataBase? = null

        fun getDatabase(context: Context): YoutubeDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    YoutubeDataBase::class.java,
                    "word_database"
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}