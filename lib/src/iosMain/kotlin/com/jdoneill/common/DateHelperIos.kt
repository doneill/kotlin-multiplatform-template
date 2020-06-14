package com.jdoneill.common

import platform.Foundation.NSDate

actual fun getCurrentDate() = NSDate().toString()
