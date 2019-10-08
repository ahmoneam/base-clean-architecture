package com.ahmoneam.basecleanarchitecture.utils

import com.ahmoneam.basecleanarchitecture.BuildConfig


fun isDevelopmentDebug(block: () -> Unit) {
    if (BuildConfig.DEBUG && BuildConfig.FLAVOR.contains("develop", ignoreCase = true)) {
        block.invoke()
    }
}