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

package com.google.samples.apps.sunflower.adapters

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

/**
 * bindingadapter应用于方法，这些方法用于操作表达式中的值如何设置为视图。最简单的例子是有一个公共静态方法，它将视图和值设置为：
 */
@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("setTip")
fun setTip(view: TextView, setTip: String?) {
    println("shiming " +setTip)
    view.text = "我最牛逼了"
}