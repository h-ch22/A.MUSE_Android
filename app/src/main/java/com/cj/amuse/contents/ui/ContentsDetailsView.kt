package com.cj.amuse.contents.ui

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.MenuBook
import androidx.compose.material.icons.rounded.AccountBalance
import androidx.compose.material.icons.rounded.Book
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Recommend
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Summarize
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cj.amuse.R
import com.cj.amuse.contents.models.ContentsDataModel
import com.cj.amuse.contents.models.ContentsTypeModel
import com.cj.amuse.ui.theme.AMUSETheme
import com.cj.amuse.ui.theme.Typography
import com.cj.amuse.ui.theme.gray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentsDetailsView(data: ContentsDataModel?) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    AMUSETheme {
        Scaffold(topBar = {
            TopAppBar(
                title = { Text(text = data?.title ?: "", style = Typography.titleLarge) }
            )
        }) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp).verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if(data != null){
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
                            error = painterResource(id = R.drawable.ic_appstore),
                            placeholder = painterResource(id = R.drawable.ic_appstore)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(text = data.title, style = Typography.titleMedium)

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = "${data.author} | ${data.publisher}",
                            style = Typography.bodySmall,
                            color = gray
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Rounded.Star,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )

                            Spacer(modifier = Modifier.width(5.dp))

                            Text(
                                text = data.score.toString(),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            when (data.type) {
                                ContentsTypeModel.BOOK -> {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Rounded.MenuBook,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }

                                ContentsTypeModel.MOVIE -> {
                                    Icon(imageVector = Icons.Rounded.Movie, contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary)
                                }

                                ContentsTypeModel.CULTURE -> {
                                    Icon(
                                        imageVector = Icons.Rounded.AccountBalance,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }

                                ContentsTypeModel.ETC -> {
                                    Icon(imageVector = Icons.Rounded.MoreHoriz, contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary)
                                }
                            }

                            Spacer(modifier = Modifier.width(5.dp))

                            Text(text = data.type.getString(), color = MaterialTheme.colorScheme.primary)
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Rounded.Summarize,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )

                            Spacer(modifier = Modifier.width(5.dp))

                            Text(
                                text = stringResource(id = R.string.TXT_SUMMARY),
                                color = MaterialTheme.colorScheme.primary,
                                style = Typography.titleSmall
                            )

                            Spacer(modifier = Modifier.weight(1f))
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = data.summary,
                            style = Typography.bodyMedium,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.align(Alignment.Start)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            ElevatedButton(onClick = { /*TODO*/ }) {
                                Icon(imageVector = Icons.Rounded.ThumbUp, contentDescription = null)

                                Spacer(modifier = Modifier.width(10.dp))

                                Text(text = stringResource(id = R.string.TXT_RECOMMEND))
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Button(onClick = { /*TODO*/ }) {
                                Icon(imageVector = Icons.Rounded.PlayArrow, contentDescription = null)

                                Spacer(modifier = Modifier.width(10.dp))

                                Text(text = stringResource(id = R.string.TXT_PLAY))
                            }
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))

                        Icon(imageVector = Icons.Rounded.Warning, contentDescription = null)

                        Text(text = stringResource(id = R.string.TXT_NO_CONTENTS))

                        Spacer(modifier = Modifier.weight(1f))
                    }

                }
            }
        }
    }
}

@Preview
@Composable
fun ContentsDetailsViewPreview() {
    ContentsDetailsView(
//        data = ContentsDataModel(
//            author = "Author",
//            title = "Title",
//            summary = "Summary",
//            publisher = "Publisher",
//            type = ContentsTypeModel.BOOK,
//            url = Uri.EMPTY,
//            cover = Uri.EMPTY,
//            score = 0
//        )

        data = null
    )
}
