package com.fintonic.designsystem.components.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fintonic.designsystem.R
import com.fintonic.designsystem.components.SpacerHorizontal
import com.fintonic.designsystem.components.text.Text
import com.fintonic.designsystem.foundation.AppColor
import com.fintonic.designsystem.foundation.appTypography

@Composable
fun <A : BottomSheetModel> BottomSheet(
    title: String,
    values: List<A>,
    onClick: (A) -> Unit
) {
    LazyColumn(Modifier.padding(bottom = 10.dp)) {
        item {
            Text(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                text = title,
                style = appTypography.headingS,
                color = AppColor.Gray100
            )
        }

        items(values) {
            BottomSheetItem(
                type = it,
                onClick = onClick
            )
        }
    }
}

@Composable
fun BottomSheet(
    title: String,
    values: List<Pair<String, () -> Unit>>,
) {
    LazyColumn(Modifier.padding(bottom = 10.dp)) {
        item {
            Text(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                text = title,
                style = appTypography.headingS,
                color = AppColor.Gray100
            )
        }

        items(values) {
            BottomSheetItem(
                text = it.first,
                onClick = it.second
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BottomSheet(
    title: String,
    content: @Composable () -> Unit
) {
    val (item1, item2, item3, item4) = remember { FocusRequester.createRefs() }

    var a by remember { mutableStateOf("") }
    var b by remember { mutableStateOf("") }
    var c by remember { mutableStateOf("") }
    var d by remember { mutableStateOf("") }

    Column(Modifier.padding(bottom = 10.dp)) {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
            text = title,
            style = appTypography.headingXS,
            color = AppColor.Gray70
        )

        Row {
            Box(
                modifier = Modifier
                    .requiredWidth(32.dp)
                    .background(AppColor.Gray10.color)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                BasicTextField(
                    value = a, onValueChange = { a = it; if (it.isNotEmpty()) item1.requestFocus() },
                    Modifier
                        .wrapContentWidth()
                        .focusRequester(item4)
                        .onFocusChanged { if (it.hasFocus) a = "" }
                )
            }

            SpacerHorizontal(width = 16.dp)

            BasicTextField(
                value = b, onValueChange = { b = it; if (it.isNotEmpty()) item2.requestFocus() else item4.requestFocus() },
                Modifier
                    .defaultMinSize(10.dp)
                    .background(AppColor.Gray10.color)
                    .focusRequester(item1)
                    .onFocusChanged { if (it.hasFocus) b = "" }
            )

            SpacerHorizontal(width = 16.dp)

            BasicTextField(
                value = c, onValueChange = { c = it; if (it.isNotEmpty()) item3.requestFocus() else item1.requestFocus() },
                Modifier
                    .background(AppColor.Gray10.color)
                    .focusRequester(item2)
                    .onFocusChanged { if (it.hasFocus) c = "" }
            )

            SpacerHorizontal(width = 16.dp)

            BasicTextField(
                value = d, onValueChange = { d = it; if (it.isNotEmpty()) item2.requestFocus() },
                Modifier
                    .background(AppColor.Gray10.color)
                    .focusRequester(item3)
                    .onFocusChanged { if (it.hasFocus) d = "" }
            )

            SpacerHorizontal(width = 16.dp)

            Icon(painter = painterResource(id = R.drawable.ic_help), contentDescription = null)
        }

        Text(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
            text = title,
            style = appTypography.bodyS,
        )
    }
}

@Composable
fun <A : BottomSheetModel> BottomSheetItem(
    type: A,
    onClick: (A) -> Unit
) {
    Text(
        text = stringResource(id = type.text),
        modifier = Modifier
            .clickable { onClick(type) }
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .fillMaxWidth(),
        style = appTypography.bodyL,
        color = AppColor.Gray100
    )
}

@Composable
fun BottomSheetItem(
    text: String,
    onClick: () -> Unit
) {
    Text(
        text = text,
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .fillMaxWidth(),
        style = appTypography.bodyL,
        color = AppColor.Gray100
    )
}

interface BottomSheetModel {
    val text: Int
}