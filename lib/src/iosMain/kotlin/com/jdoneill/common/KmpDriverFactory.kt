package com.jdoneill.common

import com.jdoneill.db.KmpDb
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class KmpDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(KmpDb.Schema, "kmp.db")
    }
}