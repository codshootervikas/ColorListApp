package com.example.colorlistapp.di

import com.example.colorlistapp.ColorRepository
import com.example.colorlistapp.db.ColorDao
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


//@Module
//@InstallIn(SingletonComponent::class)
//abstract class RepositoryBind {
//
//    @Singleton
//    @Binds
//    abstract fun bindColorRepository( colorRepository: ColorRepository ): ColorDao
//
//}