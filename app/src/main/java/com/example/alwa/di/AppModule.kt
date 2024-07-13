package com.example.alwa.di

import com.example.alwa.ui.AppViewModel
import com.example.alwa.data.AppRepository
import com.example.alwa.data.AppRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<AppRepository> { AppRepositoryImpl(get()) }
    viewModel { AppViewModel(get()) }
}