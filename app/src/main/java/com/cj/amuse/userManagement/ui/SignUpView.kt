package com.cj.amuse.userManagement.ui

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.AlternateEmail
import androidx.compose.material.icons.rounded.Password
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cj.amuse.R
import com.cj.amuse.frameworks.helper.DateUtils
import com.cj.amuse.frameworks.models.PastSelectableDates
import com.cj.amuse.frameworks.ui.MainActivity
import com.cj.amuse.frameworks.ui.StartActivity
import com.cj.amuse.ui.theme.AMUSETheme
import com.cj.amuse.userManagement.helper.UserManagement
import com.cj.amuse.userManagement.models.UserManagementAlertTypeModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpView(navHostController: NavHostController){
    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var passwordVerification by remember {
        mutableStateOf("")
    }

    var name by remember {
        mutableStateOf("")
    }

    var nickName by remember {
        mutableStateOf("")
    }

    var phoneNumber by remember {
        mutableStateOf("")
    }

    var birthday by remember {
        mutableStateOf("")
    }

    var isAcceptEULA by remember {
        mutableStateOf(false)
    }

    var isAcceptPrivacyLicense by remember {
        mutableStateOf(false)
    }

    var showAlert by remember {
        mutableStateOf(false)
    }

    var alertType by remember {
        mutableStateOf<UserManagementAlertTypeModel?>(null)
    }

    var showProgress by remember {
        mutableStateOf(false)
    }

    var showDatePicker by remember {
        mutableStateOf(false)
    }

    val helper = UserManagement()
    val scrollState = rememberScrollState()
    val datePickerState = rememberDatePickerState(
        selectableDates = PastSelectableDates
    )
    val context = LocalContext.current

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.TXT_SIGN_UP))
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        AMUSETheme {
            Surface(modifier = Modifier
                .fillMaxWidth()
                .padding(it)) {

                Column(modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(scrollState)) {
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

                        Column {
                            OutlinedTextField(
                                value = password,
                                onValueChange = { password = it },
                                modifier = Modifier.fillMaxWidth(),
                                label = {
                                    Text(
                                        text = stringResource(id = R.string.TXT_PASSWORD)
                                    )
                                },
                                singleLine = true,
                                visualTransformation = PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            OutlinedTextField(
                                value = passwordVerification,
                                onValueChange = { passwordVerification = it },
                                modifier = Modifier.fillMaxWidth(),
                                label = {
                                    Text(
                                        text = stringResource(id = R.string.TXT_PASSWORD_VERIFICATION)
                                    )
                                },
                                singleLine = true,
                                visualTransformation = PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Rounded.Person, contentDescription = null)

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                modifier = Modifier.fillMaxWidth(),
                                label = {
                                    Text(
                                        text = stringResource(id = R.string.TXT_NAME)
                                    )
                                },
                                singleLine = true
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            OutlinedTextField(
                                value = nickName,
                                onValueChange = { nickName = it },
                                modifier = Modifier.fillMaxWidth(),
                                label = {
                                    Text(
                                        text = stringResource(id = R.string.TXT_NICK_NAME)
                                    )
                                },
                                singleLine = true
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Rounded.Phone, contentDescription = null)

                        Spacer(modifier = Modifier.width(10.dp))

                        OutlinedTextField(
                            value = phoneNumber,
                            onValueChange = { phoneNumber = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                Text(
                                    text = stringResource(id = R.string.TXT_PHONE)
                                )
                            },
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedButton(onClick = {
                        showDatePicker = true
                    }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        if(birthday.isEmpty()){
                            Text(text = stringResource(id = R.string.TXT_BIRTHDAY_NOT_SELECTED))
                        } else {
                            Text(text = birthday)
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = isAcceptEULA, onCheckedChange = { isAcceptEULA = it })

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(text = stringResource(id = R.string.TXT_ACCEPT_EULA))

                        Spacer(modifier = Modifier.weight(1f))

                        TextButton(onClick = { /*TODO*/ }) {
                            Text(text = stringResource(id = R.string.TXT_READ))
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = isAcceptPrivacyLicense, onCheckedChange = { isAcceptPrivacyLicense = it })

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(text = stringResource(id = R.string.TXT_ACCEPT_PRIVACY_LICENSE))

                        Spacer(modifier = Modifier.weight(1f))

                        TextButton(onClick = { /*TODO*/ }) {
                            Text(text = stringResource(id = R.string.TXT_READ))
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    if(showProgress){
                        CircularProgressIndicator()
                    } else {
                        Button(onClick = {
                            if(email.isEmpty() || password.isEmpty() || passwordVerification.isEmpty() || name.isEmpty() || nickName.isEmpty() || phoneNumber.isEmpty() || birthday.isEmpty()){
                                alertType = UserManagementAlertTypeModel.EMPTY_FIELD
                                showAlert = true
                            } else if(!email.contains("@")){
                                alertType = UserManagementAlertTypeModel.INVALID_EMAIL_TYPE
                                showAlert = true
                            } else if(password.length < 8) {
                                alertType = UserManagementAlertTypeModel.WEAK_PASSWORD
                                showAlert = true
                            } else if(password != passwordVerification){
                                alertType = UserManagementAlertTypeModel.PASSWORD_MISMATCH
                                showAlert = true
                            } else if(!isAcceptEULA || !isAcceptPrivacyLicense){
                                alertType = UserManagementAlertTypeModel.LICENSE_NOT_ACCEPTED
                                showAlert = true
                            } else {
                                showProgress = true

                                helper.signUp(email = email, password = password, name = name, nickName = nickName, phoneNumber = phoneNumber, birthday = birthday){
                                    showProgress = false

                                    if(it){
                                        context.startActivity(Intent(context as StartActivity, MainActivity::class.java))
                                        context.finish()
                                    } else {
                                        alertType = UserManagementAlertTypeModel.SIGN_UP_ERROR
                                        showAlert = true
                                    }
                                }
                            }
                        }, modifier = Modifier.align(Alignment.End)) {
                            Text(text = stringResource(id = R.string.TXT_SIGN_UP))
                        }
                    }

                    if(showDatePicker){
                        DatePickerDialog(
                            onDismissRequest = { showDatePicker = false },
                            confirmButton = {
                                TextButton(onClick = {
                                    datePickerState.selectedDateMillis?.let {
                                        birthday = DateUtils().dateToString(
                                            DateUtils().convertMillisToLocalDate(it)
                                        )
                                    }

                                    showDatePicker = false
                                }) {
                                    Text(text = stringResource(id = R.string.TXT_OK))
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showDatePicker = false }) {
                                    Text(text = stringResource(id = R.string.TXT_CANCEL))
                                }
                            }) {
                            DatePicker(state = datePickerState)
                        }
                    }

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

@Preview
@Composable
fun SignUpViewPreview(){
    SignUpView(navHostController = rememberNavController())
}
