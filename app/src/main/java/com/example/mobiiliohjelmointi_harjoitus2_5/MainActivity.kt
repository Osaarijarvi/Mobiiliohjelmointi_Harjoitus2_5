package com.example.mobiiliohjelmointi_harjoitus2_5

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.test.isEnabled
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.mobiiliohjelmointi_harjoitus2_5.ui.theme.Mobiiliohjelmointi_Harjoitus2_5Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Mobiiliohjelmointi_Harjoitus2_5Theme {
                    DiceCastApp()
            }
        }
    }
}

val dices = listOf(
    R.drawable.empty_dice,
    R.drawable.dice_1,
    R.drawable.dice_2,
    R.drawable.dice_3,
    R.drawable.dice_4,
    R.drawable.dice_5,
    R.drawable.dice_6
)

@Composable
fun DiceCastApp() {
    var castCounter by remember() { mutableStateOf(0) }
    var totalScore by remember() { mutableStateOf(0) }
    var diceDots1 by remember() { mutableStateOf(0) }
    var diceDots2 by remember() { mutableStateOf(0) }

    Column(
        modifier = Modifier.padding(24.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DiceImage(diceDots1, diceDots2)

        Spacer(modifier = Modifier.height(32.dp))

        if(castCounter <4 ) {
            CastButton(
                castCounter = castCounter,
                enabledCastButton = true,
                onRandom = {
                    // When the function is called, counter goes up with 1
                    castCounter = castCounter++

                    // Numbers matching the dots of both dices are raffled
                    diceDots1 = (1..6).random()
                    diceDots2 = (1..6).random()
                }
            )
        }

        CountScore(
            diceDots1, diceDots2,
            addScore = { diceScore: Int ->
                //Log.d("diceScore", diceScore.toString())
                totalScore = totalScore + diceScore
            })

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Pisteet",
                modifier = Modifier.background(Color.DarkGray)
                    .padding(vertical = 2.dp, horizontal = 8.dp),
                fontSize = 20.sp
            )
            Text(
                text = "$totalScore",
                modifier = Modifier.background(Color.Green)
                    .padding(vertical = 2.dp, horizontal = 8.dp),
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Game is reset after three casts
        if (castCounter == 4)
        {
            StartAgainButton(
                enabledStartAgainButton = true,
                startOver = {
                    enabledCastButton = false
                    castCounter = 0
                    totalScore = 0
                    diceDots1 = 0
                    diceDots2 = 0
                }
            )
        }
    }
}

@Composable
fun CastButton(enabledCastButton : Boolean,
               castCounter: Int,
               onRandom: () -> Unit
) {
    Button(
        enabled = enabledCastButton,
        onClick = onRandom
    ) {
        Text(text = "Heitä", fontSize = 24.sp)
    }
}
@Composable
fun StartAgainButton(
    enabledStartAgainButton : Boolean,
    startOver: () -> Unit
) {
    Button(
        enabled = enabledStartAgainButton,
        onClick = startOver
    ) {
        Text(text = "Aloita Alusta", fontSize = 24.sp)
    }
}

@Composable
fun DiceImage(diceDots1: Int, diceDots2: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painter = painterResource(id = dices[diceDots1]),
            contentDescription = "Dice one values",
            modifier = Modifier.weight(0.5f)
        )
        Image(
            painter = painterResource(id = dices[diceDots2]),
            contentDescription = "Dice two values",
            modifier = Modifier.weight(0.5f)
        )
    }
}

@Composable
fun CountScore(
    diceDots1: Int, diceDots2: Int,
    addScore: (diceScore: Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // When both dices have the same number of dots, sum of dots
        // will be added to the total score.
        if (diceDots1 == diceDots2) {
            addScore(diceDots1 + diceDots2)
        }
        // When the first dice has higher number of dots, its number of dots
        // will be added to the total score.
        if (diceDots1 > diceDots2) {
            addScore(diceDots1)
        }
        // When the second dice has higher number of dots, its number of dots
        // will be added to the total score.
        if (diceDots2 > diceDots1) {
            addScore(diceDots2)
        }
    }
}


