package com.fintonic.designsystem.components.input

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fintonic.designsystem.R
import com.fintonic.designsystem.foundation.AppColor
import com.fintonic.designsystem.foundation.appTypography



@Composable
internal fun Input(
    text: String,
    onTextChange: (String) -> Unit,
    label: String,
    action: () -> Unit,
    modifier: Modifier = Modifier,
    subText: SubText? = null,
    placeholder: String? = null,
    maxLines: Int = Int.MAX_VALUE,
) {

    var focused by remember {
        mutableStateOf(false)
    }

    val focusRequester = FocusRequester()

    Column(
        modifier = modifier
            .defaultMinSize(minWidth = TextFieldDefaults.MinWidth)
            .onFocusChanged { focused = it.isFocused }
            .focusRequester(focusRequester)
            .focusable(true)
            .clickable {
                action()
                focusRequester.requestFocus()
            }
    ) {
        Text(text = label, style = appTypography.bodyL, color = AppColor.Gray100.color)

        Row {
            Box(
                modifier = modifier
                    .weight(1f)
            ) {
                if (!focused && text.isBlank())
                    Text(text = placeholder ?: label, style = appTypography.bodyL, color = AppColor.Gray70.color)

                BasicTextField(
                    modifier = modifier.fillMaxWidth(),
                    value = text,
                    onValueChange = { onTextChange(it) },
                    textStyle = appTypography.bodyL,
                    maxLines = maxLines,
                    singleLine = maxLines == 1,
                    enabled = false,
                    readOnly = true,
                    cursorBrush = SolidColor(AppColor.Blue.color),
                )
            }

            Icon(
                painter = if (focused) {
                    painterResource(id = R.drawable.ic_arrow_small_up)
                } else {
                    painterResource(id = R.drawable.ic_arrow_small_down)
                },
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        val color = when {
            subText.isError() && focused -> AppColor.Coral.color
            focused -> AppColor.Blue.color
            else -> AppColor.Gray30.color
        }

        Divider(
            Modifier
                .height(Dp(0.5f))
                .background(color = color)
        )

        subText?.let {
            when (it) {
                is SubText.Error -> Text(text = it.text, style = appTypography.bodyS, color = AppColor.Coral.color)
                is SubText.Info -> Text(text = it.text, style = appTypography.bodyS, color = AppColor.Gray70.color)
            }
        }
    }
}


@Composable
fun InputAction(
    text: String,
    onTextChange: (String) -> Unit,
    label: String,
    action: () -> Unit,
    modifier: Modifier = Modifier,
    subText: SubText? = null,
    placeholder: String? = null,
    maxLines: Int = Int.MAX_VALUE,
) {

    var focused by remember {
        mutableStateOf(false)
    }

    val focusRequester = FocusRequester()

    Column(
        modifier = modifier
            .defaultMinSize(minWidth = TextFieldDefaults.MinWidth)
            .onFocusChanged { focused = it.isFocused }
            .focusRequester(focusRequester)
            .focusable(true)
            .clickable {
                action()
                focusRequester.requestFocus()
            }
    ) {
//        Text(text = label, style = appTypography.bodyL, color = AppColor.Gray100.color)

        Row {
            Box(
                modifier = modifier
                    .weight(1f)
            ) {
                if (!focused && text.isBlank())
                    Text(text = placeholder ?: label, style = appTypography.bodyL, color = AppColor.Gray70.color)

                BasicTextField(
                    modifier = modifier.fillMaxWidth(),
                    value = text,
                    onValueChange = { onTextChange(it) },
                    textStyle = appTypography.bodyL,
                    maxLines = maxLines,
                    singleLine = maxLines == 1,
                    enabled = false,
                    readOnly = true,
                    cursorBrush = SolidColor(AppColor.Blue.color),
                )
            }

            Icon(
                painter = if (focused) {
                    painterResource(id = R.drawable.ic_arrow_small_up)
                } else {
                    painterResource(id = R.drawable.ic_arrow_small_down)
                },
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        val color = when {
            subText.isError() && focused -> AppColor.Coral.color
            focused -> AppColor.Blue.color
            else -> AppColor.Gray30.color
        }

        Divider(
            Modifier
                .height(Dp(0.5f))
                .background(color = color)
        )

        subText?.let {
            when (it) {
                is SubText.Error -> Text(text = it.text, style = appTypography.bodyS, color = AppColor.Coral.color)
                is SubText.Info -> Text(text = it.text, style = appTypography.bodyS, color = AppColor.Gray70.color)
            }
        }
    }
}

@Composable
fun InputText(
    text: String,
    onTextChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    label: String? = null,
    subText: SubText? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    maxLines: Int = Int.MAX_VALUE,
    enabled: Boolean = true,
    readOnly: Boolean = false,
) {

    var focused by remember {
        mutableStateOf(false)
    }

    val focusRequester = FocusRequester()

    Column(
        modifier = modifier
            .defaultMinSize(minWidth = TextFieldDefaults.MinWidth)
            .onFocusChanged { focused = it.isFocused }
    ) {
//        Text(text = label, style = appTypography.bodyL, color = AppColor.Gray100.color)

        Row {
            Box(
                modifier = modifier
                    .weight(1f)
            ) {
                if (!focused && text.isBlank())
                    Text(text = placeholder, style = appTypography.bodyL, color = AppColor.Gray70.color)

                BasicTextField(
                    modifier = modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    value = text,
                    onValueChange = { onTextChange(it) },
                    textStyle = appTypography.bodyL,
                    keyboardActions = keyboardActions,
                    keyboardOptions = keyboardOptions,
                    maxLines = maxLines,
                    singleLine = maxLines == 1,
                    enabled = enabled,
                    readOnly = readOnly,
                    cursorBrush = SolidColor(AppColor.Blue.color),
                )
            }

            if (text.isNotBlank() || !readOnly && !enabled)
                Icon(
                    modifier = Modifier.clickable {
                        onTextChange("")
                        focusRequester.requestFocus()
                    },
                    imageVector = Icons.Default.Clear,
                    contentDescription = null
                )
        }

        Spacer(modifier = Modifier.height(4.dp))

        val color = when {
            subText.isError() && focused -> AppColor.Coral.color
            focused -> AppColor.Blue.color
            else -> AppColor.Gray30.color
        }

        Divider(
            Modifier
                .height(1f.dp)
                .background(color)
        )

        subText?.let {
            when (it) {
                is SubText.Error -> Text(text = it.text, style = appTypography.bodyS, color = AppColor.Coral.color)
                is SubText.Info -> Text(text = it.text, style = appTypography.bodyS, color = AppColor.Gray70.color)
            }
        }
    }
}


sealed class SubText {
    data class Error(
        val text: String,
    ) : SubText()

    data class Info(
        val text: String
    ) : SubText()

    fun isError(): Boolean =
        this is Error
}

fun SubText?.isError(): Boolean =
    this?.isError() ?: false