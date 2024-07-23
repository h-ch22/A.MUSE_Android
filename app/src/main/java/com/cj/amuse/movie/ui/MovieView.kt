package com.cj.amuse.movie.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cj.amuse.R
import com.cj.amuse.contents.helper.ContentsHelper
import com.cj.amuse.contents.models.BestContentsListModel
import com.cj.amuse.contents.models.ContentsDataModel
import com.cj.amuse.contents.models.ContentsTypeModel
import com.cj.amuse.contents.ui.ContentsDetailsView
import com.cj.amuse.ui.theme.AMUSETheme
import com.cj.amuse.ui.theme.Typography
import com.cj.amuse.ui.theme.gray
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieView(){
    val helper = ContentsHelper()
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val bestContents = remember {
        mutableStateListOf<ContentsDataModel>()
    }

    val coroutineScope = rememberCoroutineScope()

    val bottomSheetState = androidx.compose.material.rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden, skipHalfExpanded = true
    )

    val hideBottomSheet: () -> Unit = {
        coroutineScope.launch {
            bottomSheetState.hide()
        }
    }

    var selectedContent by remember {
        mutableStateOf<ContentsDataModel?>(null)
    }

    val categories = stringArrayResource(id = R.array.CATEGORIES_MOVIE)

    var showProgress by remember {
        mutableStateOf(true)
    }

    AMUSETheme {
        ModalBottomSheetLayout(sheetContent = {
            Column {
                Surface(modifier = Modifier.wrapContentSize(), color = MaterialTheme.colorScheme.surface) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Spacer(modifier = Modifier.weight(1f))

                        IconButton(onClick = { hideBottomSheet() }) {
                            Icon(imageVector = Icons.Rounded.Cancel, contentDescription = null)
                        }
                    }
                }


                ContentsDetailsView(data = selectedContent)
            }
        }, sheetState = bottomSheetState, sheetShape = RoundedCornerShape(15.dp), sheetElevation = 5.dp){
            Surface(modifier = Modifier.fillMaxSize()) {
                LaunchedEffect(key1 = true) {
                    helper.getContents(ContentsTypeModel.MOVIE){
                        for(i in 0 until if(ContentsHelper.movieContents.size > 3) 3 else ContentsHelper.movieContents.size){
                            bestContents.add(
                                ContentsHelper.movieContents[i]
                            )
                        }

                        showProgress = false
                    }
                }

                Column(modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally) {
                    if(showProgress){
                        Spacer(modifier = Modifier.weight(1f))

                        CircularProgressIndicator()

                        Spacer(modifier = Modifier.weight(1f))
                    } else if(ContentsHelper.movieContents.isEmpty()) {
                        Spacer(modifier = Modifier.weight(1f))

                        Icon(imageVector = Icons.Rounded.Warning, contentDescription = null)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = stringResource(id = R.string.TXT_NO_CONTENTS), style = Typography.bodyMedium, color = gray)

                        Spacer(modifier = Modifier.weight(1f))
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Rounded.Star, contentDescription = null, tint = MaterialTheme.colorScheme.primary)

                            Spacer(modifier = Modifier.width(5.dp))

                            Text(text = stringResource(id = R.string.TXT_TOP_3), style = Typography.titleMedium, color = MaterialTheme.colorScheme.primary)

                            Spacer(modifier = Modifier.weight(1f))
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        for((i, data) in bestContents.withIndex()){
                            BestContentsListModel(data = data, rank = i + 1, context = context){
                                selectedContent = data

                                coroutineScope.launch {
                                    bottomSheetState.show()
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Rounded.Category, contentDescription = null, tint = MaterialTheme.colorScheme.primary)

                            Spacer(modifier = Modifier.width(5.dp))

                            Text(text = stringResource(id = R.string.TXT_CATEGORIES), style = Typography.titleMedium, color = MaterialTheme.colorScheme.primary)

                            Spacer(modifier = Modifier.weight(1f))
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 150.dp), contentPadding = PaddingValues(5.dp), modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp)) {
                            items(categories){ item ->
                                TextButton(onClick = { /*TODO*/ }, modifier = Modifier) {
                                    Text(item)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MovieViewPreview(){
    MovieView()
}
