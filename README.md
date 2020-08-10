## TheMovieDb-android
Minimalist android movie database app built using Kotlin on top of TMDb API

### Features

#### Splash screen

Built using Kotlin Coroutines.

#### Movies list
2 Fragments i.e. Now Showing and Popular Movies (Poster, Title, Release date, Overview text)

#### Detailed view 
Detailed overview, Release date, Backdrop image, Title, Trailers

#### Search Feature
Search in a directory of over 500,000+ movies

#### Local Cache 
See your results offline. Built using Room.

### Libraries/Arch used:
* OkHttp3
* Retrofit
* Jetpack (LiveData, Lifecycle, Room)
* Glide
* Paging
* MVVM architecture

### Usage
Go to ```app/java/adeel/moviedb/API_KEY.kt``` and replace the dummy string with your TMDb API key

``` val TMDB_API_KEY = "YOUR_API_KEY_HERE" ```
