package com.example.testprojectfinal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.testprojectfinal.bluetooth.BleData

@Composable
fun BleScreen(list: List<BleData>) {

    if (list.isEmpty()) {

        Text(
            text = "ไม่พบอุปกรณ์ Bluetooth",
            modifier = Modifier.padding(16.dp)
        )

        return
    }

    LazyColumn {

        items(list) { device ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {

                Column(
                    modifier = Modifier.padding(12.dp)
                ) {

                    Text("Name : ${device.name}")
                    Text("MAC : ${device.address}")
                    Text("RSSI : ${device.rssi}")

                }

            }

        }

    }
}