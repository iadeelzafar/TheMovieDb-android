package adeel.moviedb.utils

class Constants {

    companion object {
        fun getGenre(id: Int): String {
            val genreMap = HashMap<Int,String>()
            genreMap.put(28, "Action")
            genreMap.put(12, "Adventure")
            genreMap.put(16, "Animation")
            genreMap.put(35, "Comedy")
            genreMap.put(80, "Crime")
            genreMap.put(99, "Documentary")
            genreMap.put(18, "Drama")
            genreMap.put(10751, "Family")
            genreMap.put(14, "Fantasy")
            genreMap.put(36, "History")
            genreMap.put(27, "Horror")
            genreMap.put(10402, "Music")
            genreMap.put(9648, "Mystery")
            genreMap.put(10749, "Romance")
            genreMap.put(878, "Science Fiction")
            genreMap.put(10770, "TV Movie")
            genreMap.put(53, "Thriller")
            genreMap.put(10752, "War")
            genreMap.put(37, "Western")

            return genreMap.get(id)!!
        }


        val RANDOM_PATH = "asdfghjkl"

        //CONTENT_TYPES
        val CONTENT_MOVIE = 0
        val CONTENT_SIMILAR = 3
        val CONTENT_PROGRESS = 777


        val NOWSHOWING: Int = 1234
        val POPULAR: Int = 1235
        val SEARCHES: Int = 1236

    }

}