package com.jvrni.albo_challenge.di

import com.jvrni.albo_challenge.ui.MainViewModel
import com.jvrni.feature.card.CardViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

/**
 * ENGLISH
 *
 * Creating ViewModel dependency injection with the repository, which we have the dependency on in our [serviceModule].
 *
 * .
 *
 * .
 *
 * SPANISH
 *
 * Creando la inyección de dependencia de ViewModel con el repositorio, del cual tenemos la dependencia en nuestro [serviceModule].
 */

val uiModule = module {

    // ViewModel for MainViewModel
    viewModelOf(::MainViewModel)

    // ViewModel for CardViewModel
    viewModelOf(::CardViewModel)
}