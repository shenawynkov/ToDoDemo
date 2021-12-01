package com.shenawynkov.tododemo.di

import com.shenawynkov.tododemo.data.database.TodoDatabase
import com.shenawynkov.tododemo.data.repo.TodoRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {

    @Provides
    @Singleton
    fun provideHomeRepository(db:TodoDatabase): TodoRepo {
        return TodoRepo(db)
    }

}