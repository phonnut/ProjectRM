package com.example.testprojectfinal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.testprojectfinal.accelerometer.AccelerometerData

@Composable
fun AccelerometerScreen(data: AccelerometerData?) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            if (data == null) {

                Text("Accelerometer : Waiting...")

            } else {

                Text("Accelerometer")
                Text("X : %.2f".format(data.x))
                Text("Y : %.2f".format(data.y))
                Text("Z : %.2f".format(data.z))

            }

        }

    }

}