package com.dwe.bookkmp.di

import com.dwe.bookkmp.presentation.screen.home.HomeViewModel
import com.dwe.bookkmp.room.getRoomDatabase
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

expect val targetModule: Module

val sharedModule = module {
    single { getRoomDatabase(get()) }
    // inject viewModel
    viewModel { HomeViewModel(get()) }
}

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null
) {
    startKoin {
        config?.invoke(this)
        modules(
            targetModule,
            sharedModule,
        )
    }
}