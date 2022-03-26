package com.ahernaez.jetpackcomposeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahernaez.jetpackcomposeplayground.models.Todo
import com.ahernaez.jetpackcomposeplayground.ui.theme.JetpackComposePlaygroundTheme

class MainActivity : ComponentActivity() {

    val todoList =  mutableStateListOf<Todo>()
    private lateinit var onAddBtnClickListener: OnAddBtnClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpData()
        setUpListeners()

        setContent {
            JetpackComposePlaygroundTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column() {
                        TodoInput(onAddBtnClickListener)
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

    private fun addData(input: String){

        val newTodo = Todo(input, false)
        todoList.add(newTodo)
    }

    private fun setUpListeners(){

        setOnAddBtnClickListener(object : OnAddBtnClickListener{
            override fun onAddBtnClicked(input: String) {
                addData(input)
            }
        })
    }

    private fun setOnAddBtnClickListener(onAddBtnClickListener: OnAddBtnClickListener){
        this.onAddBtnClickListener = onAddBtnClickListener
    }

    interface OnAddBtnClickListener{
        fun onAddBtnClicked(input: String)
    }
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

@Composable
fun TodoInput(onAddBtnClickListener: MainActivity.OnAddBtnClickListener){

    Row(modifier = Modifier.padding(all = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically){

        val textState = remember { mutableStateOf(TextFieldValue())}
        TextField(
            value = textState.value,
            onValueChange = {textState.value = it}
        )

        Spacer(modifier = Modifier.width(8.dp))

        Button(onClick = {
            onAddBtnClickListener.onAddBtnClicked(textState.value.text)

        }) {
            Text(text = "Add")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackComposePlaygroundTheme {

    }
}