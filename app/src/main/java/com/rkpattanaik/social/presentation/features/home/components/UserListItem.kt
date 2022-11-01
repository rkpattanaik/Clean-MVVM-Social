package com.rkpattanaik.social.presentation.features.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rkpattanaik.social.domain.entity.UserEntity
import com.rkpattanaik.social.presentation.ui.theme.SocialTheme

@Composable
fun UserListItem(item: UserEntity) {
    Card(
        border = BorderStroke(0.5.dp, Color.LightGray),
        modifier = Modifier.wrapContentSize()
    ) {
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .padding(12.dp)
        ) {
            Text(
                text = "${item.firstName} ${item.lastName}",
                style = MaterialTheme.typography.body1
            )

            Spacer(modifier = Modifier.size(4.dp))

            Text(
                text = item.email,
                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Light),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserListItemPreview() {
    SocialTheme {
        UserListItem(
            UserEntity(
            firstName = "Rajesh",
            lastName = "Pattanaik",
            email = "rkpattanaik21@gmail.com"
        )
        )
    }
}