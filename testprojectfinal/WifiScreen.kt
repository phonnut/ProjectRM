package com.example.testprojectfinal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.testprojectfinal.wifi.WifiData
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WifiScreen(list: List<WifiData>) {

    LazyColumn {

        items(list){wifi->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ){

                Column(
                    modifier = Modifier.padding(12.dp)
                ){

                    Text("SSID : ${wifi.ssid}")

                    Text("BSSID : ${wifi.bssid}")

                    Text("RSSI : ${wifi.rssi} dBm")

                    Text("Frequency : ${wifi.frequency}")

                    Text(
                        "Time : ${
                            SimpleDateFormat(
                                "HH:mm:ss",
                                Locale.getDefault()
                            ).format(Date(wifi.timestamp))
                        }"
                    )

                }

            }

        }

    }

}