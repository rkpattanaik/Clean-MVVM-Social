package com.rkpattanaik.social.presentation.features.home.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rkpattanaik.social.domain.entity.PostEntity
import com.rkpattanaik.social.presentation.ui.common.UIState

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PostsSection(
    state: UIState<List<PostEntity>>
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Posts",
            style = MaterialTheme.typography.h5
        )

        if(state.isLoading) {
            CircularProgressIndicator()
        } else {
            if(state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.size(2.dp))
                }

                state.data?.let {
                    items(it) { item: PostEntity ->
                        PostListItem(item)
                    }
                }

                item {
                    Spacer(modifier = Modifier.size(2.dp))
                }
            }
        }
    }
}