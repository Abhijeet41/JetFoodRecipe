package com.riegersan.composeexperiments

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhi41.jetfoodrecipeapp.R
import com.abhi41.jetfoodrecipeapp.ui.theme.SMALL_PADDING

@Preview(showBackground = true)
@Composable
fun Chip(
    modifier: Modifier = Modifier,
    onSelectionChanged: (String) -> Unit = {},
    name: String = "Chip",
    isSelected: Boolean = true,

    ) {
    Surface(
        modifier = modifier.padding(4.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) Color.Blue else Color.LightGray,

        ) {
        Row(modifier = Modifier
            .toggleable(
                value = isSelected,
                onValueChange = {
                    onSelectionChanged(name)
                }
            )
        ) {
            if (isSelected) Icon(
                modifier = Modifier.padding(
                    start = SMALL_PADDING,
                    bottom = SMALL_PADDING,
                    top = SMALL_PADDING
                ),
                painter = painterResource(id = R.drawable.ic_checkmark),
                tint = Color.White,
                contentDescription = "Check Icon"
            ) else Box() {}
            Text(
                text = name,
                color = Color.White,
                modifier = Modifier.padding(SMALL_PADDING)
            )
        }
    }
}