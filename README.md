## TheMovieDb-android
Minimalist android movie database app built using Kotlin on top of TMDb API

### Features

### Splash screen
<p align="center">
<img src="https://raw.githubusercontent.com/iadeelzafar/TheMovieDb-android/master/Screenshots/Splash%20Screen.png?token=AIY3JBF7LATVPM3SRE3S76C7HH4QE" height="640" width="316" />
</p>

Built using Kotlin Coroutines.

### Movies list
<p align="center">
<img src="https://raw.githubusercontent.com/iadeelzafar/TheMovieDb-android/master/Screenshots/NowShowing.png?token=AIY3JBD24HWOLPJNNN3QMDC7HH4JS" height="640" width="316" />
</p>

2 Fragments i.e. Now Showing and Popular Movies (Poster, Title, Release date, Overview text)

### Detailed view 
<p align="center">
<img src="https://raw.githubusercontent.com/iadeelzafar/TheMovieDb-android/master/Screenshots/DetailedScreen.png?token=AIY3JBAU5WIQWHTVBB2HDRC7HH5WG" height="640" width="316" />
</p>
Detailed overview, Release date, Backdrop image, Title, Trailers

### Search Feature
<p align="center">
<img src="https://raw.githubusercontent.com/iadeelzafar/TheMovieDb-android/master/Screenshots/Search.gif?token=AIY3JBEYDT6INYX6AHYQWRC7HH4MY" height="640" width="316" />
</p>

Search in a directory of over 500,000+ movies

### Local Cache 
See your results offline. Built using Room.

### Libraries/Arch used:
* OkHttp3
* Retrofit
* Jetpack (LiveData, Lifecycle, Room)
* Glide
* Paging
* MVVM architecture
* Kotlin coroutines

### Usage
Go to ```app/java/adeel/moviedb/API_KEY.kt``` and replace the dummy string with your TMDb API key

``` val TMDB_API_KEY = "YOUR_API_KEY_HERE" ```
