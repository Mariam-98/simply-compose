package com.example.simplycomposehomework

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen() {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val textFieldModifier: Modifier
    val loginButtonModifier: Modifier
    val headerImageModifier: Modifier

    if (context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        textFieldModifier = Modifier
            .padding(horizontal = 24.dp)
        loginButtonModifier = Modifier
            .padding(horizontal = 48.dp)
        headerImageModifier = Modifier
            .padding(top = 80.dp)
    } else {
        textFieldModifier = Modifier
            .padding(horizontal = 48.dp)
        loginButtonModifier = Modifier
            .padding(horizontal = 72.dp)
        headerImageModifier = Modifier
    }

    Column(
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_login_header),
            contentDescription = null,
            modifier = headerImageModifier.size(200.dp)
        )
        Text(
            text = stringResource(id = R.string.login_header),
            fontSize = 40.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        EmailTextField(
            value = email,
            onValueChange = { email = it },
            keyboardController = keyboardController,
            focusManager = focusManager,
            modifier = textFieldModifier
        )
        Spacer(modifier = Modifier.height(10.dp))
        PasswordTextField(
            value = password,
            onValueChange = { password = it },
            keyboardController = keyboardController,
            focusManager = focusManager,
            modifier = textFieldModifier
        )
        Spacer(modifier = Modifier.height(24.dp))
        LoginButton(loginButtonModifier) {
            focusManager.clearFocus()
            showToast(context, "Login clicked!")
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row {
            Image(
                painter = painterResource(id = R.drawable.ic_login_fb),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_login_google),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_login_ig),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
            )
        }
        Spacer(modifier = Modifier.height(42.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun EmailTextField(
    value: String,
    onValueChange: (String) -> Unit,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager,
    modifier: Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text(text = stringResource(id = R.string.email)) },
        modifier = modifier
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
            focusManager.clearFocus()
        })
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager,
    modifier: Modifier
) {
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text(text = stringResource(id = R.string.password)) },
        modifier = modifier
            .fillMaxWidth(),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
            focusManager.clearFocus()
        }),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(
                    imageVector = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
fun LoginButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.login_button),
            fontSize = 16.sp
        )
    }
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Preview
@Composable
fun DefaultPreview() {
    LoginScreen()
}