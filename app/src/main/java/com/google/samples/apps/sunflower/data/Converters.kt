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

import androidx.room.TypeConverter
import java.util.Calendar

/**
 * Type converters to allow Room to reference complex data types.
 * 类型转换器，允许空间引用复杂的数据类型。
 */
class Converters {
    /**
     * TypeConverter 将方法标记为类型转换器。类可以有任意多个@typeconverter方法。
     */
    @TypeConverter fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    /**
     * 把毫秒值转化为一个对象
     */
    @TypeConverter fun datestampToCalendar(value: Long): Calendar =
            Calendar.getInstance().apply { timeInMillis = value }
}