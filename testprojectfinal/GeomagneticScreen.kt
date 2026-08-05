package com.example.testprojectfinal

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.testprojectfinal.geomagnetic.GeomagneticData

@Composable
fun GeomagneticScreen(data: GeomagneticData?) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            if (data == null) {

                Text("Geomagnetic : Waiting...")

            } else {

                Text("X : %.2f".format(data.x))
                Text("Y : %.2f".format(data.y))
                Text("Z : %.2f".format(data.z))
                Text("Magnitude : %.2f µT".format(data.magnitude))

            }

        }

    }

}