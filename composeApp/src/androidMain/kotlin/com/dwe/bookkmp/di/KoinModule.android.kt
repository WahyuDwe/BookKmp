package com.dwe.bookkmp.di

import com.dwe.bookkmp.database.getDatabaseBuilder
import org.koin.core.module.Module
import org.koin.dsl.module

actual val targetModule = module {
    single { getDatabaseBuilder(context = get()) }
}