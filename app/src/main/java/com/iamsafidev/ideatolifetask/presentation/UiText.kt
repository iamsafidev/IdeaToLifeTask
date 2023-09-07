package com.iamsafidev.ideatolifetask.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

sealed class UiText {
    data class DynamicString(val value: String) : UiText()
    data class StringResource(
        val id: Int,
        var args: List<Any> = emptyList()
    ) : UiText()

    @Composable
    fun asString(): String {
        val context = LocalContext.current.applicationContext
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(id, *args.toTypedArray())
        }
    }

}
