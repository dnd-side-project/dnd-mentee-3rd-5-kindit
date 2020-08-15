package com.dnd.kindit.util

import android.util.Patterns
import java.util.regex.Pattern


class ValidationCheck {
    private val PASSWORD_PATTERN =
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{4,}$"

    var changeEmail: Boolean = false
    var changeNickname: Boolean = false
    var changePassword1: Boolean = false
    var changePassword2: Boolean = false

    fun checkEmailRegex(userInputEmail: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(userInputEmail).matches()
    }

    fun checkPasswordRegex(userInputPassword: String): Boolean {
        val pattern: Pattern = Pattern.compile(PASSWORD_PATTERN)
        return pattern.matcher(userInputPassword).matches()
    }

    fun checkAllValidation(): Boolean {
        return changeEmail && changeNickname && changePassword1 && changePassword2
    }
}