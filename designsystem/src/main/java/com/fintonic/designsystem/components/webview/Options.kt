package com.fintonic.designsystem.components.webview

import androidx.annotation.StringRes
import com.fintonic.designsystem.components.bottomsheet.BottomSheetModel

data class Options(
    @StringRes val title: Int,
    val actions: List<BottomSheetModel>,
)

data class MenuItem(
    val action: () -> Unit,
    val icon: Int
)