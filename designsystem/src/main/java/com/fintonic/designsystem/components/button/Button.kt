package com.fintonic.designsystem.components.button

import android.graphics.drawable.Icon
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fintonic.designsystem.components.text.Text
import com.fintonic.designsystem.foundation.*

@Composable
internal fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: AppColor,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = RoundedCornerShape(48.dp),
    content: @Composable RowScope.() -> Unit,
) {
    Box(
        modifier = Modifier
            .background(color.color, shape)
            .clip(shape)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(),
                enabled = enabled,
                role = Role.Button,
                onClick = onClick
            )
    ) {
        ProvideTextStyle(
            value = AppTheme.typography.bodyM
        ) {
            Row(
                modifier = modifier
                    .defaultMinSize(
                        minWidth = ButtonDefaults.MinWidth,
                        minHeight = ButtonDefaults.MinHeight
                    )
                    .padding(horizontal = ButtonHorizontalPadding, vertical = ButtonVerticalPadding),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                content = content
            )
        }
    }
}

@Composable
fun ButtonPrimary(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    @DrawableRes iconLeft: Int? = null,
    @DrawableRes iconRight: Int? = null,
) {
    val colorText = AppTheme.buttonColors.primary.colorFor(enabled)
    val color = AppTheme.buttonColors.primary(enabled)
    val textAlign = remember(iconRight, iconLeft) {
        if (iconRight == null && iconLeft == null) TextAlign.Center else null
    }

    Button(onClick = onClick, enabled = enabled, color = color, modifier = modifier) {
        iconLeft?.let {
            Icon(painter = painterResource(id = it), contentDescription = null, tint = colorText.color)
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(style = appTypography.bodyM, text = text, color = colorText, textAlign = textAlign)
        iconRight?.let {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(painterResource(id = it), contentDescription = null, tint = colorText.color)
        }
    }
}

@Composable
fun ButtonSecondary(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    @DrawableRes iconLeft: Int? = null,
    @DrawableRes iconRight: Int? = null,
) {
    val colorText = AppTheme.buttonColors.secondary.colorFor(enabled)
    val color = AppTheme.buttonColors.secondary(enabled)
    val textAlign = remember(iconRight, iconLeft) {
        if (iconRight == null && iconLeft == null) TextAlign.Center else null
    }

    Button(onClick = onClick, enabled = enabled, color = color, modifier = modifier.border(BorderStroke(1.dp, colorText.color), RoundedCornerShape(48.dp))) {
        iconLeft?.let {
            Icon(painter = painterResource(id = it), contentDescription = null, tint = colorText.color)
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(style = appTypography.bodyM, text = text, color = colorText, textAlign = textAlign)
        iconRight?.let {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(painterResource(id = it), contentDescription = null, tint = colorText.color)
        }
    }
}

@Composable
fun ButtonTertiary(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    @DrawableRes iconLeft: Int? = null,
    @DrawableRes iconRight: Int? = null,
) {
    val colorText = AppTheme.buttonColors.tertiary.colorFor(enabled)
    val color = AppTheme.buttonColors.tertiary(enabled)
    val textAlign = remember(iconRight, iconLeft) {
        if (iconRight == null && iconLeft == null) TextAlign.Center else null
    }

    Button(onClick = onClick, enabled = enabled, color = color, modifier = modifier) {
        iconLeft?.let {
            Icon(painter = painterResource(id = it), contentDescription = null, tint = colorText.color)
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(style = appTypography.bodyM, text = text, color = colorText, textAlign = textAlign)
        iconRight?.let {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(painterResource(id = it), contentDescription = null, tint = colorText.color)
        }
    }
}

@Composable
fun ButtonIcon(
    @DrawableRes icon: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colorIcon: AppColor = AppTheme.buttonColors.primary.colorFor(enabled),
    color: AppColor = AppTheme.buttonColors.primary(enabled),
    shape: Shape = RoundedCornerShape(48.dp)
) {
    ButtonIcon(onClick, modifier, enabled, color, shape) {
        Icon(painter = painterResource(id = icon), contentDescription = null, tint = colorIcon.color)
    }
}

@Composable
fun ButtonIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: AppColor = AppTheme.buttonColors.primary(enabled),
    shape: Shape = RoundedCornerShape(48.dp),
    icon: @Composable () -> Unit,
) {
    Button(onClick = onClick, enabled = enabled, color = color, modifier = modifier, shape = shape) {
        icon()
    }
}

private val ButtonHorizontalPadding = 16.dp
private val ButtonVerticalPadding = 12.dp