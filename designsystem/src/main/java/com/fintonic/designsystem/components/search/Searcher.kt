package com.fintonic.designsystem.components.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
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
            modifier = modifier,
            text = text,
            onTextChange = onTextChange,
            placeholder = placeholder
        )
    }
}