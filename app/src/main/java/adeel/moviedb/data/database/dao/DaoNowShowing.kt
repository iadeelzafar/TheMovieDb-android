package adeel.moviedb.data.database.dao

import adeel.moviedb.data.database.entities.NowShowingEntity
import androidx.paging.DataSource
import androidx.room.*

/**
 * DaoNowShowing is the class to CRUD our NowShowing movies in the Room database
 */

@Dao
 interface DaoNowShowing {

    @Query("SELECT * FROM nowshowing ORDER BY timeAdded ASC")
    fun loadAllNowShowing(): DataSource.Factory<Int, NowShowingEntity>

    @Query("SELECT * FROM nowshowing WHERE movieId = :id ORDER BY timeAdded")
    fun checkIfNowShowing(id: Int):Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNowShowing(nowShowingEntity: NowShowingEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(searches: List<NowShowingEntity>)

    @Delete
    fun deleteNowShowing(nowShowingEntity: NowShowingEntity)

    @Query("DELETE FROM nowshowing")
    fun deleteAll()

    @Query("SELECT COUNT(movieId) FROM nowshowing")
    fun getNumberOfRows(): Int

}