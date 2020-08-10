package adeel.moviedb.data.repositories

import adeel.moviedb.data.database.databaseResults.PopularResults
import adeel.moviedb.data.database.localCache.PopularLocalCache
import adeel.moviedb.data.network.NetworkService
import adeel.moviedb.ui.base.boundaryCallbacks.PopularBoundaryCallbacks
import androidx.paging.LivePagedListBuilder


class PopularRepository(
    private val service: NetworkService,
    private val popularCache: PopularLocalCache
) {

    fun popular(region: String): PopularResults {

        val dataSourceFactory = popularCache.getAllPopular()

        val boundaryCallback = PopularBoundaryCallbacks(region, service, popularCache)
        val networkErrors = boundaryCallback.networkErrors

        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .build()
        return PopularResults(data, networkErrors)
    }



    companion object {
        private const val NETWORK_PAGE_SIZE = 50
        private const val DATABASE_PAGE_SIZE = 60
    }

}