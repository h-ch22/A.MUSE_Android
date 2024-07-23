package com.cj.amuse.userManagement.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.AlternateEmail
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cj.amuse.R
import com.cj.amuse.ui.theme.AMUSETheme
import com.cj.amuse.ui.theme.Typography
import com.cj.amuse.ui.theme.gray
import com.cj.amuse.userManagement.helper.UserManagement
import com.cj.amuse.userManagement.models.UserManagementAlertTypeModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordView(navHostController: NavHostController) {
    var email by remember {
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

    val helper = UserManagement()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            LargeTopAppBar(title = { Text(text = stringResource(id = R.string.TXT_RESET_PASSWORD)) },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = null
                        )
                    }
                })
        }
    ) {
        AMUSETheme {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(text = stringResource(id = R.string.TXT_RESET_PASSWORD_DESCRIPTION), style = Typography.bodySmall, color = gray)

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
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    if(showProgress){
                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                    } else {
                        Button(onClick = {
                            if(email.isEmpty()){
                                alertType = UserManagementAlertTypeModel.EMPTY_FIELD
                                showAlert = true
                            } else if(!email.contains("@")){
                                alertType = UserManagementAlertTypeModel.INVALID_EMAIL_TYPE
                                showAlert = true
                            } else {
                                showProgress = true

                                helper.sendPasswordResetMail(email){
                                    showProgress = false

                                    alertType = if(it) UserManagementAlertTypeModel.SENT_PASSWORD_RESET_MAIL else UserManagementAlertTypeModel.ACCOUNT_DOES_NOT_EXISTS
                                    showAlert = true
                                }
                            }
                        }, modifier = Modifier.align(Alignment.End)) {
                            Text(text = stringResource(id = R.string.TXT_RESET_PASSWORD))
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

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
                                    imageVector = if(alertType == UserManagementAlertTypeModel.SENT_PASSWORD_RESET_MAIL) Icons.Rounded.CheckCircle else Icons.Rounded.Warning,
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

@Preview
@Composable
fun ResetPasswordViewPreview() {
    ResetPasswordView(navHostController = rememberNavController())
}
