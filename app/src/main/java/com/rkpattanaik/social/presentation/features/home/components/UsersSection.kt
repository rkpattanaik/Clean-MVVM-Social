package com.rkpattanaik.social.presentation.features.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rkpattanaik.social.domain.entity.UserEntity
import com.rkpattanaik.social.presentation.ui.common.UIState

@Composable
fun UsersSection(
    state: UIState<List<UserEntity>>
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
    ) {
        Text(
            text = "Users",
            style = MaterialTheme.typography.h5
        )

        Spacer(modifier = Modifier.size(8.dp))

        if(state.isLoading) {
            CircularProgressIndicator()
        } else {
            if(state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )
            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                state.data?.let {
                    items(it) { item: UserEntity ->
                        UserListItem(item)
                    }
                }
            }
        }
    }
}