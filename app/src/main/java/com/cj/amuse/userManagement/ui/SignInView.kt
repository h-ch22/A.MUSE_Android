package com.cj.amuse.userManagement.ui

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AlternateEmail
import androidx.compose.material.icons.rounded.Key
import androidx.compose.material.icons.rounded.Password
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cj.amuse.R
import com.cj.amuse.frameworks.helper.AES256Util
import com.cj.amuse.frameworks.helper.DataStoreUtil
import com.cj.amuse.frameworks.ui.MainActivity
import com.cj.amuse.frameworks.ui.StartActivity
import com.cj.amuse.ui.theme.AMUSETheme
import com.cj.amuse.ui.theme.Typography
import com.cj.amuse.ui.theme.gray
import com.cj.amuse.userManagement.helper.UserManagement
import com.cj.amuse.userManagement.models.AuthInfoModel
import com.cj.amuse.userManagement.models.UserManagementAlertTypeModel
import com.cj.amuse.userManagement.models.UserManagementNavigationRoute
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInView() {
    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var showProgress by remember {
        mutableStateOf(false)
    }

    var alertType by remember {
        mutableStateOf<UserManagementAlertTypeModel?>(null)
    }

    var showAlert by remember {
        mutableStateOf(false)
    }

    var isLoadSuccess by remember {
        mutableStateOf(false)
    }

    val navController = rememberNavController()
    val context = LocalContext.current
    val helper = UserManagement()
    val dataStoreUtil = DataStoreUtil(context)
    val authInfo = dataStoreUtil.getFromDataStore().collectAsState(initial = AuthInfoModel(email = "", password = ""))
    val coroutineScope = rememberCoroutineScope()

    NavHost(navController = navController, startDestination = UserManagementNavigationRoute.SPLASH.getString()) {
        composable(UserManagementNavigationRoute.SIGN_UP.getString()){
            SignUpView(navHostController = navController)
        }

        composable(UserManagementNavigationRoute.RESET_PASSWORD.getString()){
            ResetPasswordView(navHostController = navController)
        }

        composable(UserManagementNavigationRoute.SPLASH.getString()){
            LaunchedEffect(key1 = true) {
                isLoadSuccess = true
            }

            Column(modifier = Modifier.fillMaxSize().padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                if(isLoadSuccess){
                    navController.navigate(UserManagementNavigationRoute.SIGN_IN.getString()){
                        popUpTo(UserManagementNavigationRoute.SPLASH.getString()){
                            inclusive = true
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Image(
                    painter = painterResource(id = R.drawable.ic_appstore),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = stringResource(id = R.string.app_name),
                    style = Typography.titleLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.weight(1f))

                CircularProgressIndicator()
            }
        }

        composable(UserManagementNavigationRoute.SIGN_IN.getString()){
            LaunchedEffect(true) {
                if(authInfo.value.email.isNotEmpty() && authInfo.value.password.isNotEmpty()){
                    showProgress = true

                    helper.signIn(AES256Util.decrypt(authInfo.value.email), AES256Util.decrypt(authInfo.value.password)){
                        showProgress = false

                        if(it){
                            context.startActivity(Intent(context as StartActivity, MainActivity::class.java))
                            context.finish()
                        } else {
                            alertType = UserManagementAlertTypeModel.SIGN_IN_ERROR
                            showAlert = true
                        }
                    }
                }
            }

            Scaffold(
                topBar = {
                    LargeTopAppBar(title = { Text(text = stringResource(id = R.string.TXT_SIGN_IN)) })
                }
            ) {
                AMUSETheme {
                    Surface(modifier = Modifier
                        .fillMaxSize()
                        .padding(it)) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_appstore),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(RoundedCornerShape(15.dp))
                                    .align(Alignment.CenterHorizontally)
                            )

                            Spacer(modifier = Modifier.height(15.dp))

                            Text(
                                text = stringResource(id = R.string.app_name),
                                style = Typography.titleLarge,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Rounded.AlternateEmail, contentDescription = null)

                                Spacer(modifier = Modifier.width(10.dp))

                                OutlinedTextField(
                                    value = email,
                                    onValueChange = { email = it },
                                    modifier = Modifier.fillMaxWidth(),
                                    label = {
                                        Text(
                                            text = stringResource(id = R.string.TXT_EMAIL)
                                        )
                                    },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                    singleLine = true
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Rounded.Password, contentDescription = null)

                                Spacer(modifier = Modifier.width(10.dp))

                                OutlinedTextField(
                                    value = password,
                                    onValueChange = { password = it },
                                    modifier = Modifier.fillMaxWidth(),
                                    label = {
                                        Text(
                                            text = stringResource(id = R.string.TXT_PASSWORD)
                                        )
                                    },
                                    visualTransformation = PasswordVisualTransformation(),
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            if(showProgress){
                                LinearProgressIndicator(
                                    modifier = Modifier.fillMaxWidth()
                                )
                            } else {
                                Button(onClick = {
                                    if(email == "" || password == ""){
                                        alertType = UserManagementAlertTypeModel.EMPTY_FIELD
                                        showAlert = true
                                    } else if(!email.contains("@")){
                                        alertType = UserManagementAlertTypeModel.INVALID_EMAIL_TYPE
                                        showAlert = true
                                    } else {
                                        showProgress = true

                                        helper.signIn(email, password) {
                                            showProgress = false

                                            if(!it){
                                                alertType = UserManagementAlertTypeModel.SIGN_IN_ERROR
                                                showAlert = true
                                            } else {
                                                coroutineScope.launch {
                                                    dataStoreUtil.saveToDataStore(AES256Util.encrypt(email), AES256Util.encrypt(password))
                                                }

                                                context.startActivity(Intent(context as StartActivity, MainActivity::class.java))
                                                context.finish()
                                            }
                                        }
                                    }
                                },
                                    modifier = Modifier.fillMaxWidth()) {
                                    Text(text = stringResource(id = R.string.TXT_SIGN_IN))
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                TextButton(onClick = {
                                    navController.navigate(UserManagementNavigationRoute.RESET_PASSWORD.getString()){
                                        popUpTo(UserManagementNavigationRoute.SIGN_IN.getString()){
                                            inclusive = false
                                        }
                                    }
                                }) {
                                    Text(text = stringResource(id = R.string.TXT_RESET_PASSWORD))
                                }

                                Spacer(modifier = Modifier.weight(1f))

                                TextButton(onClick = {
                                    navController.navigate(UserManagementNavigationRoute.SIGN_UP.getString()){
                                        popUpTo(UserManagementNavigationRoute.SIGN_IN.getString()){
                                            inclusive = false
                                        }
                                    }
                                }) {
                                    Text(text = stringResource(id = R.string.TXT_SIGN_UP))
                                }
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            Text(text = stringResource(id = R.string.TXT_COPYRIGHT_INFORMATION), style = Typography.bodySmall, color = gray, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())

                            if(showAlert && alertType != null){
                                AlertDialog(
                                    onDismissRequest = { showAlert = false },
                                    confirmButton = {
                                        TextButton(onClick = { showAlert = false }) {
                                            Text(text = stringResource(id = R.string.TXT_OK))
                                        }
                                    },
                                    title = {
                                        Text(text = alertType!!.getTitle(context))
                                    },
                                    text = {
                                        Text(text = alertType!!.getMessage(context))
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Rounded.Warning,
                                            contentDescription = null
                                        )
                                    }
                                )
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
fun SignInViewPreview() {
    SignInView()
}
