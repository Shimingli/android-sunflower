/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.samples.apps.sunflower.utilities.DATABASE_NAME
import com.google.samples.apps.sunflower.workers.SeedDatabaseWorker
import kotlin.concurrent.thread

/**
 * The Room database for this app
 */
@Database(entities = [GardenPlanting::class, Plant::class], version = 1, exportSchema = false)
// TypeConverters 指定Room可以使用的其他类型转换器。
// typeconverter被添加到元素的作用域中，因此如果将其放在类/接口上，该类中的所有方法/字段都将能够使用转换器。
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun gardenPlantingDao(): GardenPlantingDao

    abstract fun plantDao(): PlantDao

    companion object {

        // For Singleton instantiation
        //用于单例实例化
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        //创建并预填充数据库。有关详细信息，请参阅本文：
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            //arch_disk_io_0  主线程
                            Thread.currentThread().name
                            println("shiming  name==="+      Thread.currentThread().name)
                            thread {
                                println("shiming  name=== Thread.currentThread().name "+      Thread.currentThread().name)
                            }
                            //同一个时间就只有一个请求
                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    })
                    .build()
        }
    }
}
