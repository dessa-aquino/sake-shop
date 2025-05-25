package com.example.sakeshop.di

import com.example.sakeshop.data.repository.LocalSakeStoreRepository
import com.example.sakeshop.domain.repository.SakeStoreRepository
import com.example.sakeshop.presentation.viewmodel.SakeStoreViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<SakeStoreRepository> { LocalSakeStoreRepository(get()) }

    viewModel { SakeStoreViewModel(get()) }
}
