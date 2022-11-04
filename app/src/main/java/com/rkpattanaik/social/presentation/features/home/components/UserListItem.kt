package com.rkpattanaik.social.presentation.features.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.rkpattanaik.social.R
import com.rkpattanaik.social.domain.entity.UserEntity
import com.rkpattanaik.social.presentation.ui.theme.SocialTheme

@Composable
fun UserListItem(user: UserEntity) {
    Box(
        modifier = Modifier.wrapContentSize()
    ) {
        Column(
            modifier = Modifier
                .wrapContentWidth()
        ) {
            AsyncImage(
                model = user.avatar,
                placeholder = painterResource(id = R.drawable.avatar_placeholder),
                contentDescription = "User Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .align(CenterHorizontally)
            )

            Spacer(modifier = Modifier.size(4.dp))

            Text(
                text = user.firstName,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.align(CenterHorizontally)
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