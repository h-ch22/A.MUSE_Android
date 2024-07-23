package com.cj.amuse.home.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cj.amuse.R
import com.cj.amuse.home.helper.BannersHelper
import com.cj.amuse.home.models.BannerDataModel
import com.cj.amuse.home.models.BannerListModel
import com.cj.amuse.ui.theme.AMUSETheme
import com.cj.amuse.ui.theme.Typography
import com.cj.amuse.ui.theme.gray

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeView(){
    val bannerHelper = BannersHelper()
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    var showBannerProgress by remember {
        mutableStateOf(true)
    }

    AMUSETheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier
                .verticalScroll(scrollState)
                .padding(20.dp)) {
                LaunchedEffect(key1 = true) {
                    bannerHelper.getBanners {
                        showBannerProgress = false
                    }
                }

                if(showBannerProgress){
                    CircularProgressIndicator()
                } else {
                    HorizontalPager(
                        state = rememberPagerState {
                            BannersHelper.bannersList.size
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp), verticalAlignment = Alignment.CenterVertically) {
                        BannerListModel(data = BannersHelper.bannersList[it], context = context)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Rounded.PlayArrow, contentDescription = null, tint = MaterialTheme.colorScheme.primary)

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(text = stringResource(id = R.string.TXT_CONTINUE_PLAY), style = Typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = stringResource(id = R.string.TXT_NO_CONTENT_IN_PLAY), style = Typography.bodySmall, color = gray)
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeViewPreview(){
    HomeView()
}
