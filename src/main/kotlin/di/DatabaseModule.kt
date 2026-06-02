package com.br.di

import com.br.utils.Constants.MONGODB_URI_LOCAL
import com.br.utils.Constants.DATABASE_NAME
import com.mongodb.kotlin.client.coroutine.MongoClient
import org.koin.dsl.module

object DatabaseModule {

    val module = module {
        single {
            val client = MongoClient.create(System.getenv(MONGODB_URI_LOCAL))
            client.getDatabase(System.getenv(DATABASE_NAME))
        }
    }
}