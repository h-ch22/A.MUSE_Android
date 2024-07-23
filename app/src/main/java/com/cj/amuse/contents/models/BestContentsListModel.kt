package com.cj.amuse.contents.models

import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cj.amuse.R
import com.cj.amuse.contents.helper.ContentsHelper
import com.cj.amuse.ui.theme.AMUSETheme
import com.cj.amuse.ui.theme.Typography
import com.cj.amuse.ui.theme.accent
import com.cj.amuse.ui.theme.gray

@Composable
fun BestContentsListModel(data: ContentsDataModel, rank: Int, context: Context, onClick: () -> Unit) {
    AMUSETheme {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            shape = RoundedCornerShape(30.dp),
            elevation = CardDefaults.cardElevation(5.dp),
            onClick = {
                onClick()
            }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Text(
                    text = rank.toString(),
                    style = Typography.titleLarge,
                    color = if (rank < 4) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = ContentsHelper.getRankSuffix(rank),
                    style = Typography.titleMedium,
                    color = if (rank < 4) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.width(10.dp))

                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(data.url)
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(15.dp)),
                    error = painterResource(id = R.drawable.ic_appstore)
                )

                Spacer(modifier = Modifier.width(20.dp))

                Column {
                    Text(
                        text = "${data.author} | ${data.publisher}",
                        style = Typography.bodySmall,
                        color = gray
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(text = data.title, style = Typography.titleLarge)

                    Spacer(modifier = Modifier.height(5.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Rounded.Star,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = data.score.toString(),
                            style = Typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun BestContentsListModelPreview() {
    BestContentsListModel(
        data = ContentsDataModel(
            "Author",
            "Publisher",
            "Summary",
            "Title",
            ContentsTypeModel.BOOK,
            Uri.EMPTY,
            Uri.EMPTY,
            0
        ), 4, LocalContext.current
    ){

    }
}
