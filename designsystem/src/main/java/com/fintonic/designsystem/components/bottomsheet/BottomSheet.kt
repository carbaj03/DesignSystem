package com.fintonic.designsystem.components.bottomsheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
                style = appTypography.headingXS,
                color = AppColor.Gray70
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

interface BottomSheetModel {
    val text: Int
}