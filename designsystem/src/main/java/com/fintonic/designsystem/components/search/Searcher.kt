package com.fintonic.designsystem.components.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fintonic.designsystem.R
import com.fintonic.designsystem.components.Spacer
import com.fintonic.designsystem.components.input.InputTextBasic
import com.fintonic.designsystem.foundation.AppColor

@Composable
fun Searcher(
    text: String,
    placeholder: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit = {},
    hasFocus: (Boolean) -> Unit = {},
) {
    Row(
        modifier = modifier
            .defaultMinSize(minWidth = 150.dp)
            .background(AppColor.Gray10.color, RoundedCornerShape(32.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = null
        )
        Spacer(12.dp)
        InputTextBasic(
            modifier = modifier.defaultMinSize(minWidth = 150.dp),
            text = text,
            onTextChange = onTextChange,
            placeholder = placeholder,
            keyboardActions = KeyboardActions(
                onSearch = { onSearch(text) }
            ),
            keyboardOptions = KeyboardOptions.Default,
            hasFocus = hasFocus,
            maxLines = 1
        )
    }
}

@Preview
@Composable
fun Searcher(){
    Searcher(text = "asdsfddsfsdfsff", placeholder = "sadfsdf", onTextChange = {})
}