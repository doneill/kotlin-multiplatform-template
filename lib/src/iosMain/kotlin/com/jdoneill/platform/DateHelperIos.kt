package com.jdoneill.platform

import platform.Foundation.NSDate

actual fun getCurrentDate() = NSDate().toString()
