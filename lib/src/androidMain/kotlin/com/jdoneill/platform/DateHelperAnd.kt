package com.jdoneill.platform

import java.util.Date

actual fun getCurrentDate(): String = Date().toString()
