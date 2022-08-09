package com.fintonic.designsystem.components.input

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.fintonic.designsystem.R
import com.fintonic.designsystem.components.SpacerVertical
import com.fintonic.designsystem.components.text.Text
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
        Text(text = label, style = appTypography.bodyL, color = AppColor.Gray100)

        Row {
            Box(
                modifier = modifier
                    .weight(1f)
            ) {
                if (!focused && text.isBlank())
                    Text(text = placeholder ?: label, style = appTypography.bodyL, color = AppColor.Gray70)

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
                is SubText.Error -> Text(text = it.text, style = appTypography.bodyS, color = AppColor.Coral)
                is SubText.Info -> Text(text = it.text, style = appTypography.bodyS, color = AppColor.Gray70)
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
        Row {
            Box(
                modifier = modifier
                    .weight(1f)
            ) {
                if (!focused && text.isBlank())
                    Text(text = placeholder ?: label, style = appTypography.bodyL, color = AppColor.Gray70)

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
                is SubText.Error -> Text(text = it.text, style = appTypography.bodyS, color = AppColor.Coral)
                is SubText.Info -> Text(text = it.text, style = appTypography.bodyS, color = AppColor.Gray70)
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
        Row {
            Box(
                modifier = modifier
                    .weight(1f)
            ) {
                if (!focused && text.isBlank())
                    Text(text = placeholder, style = appTypography.bodyL, color = AppColor.Gray70)

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
                is SubText.Error -> Text(text = it.text, style = appTypography.bodyS, color = AppColor.Coral)
                is SubText.Info -> Text(text = it.text, style = appTypography.bodyS, color = AppColor.Gray70)
            }
        }
    }
}

@Composable
fun InputCurrency(
    text: String,
    onTextChange: (String) -> Unit,
    currency: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    subText: SubText? = null,
    keyboardActions: KeyboardActions = KeyboardActions(),
    maxLines: Int = 1,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    allowDecimals: Boolean = false
) {
    val regex = remember(allowDecimals) {
        if (allowDecimals) "^\\d*\\.?\\d*${'$'}".toRegex()
        else "^\\d+\$".toRegex()
    }

    var focused by remember { mutableStateOf(false) }

    val focusRequester = FocusRequester()

    var textFieldValueState by remember {
        mutableStateOf(
            TextFieldValue(
                text = text,
                selection = TextRange(
                    index = text.length
                )
            )
        )
    }

    if (textFieldValueState.text != text) {
        textFieldValueState = textFieldValueState.copy(text)
    }

    Column(
        modifier = modifier
            .defaultMinSize(minWidth = TextFieldDefaults.MinWidth)
            .onFocusChanged { focused = it.isFocused }
    ) {
        Row {
            Box(
                modifier = modifier
                    .weight(1f)
            ) {
                if (!focused && text.isBlank())
                    Text(
                        text = placeholder,
                        style = appTypography.bodyL,
                        color = AppColor.Gray70,
                        textAlign = TextAlign.End
                    )

                BasicTextField(
                    modifier = modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    value = textFieldValueState,
                    onValueChange = {
                        if (it.text.contains(regex) || it.text.isBlank()) {
                            textFieldValueState = it
                            onTextChange(it.text)
                        }
                    },
                    textStyle = appTypography.headingM.copy(textAlign = TextAlign.End, color = AppColor.Black.color),
                    keyboardActions = keyboardActions,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = maxLines,
                    singleLine = maxLines == 1,
                    enabled = enabled,
                    readOnly = readOnly,
                    cursorBrush = SolidColor(AppColor.Blue.color),
                )
            }

            Text(
                text = currency,
                style = appTypography.headingM,
                color = AppColor.Black,
                modifier = Modifier.padding(start = 6.dp)
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
            SpacerVertical(height = 10.dp)
            when (it) {
                is SubText.Error -> Text(
                    text = it.text,
                    style = appTypography.bodyS,
                    color = AppColor.Coral,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
                is SubText.Info -> Text(
                    text = it.text,
                    style = appTypography.bodyS,
                    color = AppColor.Gray100,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

class AmountOrMessageVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return TransformedText(
            text = AnnotatedString(text.text.replace(Regex("""^\d*\.?\d*${'$'}"""), "")),
            offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int =
                    if (text.isDigitsOnly()) offset else offset - 1

                override fun transformedToOriginal(offset: Int): Int =
                    if (text.isDigitsOnly()) offset else offset + 1
            }
        )
    }
}

@Preview
@Composable
fun Test() {
    InputCurrency(
        text = "sd",
        onTextChange = { "" },
        placeholder = "sefa",
        currency = "€"
    )
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