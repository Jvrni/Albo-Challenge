package com.jvrni.albo_challenge.di

import com.jvrni.core.service.BuildConfig
import com.jvrni.core.service.Interceptor
import com.jvrni.core.service.ValueApi
import com.jvrni.core.service.repository.ValueRepository
import com.jvrni.core.service.repository.ValueRepositoryImpl
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * ENGLISH
 *
 * Creation of dependency on our services ([provideValueApi]) and repositories ([provideValueRepository]).
 * We need to have an instance of Retrofit ([provideRetrofit]) and OkHttpClient ([provideOkHttpClient]) to communicate with our web service.
 *
 * .
 *
 * .
 *
 * SPANISH
 *
 * Creación de dependencia de nuestros servicios ([provideValueApi]) y repositorios ([provideValueRepository]).
 * Necesitamos tener una instancia de Retrofit ([provideRetrofit]) y OkHttpClient ([provideOkHttpClient]) para comunicarnos con nuestro servicio web.
 */

val serviceModule = module {
    factory { Interceptor() }
    factory { provideOkHttpClient(get()) }
    factory { provideValueApi(get()) }
    factory { provideValueRepository(get()) }

    single { provideRetrofit(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder().baseUrl(BuildConfig.API_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()

fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient =
    OkHttpClient().newBuilder().addInterceptor(interceptor).build()

fun provideValueApi(retrofit: Retrofit): ValueApi = retrofit.create(ValueApi::class.java)

fun provideValueRepository(api: ValueApi): ValueRepository = ValueRepositoryImpl(api)
