package com.fintonic.designsystem.components.legacy

import androidx.compose.runtime.Composable
import com.fintonic.designsystem.components.text.Text
import com.fintonic.designsystem.foundation.AppColor
import com.fintonic.designsystem.foundation.appTypography

@Composable
fun H1Black(text: String) {
    Text(text = text, style = appTypography.headingS, color = AppColor.Gray100)
}

@Composable
fun H2Black(text: String) {
    Text(text = text, style = appTypography.headingXS, color = AppColor.Gray100)
}

@Composable
fun H3Black(text: String) {
    Text(text = text, style = appTypography.bodyL, color = AppColor.Gray100)
}

@Composable
fun H3Gray(text: String) {
    Text(text = text, style = appTypography.bodyL, color = AppColor.Gray70)
}

@Composable
fun Micro1Black(text: String) {
    Text(text = text, style = appTypography.bodyM, color = AppColor.Gray100)
}

@Composable
fun Micro1Gray(text: String) {
    Text(text = text, style = appTypography.bodyM, color = AppColor.Gray70)
}

@Composable
fun Micro2Black(text: String) {
    Text(text = text, style = appTypography.bodyS, color = AppColor.Gray100)
}

@Composable
fun Micro2Gray(text: String) {
    Text(text = text, style = appTypography.bodyS, color = AppColor.Gray70)
}

@Composable
fun BodyBlack(text: String) {
    Text(text = text, style = appTypography.bodyL, color = AppColor.Gray100)
}

@Composable
fun BodyGray(text: String) {
    Text(text = text, style = appTypography.bodyL, color = AppColor.Gray70)
}

@Composable
fun LinkPrimary(text: String) {
    Text(text = text, style = appTypography.bodyM, color = AppColor.Blue)
}