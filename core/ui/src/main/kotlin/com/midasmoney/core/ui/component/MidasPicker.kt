package com.midasmoney.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.midasmoney.core.domain.model.IconModel
import com.midasmoney.core.domain.model.IconType
import com.midasmoney.core.domain.model.converter.IconConverter
import com.midasmoney.core.resource.R
import com.midasmoney.core.ui.theme.MidasColors

@Composable
fun ColorPickerGrid(
    selectedColor: Color?,
    onColorSelected: (Color) -> Unit
) {
    val colors = MidasColors.AllColors
    MidasCard(modifier = Modifier.fillMaxWidth()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(colors.size) { index ->
                val color = colors[index]
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(color)
                        .border(
                            width = if (selectedColor == color) 3.dp else 0.dp,
                            color = MaterialTheme.colorScheme.onSurface,
                            shape = CircleShape
                        )
                        .clickable { onColorSelected(color) },
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedColor == color) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = stringResource(R.string.selected),
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun IconPickerGrid(
    selectedIcon: IconType?,
    selectedColor: Color?,
    onIconSelected: (IconType?) -> Unit
) {
    val icons = IconType.entries
    MidasCard(modifier = Modifier.fillMaxWidth()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(icons.size) { index ->
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (icons[index] == selectedIcon) {
                                selectedColor?.copy(alpha = 0.2f)
                                    ?: MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f)
                            } else {
                                MaterialTheme.colorScheme.surface
                            }
                        )
                        .border(
                            width = if (icons[index] == selectedIcon) 3.dp else 0.dp,
                            color = if (icons[index] == selectedIcon)
                                selectedColor ?: MaterialTheme.colorScheme.secondaryContainer
                            else
                                MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { onIconSelected(icons[index]) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = IconConverter.getImageVector(IconModel(icons[index])),
                        contentDescription = icons[index].name,
                        modifier = Modifier.size(32.dp),
                        tint = if (icons[index] == selectedIcon)
                            selectedColor ?: MaterialTheme.colorScheme.secondaryContainer
                        else
                            MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}
