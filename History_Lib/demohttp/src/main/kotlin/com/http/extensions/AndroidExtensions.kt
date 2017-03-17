package com.http.extensions

import android.content.Context
import android.os.Build
import android.view.View

/**
 * Created by ZeroAries on 2016/2/22.
 */
val View.eContext: Context
    get() = context

inline fun supportsLollipop(code: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        code()
    }
}