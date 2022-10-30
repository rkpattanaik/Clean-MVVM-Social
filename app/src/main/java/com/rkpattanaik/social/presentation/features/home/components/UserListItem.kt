package com.rkpattanaik.social.presentation.features.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rkpattanaik.social.domain.entity.UserEntity

@Composable
fun UserListItem(item: UserEntity) {
    Card(
        border = BorderStroke(0.5.dp, Color.LightGray),
        modifier = Modifier.wrapContentSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "${item.firstName} ${item.lastName}",
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = item.email,
                style = MaterialTheme.typography.body1
            )
        }
    }
}