package com.example.bizcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bizcard.ui.theme.BizCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BizCardTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    CreateBizCard()
                }
            }
        }
    }
}

@Composable
fun CreateBizCard() {
    val isShowListBtnClicked = remember{mutableStateOf(false)}

    Surface (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Card(
            modifier = Modifier
                .height(390.dp)
                .width(200.dp)
                .padding(15.dp),
            shape = RoundedCornerShape(corner = CornerSize(15.dp)),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            )
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(top = 30.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CreateProfilePhoto()
                HorizontalDivider(
                    thickness = 5.dp,
                    color = Color.LightGray
                )
                AddInfoRow()
                ElevatedButton (
                    onClick = {
                        isShowListBtnClicked.value = !isShowListBtnClicked.value
                    },
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.elevatedButtonColors(Color.LightGray)

                ) {
                    Text(text = "Portfolio",
                        style = MaterialTheme.typography.labelLarge)
                }
                if (isShowListBtnClicked.value == true) {
                    Contents()
                }
            }
        }
    }
}

@Composable
fun AddInfoRow(modifier: Modifier = Modifier) {
    Column (
        modifier = Modifier.padding(5.dp)
    ){
        Text(
            text = "Milo Rabbit",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Blue,
            modifier = Modifier.padding(3.dp)
        )
        Text(
            text = "Senior devloper, XYZ company\nfrom 2018 - present",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(3.dp)
        )
    }
}

@Composable
private fun CreateProfilePhoto(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .size(150.dp)
            .padding(5.dp),
        shape = CircleShape,
        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        shadowElevation = 4.dp,
        color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
    ) {
        Image(painter = painterResource(R.drawable.profilepic), contentDescription = "")
    }
}


@Composable
fun Contents(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize()
            .padding(5.dp)
    ) {
        Surface(
            modifier = Modifier.padding(3.dp)
                .fillMaxSize(),
            shape = RoundedCornerShape(5.dp),
            border = BorderStroke(2.dp, color = Color.LightGray)
        ) {
            PortFolio(listOf("Project1", "Project2", "Project3", "Project4", "Project5", "Project6"))
        }
    }
}

@Composable
fun PortFolio(listOfProjects: List<String>) {
    LazyColumn (

    ){
        items(listOfProjects) {item ->
            Card (
                modifier = Modifier.padding(10.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(10.dp)
            ){
                Row {
                    CreateProfilePhoto(modifier = Modifier.size(100.dp))
                    Column {
                        Text("$item", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                        Text("Tools used: Programming language, UI, Database", style = MaterialTheme.typography.titleSmall)

                    }
                }
            }
    }
    }

}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    //CreateBizCard()
    Contents()
}