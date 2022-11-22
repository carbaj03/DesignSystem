package com.fintonic.designsystem.components.toolbar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.fintonic.designsystem.R
import com.fintonic.designsystem.foundation.AppColor
import com.fintonic.designsystem.foundation.AppTheme

@Composable
fun ItemBack(
    color: AppColor = AppTheme.colors.onToolbar,
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_left),
            contentDescription = null,
            tint = color.color
        )
    }
}

@Composable
fun ItemClose(
    color: AppColor = AppTheme.colors.onToolbar,
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = null,
            tint = color.color
        )
    }
}

@Composable
fun ItemQuestion(
    color: AppColor = AppTheme.colors.onToolbar,
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_question),
            contentDescription = null,
            tint = color.color
        )
    }
}

@Composable
fun ItemContactUs(
    color: AppColor = AppColor.Blue30,
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_info),
            contentDescription = null,
            tint = color.color
        )
    }
}
