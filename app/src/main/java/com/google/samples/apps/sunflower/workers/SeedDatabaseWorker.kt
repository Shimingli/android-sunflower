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

package com.google.samples.apps.sunflower.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.samples.apps.sunflower.data.AppDatabase
import com.google.samples.apps.sunflower.data.Plant
import com.google.samples.apps.sunflower.utilities.PLANT_DATA_FILENAME
import kotlinx.coroutines.coroutineScope

/**
 * CoroutineWorker :提供与Kotlin协同程序互操作的[ListenableWorker]实现
 * 插入数据
 */
class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    // 初始化TAG的名称
    private val TAG by lazy { SeedDatabaseWorker::class.java.simpleName }
    // suspend  Kotlin Coroutine，只需增加 suspend 关键字，就能达到同 Callback 风格相同的效率。
    override suspend fun doWork(): Result = coroutineScope {

        try {
            //打开资源的文件
            applicationContext.assets.open(PLANT_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val plantType = object : TypeToken<List<Plant>>() {}.type
                    val plantList: List<Plant> = Gson().fromJson(jsonReader, plantType)
                    //shiming  name===applicationContext DefaultDispatcher-worker-1
                    println("shiming  name===applicationContext "+      Thread.currentThread().name)
                    val database = AppDatabase.getInstance(applicationContext)
                    database.plantDao().insertAll(plantList)

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }
}