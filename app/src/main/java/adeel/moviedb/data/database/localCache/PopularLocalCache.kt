package adeel.moviedb.data.database.localCache

import adeel.moviedb.data.database.dao.PopularDao
import adeel.moviedb.data.database.entities.PopularEntry
import androidx.paging.DataSource
import kotlinx.coroutines.async
import kotlinx.coroutines.*
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executor

class PopularLocalCache(
    private val popularDao: PopularDao,
    private val ioExecutor: Executor
) {

    /**
     * Insert a list of searches in the database, on a background thread.
     */
    fun insert(repos: List<PopularEntry>, insertFinished: ()-> Unit) {
        ioExecutor.execute {
            popularDao.insert(repos)
            insertFinished()
        }
    }

    fun getAllPopular(): DataSource.Factory<Int, PopularEntry> {
        return popularDao.loadAllPopular()
    }

    fun getAllItemsInPopular(): Int {
        val data  = runBlocking {
            async(Dispatchers.Default) {
                val numItems = popularDao.getNumberOfRows()
                return@async numItems
            }.await()
        }
        return data

    }

}