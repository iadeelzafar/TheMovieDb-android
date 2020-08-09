package adeel.moviedb.data.database.dao

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.paging.PagedList
import android.arch.persistence.room.*

/**
 * Popular is the class to CRUD our Popular movies in the Room database
 */

@Dao
interface PopularDao {

    @Query("SELECT * FROM popular ORDER BY popularity DESC, voteCount Desc")
    fun loadAllPopular(): DataSource.Factory<Int, PopularEntry>

    @Query("SELECT * FROM popular WHERE movieId = :id ORDER BY timeAdded")
    fun checkIfPopular(id: Int):Boolean

    @Insert
    fun insertPopular(popularEntry: PopularEntry)

    @Delete
    fun deletePopular(popularEntry: PopularEntry)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(searches: List<PopularEntry>)

    @Query("DELETE FROM popular")
    fun deleteAll()

    @Query("SELECT COUNT(movieId) FROM popular")
    fun getNumberOfRows(): Int

}