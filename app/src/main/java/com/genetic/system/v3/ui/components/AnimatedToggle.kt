package com.genetic.system.v3.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnimatedToggle(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String = ""
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (label.isNotEmpty()) Text(text = label, color = Color.White, fontSize = 14.sp)
        
        Box(
            modifier = Modifier
                .size(48.dp, 24.dp)
                .clip(CircleShape)
                .background(if (checked) Color.Green else Color.Gray)
                .clickable { onCheckedChange(!checked) },
            contentAlignment = if (checked) Alignment.CenterEnd else Alignment.CenterStart
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(2.dp)
            )
        }
    }
}
