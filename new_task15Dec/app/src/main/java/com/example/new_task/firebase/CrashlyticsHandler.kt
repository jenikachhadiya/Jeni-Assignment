package com.example.new_task.firebase

import java.lang.Thread.UncaughtExceptionHandler

class CrashlyticsHandler(val defaulfUncaughtExceptionHandler: UncaughtExceptionHandler):Thread.UncaughtExceptionHandler {

    override fun uncaughtException(p0: Thread, p1: Throwable) {



    }
}