package adeel.moviedb.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Singleton Class
class NetworkService private constructor() {
    private val retrofit: Retrofit
    private var api: TMDBApi? = null

    val tmdbApi: TMDBApi
        get() {
            if (api == null) {
                api = retrofit.create<TMDBApi>(TMDBApi::class.java)
            }
            return api!!
        }

    init {
        val client = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        client.addInterceptor(loggingInterceptor)

        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build()
    }

    companion object {
        private val BASE_URL = "https://api.themoviedb.org/3/"
        private var networkService: NetworkService? = null

        val instance: NetworkService
            get() {
                if (networkService == null) {
                    networkService = NetworkService()
                }
                return networkService!!
            }
    }
}