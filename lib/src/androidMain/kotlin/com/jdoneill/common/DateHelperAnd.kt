package com.jdoneill.common

import java.util.Date

actual fun getCurrentDate(): String = Date().toString()
