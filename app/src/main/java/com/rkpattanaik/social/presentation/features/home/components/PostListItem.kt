package com.rkpattanaik.social.presentation.features.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rkpattanaik.social.domain.entity.PostEntity
import com.rkpattanaik.social.presentation.ui.theme.SocialTheme

@Composable
fun PostListItem(item: PostEntity) {
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
                text = item.title,
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = item.body,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostListItemPreview() {
    SocialTheme {
        PostListItem(PostEntity())
    }
}