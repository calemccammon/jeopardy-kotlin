package com.mccammon.jeopardykotlin.ui.main

interface InputListener {
    fun onSkip()
    fun onSubmit(submittedAnswer: String)
    fun onShow()
}