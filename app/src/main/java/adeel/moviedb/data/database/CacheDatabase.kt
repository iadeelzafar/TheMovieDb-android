package adeel.moviedb.data.database

import android.arch.persistence.room.*
import android.content.Context

@Database(entities = arrayOf(SearchEntry::class,
        NowShowingEntity::class, PopularEntry::class), version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class CacheDatabase: RoomDatabase() {

    companion object {
        private val LOG_TAG: String = CacheDatabase::class.simpleName.toString()
        private val LOCK: Any = Object()
        private val DATABSE_NAME: String = "movies"
        @Volatile
        private var sInstance: CacheDatabase? = null

        fun getInstance(context: Context): CacheDatabase{
            if (sInstance == null){
                synchronized(LOCK){
                    sInstance = Room.databaseBuilder(context.applicationContext,
                            CacheDatabase::class.java,CacheDatabase.DATABSE_NAME)
                            .build()
                }
            }
            return sInstance!!
        }

    }

    abstract fun nowShowingDao(): DaoNowShowing
    abstract fun poplarDao(): PopularDao
    abstract fun searchDao(): SearchDao

}