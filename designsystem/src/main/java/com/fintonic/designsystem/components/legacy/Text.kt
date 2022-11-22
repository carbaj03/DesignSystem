package com.fintonic.designsystem.components.legacy

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.fintonic.designsystem.components.text.Text
import com.fintonic.designsystem.foundation.AppColor
import com.fintonic.designsystem.foundation.appTypography

@Composable
fun DisplayPrimary(text: String, modifier: Modifier = Modifier, textAlign: TextAlign? = null) {
    Text(modifier = modifier, text = text, style = appTypography.headingS, color = AppColor.Gray100, textAlign = textAlign)
}

@Composable
fun H1Black(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null,
) {
    Text(modifier = modifier, text = text, style = appTypography.headingS, color = AppColor.Gray100, textAlign = textAlign)
}

@Composable
fun H2Black(text: String, modifier: Modifier = Modifier, textAlign: TextAlign? = null) {
    Text(modifier = modifier, text = text, style = appTypography.headingXS, color = AppColor.Gray100, textAlign = textAlign)
}

@Composable
fun H3Black(text: String, modifier: Modifier = Modifier, textAlign: TextAlign? = null) {
    Text(modifier = modifier, text = text, style = appTypography.bodyL, color = AppColor.Gray100, textAlign = textAlign)
}

@Composable
fun H3Gray(text: String, modifier: Modifier = Modifier, textAlign: TextAlign? = null) {
    Text(modifier = modifier, text = text, style = appTypography.bodyL, color = AppColor.Gray70, textAlign = textAlign)
}

@Composable
fun Micro1Black(text: String, modifier: Modifier = Modifier, textAlign: TextAlign? = null) {
    Text(modifier = modifier, text = text, style = appTypography.bodyM, color = AppColor.Gray100, textAlign = textAlign)
}

@Composable
fun Micro1Gray(text: String, modifier: Modifier = Modifier, textAlign: TextAlign? = null) {
    Text(modifier = modifier, text = text, style = appTypography.bodyM, color = AppColor.Gray70, textAlign = textAlign)
}

@Composable
fun Micro2Black(text: String, modifier: Modifier = Modifier, textAlign: TextAlign? = null) {
    Text(modifier = modifier, text = text, style = appTypography.bodyS, color = AppColor.Gray100, textAlign = textAlign)
}

@Composable
fun Micro2Gray(text: String, modifier: Modifier = Modifier, textAlign: TextAlign? = null) {
    Text(modifier = modifier, text = text, style = appTypography.bodyS, color = AppColor.Gray70, textAlign = textAlign)
}

@Composable
fun BodyBlack(text: String, modifier: Modifier = Modifier, textAlign: TextAlign? = null) {
    Text(modifier = modifier, text = text, style = appTypography.bodyL, color = AppColor.Gray100, textAlign = textAlign)
}

@Composable
fun BodyGray(text: String, modifier: Modifier = Modifier, textAlign: TextAlign? = null) {
    Text(modifier = modifier, text = text, style = appTypography.bodyL, color = AppColor.Gray70, textAlign = textAlign)
}

@Composable
fun LinkPrimary(text: String, modifier: Modifier = Modifier, textAlign: TextAlign? = null) {
    Text(modifier = modifier, text = text, style = appTypography.bodyM, color = AppColor.Blue, textAlign = textAlign)
}