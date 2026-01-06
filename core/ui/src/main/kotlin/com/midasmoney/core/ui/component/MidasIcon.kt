package com.midasmoney.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.midasmoney.core.domain.model.IconModel
import com.midasmoney.core.domain.model.IconType
import com.midasmoney.core.domain.model.converter.IconConverter
import com.midasmoney.core.resource.R

@Composable
fun MidasIcon(
    iconType: IconType,
    color: Color?
) {
    val defaultColor = MaterialTheme.colorScheme.secondaryContainer
    Icon(
        imageVector = IconConverter.getImageVector(IconModel(iconType)),
        contentDescription = stringResource(R.string.description_selected_icon),
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background((color ?: defaultColor).copy(alpha = 0.2f))
            .padding(6.dp),
        tint = color ?: defaultColor
    )
}
