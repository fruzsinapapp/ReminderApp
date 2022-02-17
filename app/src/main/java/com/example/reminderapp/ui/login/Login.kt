package com.example.reminderapp.ui.login

import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.reminderapp.Graph
import com.example.reminderapp.R
import com.google.accompanist.insets.systemBarsPadding


@Composable
fun Login(
    navController: NavController,
    sharedPreferences: SharedPreferences
){

    Surface(modifier = Modifier.fillMaxSize(), color = Color.LightGray, ) {
        val username= rememberSaveable{mutableStateOf("")}
        val password= rememberSaveable{mutableStateOf("")}
        val context = Graph.appContext
        //val context = LocalContext.current

        //val sharedPreferences :SharedPreferences

        Image(
            modifier = Modifier,
            painter = painterResource(R.drawable.background1),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){

            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = username.value,
                placeholder ={"xyz"},
                onValueChange = {username.value = it},
                label = { Text("Username")},
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = password.value,
                onValueChange = {password.value = it},
                label = { Text("Password")},
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    //navController.navigate("home")

                    if(username.value == sharedPreferences.getString("username","") &&
                        password.value == sharedPreferences.getString("password","")){
                        navController.navigate("home")
                    }
                    else {
                        Toast.makeText(
                            context,
                            "Please enter a username and a password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } ,
                enabled = true,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small


            ){
                Text(text = "Login")
            }
        }
    }
}

//@Preview
//@Composable
//fun Preview() {
//    Login(navController = NavController())
//}