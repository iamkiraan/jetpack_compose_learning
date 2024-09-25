package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
          show()
        }
    }
}



@Composable
fun App() {
    var theme = remember { mutableStateOf(false) }

    MyApplicationTheme(theme.value) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "kiran acharya")
                Button(onClick = {
                    theme.value = !theme.value
                }) {
                    Text(text = "change theme")
                }
            }
        }
    }


@Composable
fun ListComponent() {
    val counter = remember { mutableStateOf(0) }
    var scope = rememberCoroutineScope()

    var text = "counter is running ${counter.value}"
    if(counter.value==10){
        text = "counter stopped!!!"
        Log.d("LaunchEffect","counter stopped")
    }
    Column {
        Text(text = text)
        Button(onClick = {
            scope.launch {
                try{
                    for(i in 1..10){
                        counter.value++
                        delay(1000)
                    }
                }catch(e:Exception){
                    Log.d("LaunchEffect",e.message.toString())

                }
            }
        }) {
            Text(text = "start")

        }
    }

}

@Composable
fun CallUpdate(){
    val counter = remember{ mutableIntStateOf(0) }
    LaunchedEffect(key1 = Unit) {
        delay(2000)
        counter.intValue =10

    }
    Counter(counter.intValue)

}

@Composable
fun Counter(value:Int){
    val state  = rememberUpdatedState(newValue = value)
    LaunchedEffect(key1 = Unit) {
        delay(5000)
        Log.d("kiran",state.value.toString())

    }
    Text(text =state.value.toString())

}


fun a(){Log.d("launchEffect","i am apple A")}

fun b(){Log.d("launchEffect","i am apple B")}

@Composable
fun show(){
    var state =  remember {
        mutableStateOf(::a)
    }
    Button(onClick = {
        state.value = ::b
    }) {
        Text(text = "click to change the state")
    }
    LoadingScreen(state.value)
}

@Composable
fun LoadingScreen(onTimeout:()->Unit){
    val currentOnTimeout by rememberUpdatedState(onTimeout)
    LaunchedEffect(key1 = true) {
        delay(5000)
        currentOnTimeout()

    }
}