package com.example.myapplication

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Derived()
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
        if (counter.value == 10) {
            text = "counter stopped!!!"
            Log.d("LaunchEffect", "counter stopped")
        }
        Column {
            Text(text = text)
            Button(onClick = {
                scope.launch {
                    try {
                        for (i in 1..10) {
                            counter.value++
                            delay(1000)
                        }
                    } catch (e: Exception) {
                        Log.d("LaunchEffect", e.message.toString())

                    }
                }
            }) {
                Text(text = "start")

            }
        }

    }

    @Composable
    fun CallUpdate() {
        val counter = remember { mutableIntStateOf(0) }
        LaunchedEffect(key1 = Unit) {
            delay(2000)
            counter.intValue = 10

        }
        Counter(counter.intValue)

    }

    @Composable
    fun Counter(value: Int) {
        val state = rememberUpdatedState(newValue = value)
        LaunchedEffect(key1 = Unit) {
            delay(5000)
            Log.d("kiran", state.value.toString())

        }
        Text(text = state.value.toString())

    }


    fun a() {
        Log.d("launchEffect", "i am apple A")
    }

    fun b() {
        Log.d("launchEffect", "i am apple B")
    }

    @Composable
    fun show() {
        var state = remember {
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
    fun LoadingScreen(onTimeout: () -> Unit) {
        val currentOnTimeout by rememberUpdatedState(onTimeout)
        LaunchedEffect(key1 = true) {
            delay(5000)
            currentOnTimeout()

        }
    }


    @Composable
    fun Disposableeffect() {
        var state = remember { mutableStateOf(false) }
        DisposableEffect(key1 = state.value) {
            Log.d("launchEffect", "started")
            onDispose {
                Log.d("launchEffect", "cleaning up side")
            }

        }
        Button(onClick = {
            state.value = !state.value
        }) {
            Text(text = "change state")

        }

    }


    @Composable
    fun mediaPlayer() {
        val context = LocalContext.current
        DisposableEffect(Unit) {
            val mediaPLayer = MediaPlayer.create(context, R.raw.glass)
            mediaPLayer.start()

            onDispose {
                mediaPLayer.stop()
                mediaPLayer.release()

            }

        }

    }


    @Composable
    fun KeyboardComposable() {
        val view = LocalView.current
        DisposableEffect(key1 = Unit) {
            val listener = ViewTreeObserver.OnGlobalLayoutListener {
                val insects = ViewCompat.getRootWindowInsets(view)
                val isKeyboardVisible = insects?.isVisible(WindowInsetsCompat.Type.ime())
                Log.d("kiran", isKeyboardVisible.toString())
            }
            view.viewTreeObserver.addOnGlobalLayoutListener(listener)

            onDispose {
                view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
            }
        }

    }


    @Composable
    fun Counter() {
        val state = produceState(initialValue = 0) {
            for (i in 1..10) {
                delay(1000)
                value++
            }

        }
        Text(text = state.value.toString(),
            modifier = Modifier
                .padding(20.dp,20.dp,20.dp,20.dp))

    }
}


@Composable
fun loader(){
    val degree = produceState(initialValue =0) {
        while(true){
            delay(16)
            value = (value+10)%360
        }
        
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize(1f),
        content ={
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier=Modifier.padding(20.dp)
            ){
                Image(imageVector = Icons.Default.Refresh, contentDescription = "",
                    modifier = Modifier
                        .size(60.dp)
                        .rotate(degree.value.toFloat()))
            }
            Text(text = "loading",
                modifier = Modifier.padding(40.dp))
        }
    )
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun Derived(){
    val tableOf = remember { mutableStateOf(5) }
    val index = produceState(initialValue = 1 ) {
        repeat(10){
            delay(1000)
            value+=1
        }

    }
    val message = derivedStateOf {
        "${tableOf.value}*${index.value} = ${tableOf.value * index.value}"
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(1f)
    ){
        Text(text = message.value)

    }
}

