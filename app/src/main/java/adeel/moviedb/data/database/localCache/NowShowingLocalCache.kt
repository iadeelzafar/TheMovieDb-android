package adeel.moviedb.data.database.localCache

import adeel.moviedb.data.database.dao.DaoNowShowing
import adeel.moviedb.data.database.entities.NowShowingEntity
import androidx.paging.DataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executor

class NowShowingLocalCache(
    private val nowShowingDao: DaoNowShowing,
    private val ioExecutor: Executor
) {

    /**
     * Insert a list of searches in the database, on a background thread.
     */
    fun insert(repos: List<NowShowingEntity>, insertFinished: ()-> Unit) {
        ioExecutor.execute {
            nowShowingDao.insert(repos)
            insertFinished()
        }
    }

    fun getAllNowShowing(): DataSource.Factory<Int, NowShowingEntity> {
        return nowShowingDao.loadAllNowShowing()
    }

    fun getAllItemsInNowShowing(): Int {
        val data  = runBlocking {
            async(Dispatchers.Default) {
                val numItems = nowShowingDao.getNumberOfRows()
                return@async numItems
            }.await()
        }
        return data

    }

}