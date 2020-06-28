package com.jdoneill.common

import android.content.Context
import com.jdoneill.db.KmpDb
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class KmpDriverFactory(private val appContext: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(KmpDb.Schema, appContext, "kmp.db")
    }
}