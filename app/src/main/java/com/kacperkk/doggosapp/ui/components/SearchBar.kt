package com.kacperkk.doggosapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onAddClick: () -> Unit,
    placeholderText: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            placeholder = {
                Text(
                    text = placeholderText,
                    fontSize = 14.sp
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .weight(1f)
                .height(56.dp),
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            onClick = onAddClick,
            modifier = Modifier.size(48.dp),
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Dog",
                tint = Color.Black
            )
        }
    }
}
