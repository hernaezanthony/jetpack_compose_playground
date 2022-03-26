package com.ahernaez.jetpackcomposeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahernaez.jetpackcomposeplayground.models.Todo
import com.ahernaez.jetpackcomposeplayground.ui.theme.JetpackComposePlaygroundTheme

class MainActivity : ComponentActivity() {

    val todoList =  mutableStateListOf<Todo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpData()
        setContent {
            JetpackComposePlaygroundTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val todoList = arrayListOf(
                        Todo("Learn Jetpack Compose", false),
                        Todo("Learn Swift", true),
                        Todo("Learn dependency injection", false)
                    )

                    TodoList(todoItems = todoList)
                }
            }
        }
    }
}

    private fun setUpData(){

        todoList.addAll(
            arrayListOf(
                Todo("Learn Jetpack Compose", false),
                Todo("Learn Swift", true),
                Todo("Learn dependency injection", false)
            )
        )
    }

@Composable
fun TodoList(todoItems : MutableList<Todo>){
    
    LazyColumn{
        items(todoItems){ todo ->
            TodoCard(todo = todo.name, isChecked = todo.isDone)
        }
    }
    
}

@Composable
fun TodoCard(todo: String, isChecked: Boolean){

    Row(modifier = Modifier.padding(all = 8.dp)){
        val checkedState = remember { mutableStateOf(isChecked) }
        Checkbox(checked = checkedState.value, onCheckedChange = {checkedState.value = it})

        Spacer(modifier = Modifier.width(8.dp))

        Text(text = todo)
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackComposePlaygroundTheme {
        TodoCard(todo = "Learn Jetpack Compose", isChecked = false)
    }
}