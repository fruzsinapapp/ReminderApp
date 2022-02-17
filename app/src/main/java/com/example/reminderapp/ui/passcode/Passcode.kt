package com.example.reminderapp.ui.passcode

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotMutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.reminderapp.Graph
import com.example.reminderapp.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


const val pinSize = 5


@Composable
fun Passcode(
    navController: NavController,
    sharedPreferences: SharedPreferences
) {
    val inputPin = remember { mutableStateListOf<Int>() }
    //val error = remember { mutableStateOf<String>("") }
    //val showSuccess = remember { mutableStateOf(false) }
    val context = Graph.appContext
    //val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                modifier = Modifier,
                painter = painterResource(R.drawable.background1),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Enter your code to unlock",
                    style = typography.h5,
                    modifier = Modifier.padding(16.dp),
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(30.dp))
                Row {
                    (0 until pinSize).forEach {
                        Icon(
                            imageVector = if (inputPin.size > it) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircle,
                            contentDescription = it.toString(),
                            modifier = Modifier
                                .padding(8.dp)
                                .size(30.dp),
                            tint = Color.Black
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    (1..3).forEach {
                        PinKeyItem(
                            onClick = {
                                processPin(sharedPreferences, navController, inputPin, it,context)
                            }
                        ) {
                            Text(
                                text = it.toString(),
                                style = typography.h5
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    (4..6).forEach {
                        PinKeyItem(
                            onClick = {
                                processPin(sharedPreferences, navController, inputPin, it,context)
                            }
                        ) {
                            Text(
                                text = it.toString(),
                                style = typography.h5
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    (7..9).forEach {
                        PinKeyItem(
                            onClick = {
                                processPin(sharedPreferences, navController, inputPin, it, context)
                            }
                        ) {
                            Text(
                                text = it.toString(),
                                style = typography.h5,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Success",
                        modifier = Modifier
                            .size(25.dp)
                            .clickable { }
                    )
                    PinKeyItem(
                        onClick = {
                            processPin(sharedPreferences, navController, inputPin, 0,context)
                        },
                        modifier = Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        )
                    ) {
                        Text(
                            text = "0",
                            style = typography.h5,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "Clear",
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {
                                if (inputPin.isNotEmpty()) {
                                    inputPin.removeLast()
                                }
                            }
                    )
                }
            }
        }
    }

}

fun processPin(
    sharedPreferences: SharedPreferences,
    navController: NavController,
    inputPin: SnapshotStateList<Int>,
    value: Int,
    context: Context
) {
    inputPin.add(value)
    if (inputPin.size == pinSize) {
        checkPin(
            sharedPreferences, navController, inputPin,context
        )
    }
}

private fun checkPin(
    sharedPreferences: SharedPreferences,
    navController: NavController,
    inputPin: SnapshotStateList<Int>,
    context: Context
) {
    if (inputPin.joinToString("") == sharedPreferences.getString("code","")) {
        navController.navigate("home")
    } else {
        inputPin.clear()
                Toast.makeText(
                    context,
                   "Incorrect code, try another one",
                    Toast.LENGTH_SHORT
               ).show()
    }
}


@Composable
fun PinKeyItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier.padding(8.dp),
    shape: Shape = androidx.compose.material.MaterialTheme.shapes.small.copy(
        androidx.compose.foundation.shape.CornerSize(
            percent = 50
        )
    ),
    backgroundColor: Color = MaterialTheme.colors.primaryVariant,
    contentColor: Color = contentColorFor(backgroundColor = backgroundColor),
    //elevation: Dp = 4.dp,
    content: @Composable () -> Unit
) {
    Button(
        modifier = modifier,
        shape = shape,
        onClick = onClick

    ) {
        CompositionLocalProvider(
            LocalContentAlpha provides contentColor.alpha
        ) {
            ProvideTextStyle(
                typography.h2
            ) {
                Box(
                    modifier = Modifier.defaultMinSize(minWidth = 64.dp, minHeight = 64.dp),
                    contentAlignment = Alignment.Center
                ) {
                    content()
                }
            }
        }
    }
}