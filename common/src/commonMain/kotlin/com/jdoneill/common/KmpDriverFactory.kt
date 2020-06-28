package com.jdoneill.common

import com.jdoneill.db.KmpDb
import com.squareup.sqldelight.db.SqlDriver

expect class KmpDriverFactory {
    fun createDriver(): SqlDriver
}

fun createDb(kmpDriverFactory: KmpDriverFactory): KmpDb {
    val driver = kmpDriverFactory.createDriver()

    return KmpDb(driver)
}
