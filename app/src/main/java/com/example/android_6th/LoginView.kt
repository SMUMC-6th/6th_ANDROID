package com.example.android_6th

interface LoginView {
    fun onLoginSuccess(code: Int, result: Result)
    fun onLoginFailure()
}