package com.example.ecommerceapp.ui.components
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryChip(
    category: String,
    selected: Boolean,
    onClick: () -> Unit,
    onClear: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    FilterChip(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        label = { Text(category) },
        trailingIcon = if (selected && onClear != null) {
            {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Clear filter",
                    modifier = Modifier.size(18.dp)
                )
            }
        } else null
    )
}