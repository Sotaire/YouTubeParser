package com.tilek.youtubeparser.di

import android.content.Context
import androidx.room.Room
import com.tilek.youtubeparser.data.local.YoutubeDataBase
import com.tilek.youtubeparser.data.network.YouTubeApi
import com.tilek.youtubeparser.data.repository.YoutubeRepository
import com.tilek.youtubeparser.ui.details.DetailsViewModel
import com.tilek.youtubeparser.ui.main.MainViewModel
import com.tilek.youtubeparser.ui.noInternet.NoInternetFragment
import com.tilek.youtubeparser.ui.noInternet.NoInternetViewModel
import com.tilek.youtubeparser.ui.playlists.PlaylistViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://www.googleapis.com/"

val vmModule = module {
    viewModel { PlaylistViewModel(get()) }
    viewModel { MainViewModel() }
    viewModel { DetailsViewModel(get()) }
    viewModel { NoInternetViewModel() }
}

val appModule = module {
    single { androidContext().resources }
}

val networkModule = module {
    single { provideRetrofit(get()) }
    single { provideOkhttpClient() }
    factory { YouTubeApi.provideYoutubeApi(get()) }
}

val repositoryModule = module {
    factory { YoutubeRepository(get(), get()) }
}

val localModule = module {
    single { provideDatabase(androidContext()) }
}

fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
}

fun provideOkhttpClient(): OkHttpClient {
    return OkHttpClient().newBuilder() //для ограничения времени
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
}

fun provideDatabase(context: Context): YoutubeDataBase {
    return Room.databaseBuilder(
            context.applicationContext,
            YoutubeDataBase::class.java,
            "word_database"
    )
            .allowMainThreadQueries()
            .build()
}