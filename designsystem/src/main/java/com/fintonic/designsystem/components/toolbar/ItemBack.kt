package com.fintonic.designsystem.components.toolbar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.fintonic.designsystem.R
import com.fintonic.designsystem.foundation.AppColor
import com.fintonic.designsystem.foundation.AppTheme

@Composable
fun ItemBack(onBack: () -> Unit, color : AppColor = AppTheme.colors.onToolbar) {
    IconButton(onClick = onBack) {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_left),
            contentDescription = null,
            tint = color.color
        )
    }
}

@Composable
fun ItemClose(onClose: () -> Unit , color : AppColor = AppTheme.colors.onToolbar) {
    IconButton(onClick = onClose) {
        Icon(
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = null,
            tint = color.color
        )
    }
}

@Composable
fun ItemQuestion(onQuestion: () -> Unit, color : AppColor = AppTheme.colors.onToolbar) {
    IconButton(onClick = onQuestion) {
        Icon(
            painter = painterResource(id = R.drawable.ic_question),
            contentDescription = null,
            tint = color.color
        )
    }
}

@Composable
fun ItemContactUs(onContactUs: () -> Unit, color : AppColor = AppColor.Blue30) {
    IconButton(onClick = onContactUs) {
        Icon(
            painter = painterResource(id = R.drawable.ic_info),
            contentDescription = null,
            tint = color.color
        )
    }
}
