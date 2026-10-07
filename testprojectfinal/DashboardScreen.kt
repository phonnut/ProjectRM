package com.example.testprojectfinal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.testprojectfinal.accelerometer.AccelerometerData
import com.example.testprojectfinal.bluetooth.BleData
import com.example.testprojectfinal.geomagnetic.GeomagneticData
import com.example.testprojectfinal.gyroscope.GyroscopeData
import com.example.testprojectfinal.wifi.WifiData

private val Ink = Color(0xFF17211D)
private val Muted = Color(0xFF718078)
private val Green = Color(0xFF1F6A4A)
private val Canvas = Color(0xFFF4F6F2)

@Composable
fun DashboardScreen(
    wifi: List<WifiData>, ble: List<BleData>, magnetic: GeomagneticData?,
    acceleration: AccelerometerData?, gyro: GyroscopeData?
) {
    Scaffold(containerColor = Canvas) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 18.dp, bottom = 30.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Text("FIELDNOTE  /  SENSOR", color = Green, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(10.dp))
                Text("สภาพแวดล้อม\nรอบตัวคุณ", color = Ink, style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text("ภาพรวมสัญญาณและเซนเซอร์จากอุปกรณ์", color = Muted, style = MaterialTheme.typography.bodyMedium)
            }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SummaryTile("Wi‑Fi", "${wifi.size}", "เครือข่าย", Modifier.weight(1f))
                    SummaryTile("Bluetooth", "${ble.size}", "อุปกรณ์ใกล้เคียง", Modifier.weight(1f))
                }
            }
            item { SectionTitle("เซนเซอร์ในเครื่อง", "อัปเดตแบบเรียลไทม์") }
            item { SensorCard("สนามแม่เหล็ก", "Geomagnetic", magnetic?.let { "X ${fmt(it.x)}   Y ${fmt(it.y)}   Z ${fmt(it.z)} µT" } ?: "กำลังรอข้อมูล") }
            item { SensorCard("การเคลื่อนไหว", "Accelerometer", acceleration?.let { "X ${fmt(it.x)}   Y ${fmt(it.y)}   Z ${fmt(it.z)} m/s²" } ?: "กำลังรอข้อมูล") }
            item { SensorCard("การหมุน", "Gyroscope", gyro?.let { "X ${fmt(it.x)}   Y ${fmt(it.y)}   Z ${fmt(it.z)} rad/s" } ?: "กำลังรอข้อมูล") }
            item { SectionTitle("Wi‑Fi รอบตัว", "${wifi.size} เครือข่ายที่พบ") }
            if (wifi.isEmpty()) item { EmptyCard("กำลังค้นหาเครือข่าย Wi‑Fi…") }
            items(wifi.take(5)) { network ->
                DataCard(network.ssid.ifBlank { "เครือข่ายไม่ระบุชื่อ" }, "${network.rssi} dBm  ·  ${network.frequency} MHz", network.bssid)
            }
            item { SectionTitle("Bluetooth รอบตัว", "${ble.size} อุปกรณ์ที่พบ") }
            if (ble.isEmpty()) item { EmptyCard("กำลังค้นหาอุปกรณ์ Bluetooth…") }
            items(ble.take(5)) { device -> DataCard(device.name ?: "อุปกรณ์ไม่ระบุชื่อ", "${device.rssi} dBm", device.address) }
            item { Text("ข้อมูลจากเซนเซอร์ของอุปกรณ์นี้", color = Muted, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(top = 6.dp)) }
        }
    }
}

@Composable private fun SummaryTile(title: String, value: String, caption: String, modifier: Modifier = Modifier) {
    Card(modifier, shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = Green)) {
        Column(Modifier.padding(16.dp)) {
            Text(title, color = Color.White.copy(alpha = .78f), style = MaterialTheme.typography.labelMedium)
            Spacer(Modifier.height(8.dp))
            Text(value, color = Color.White, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text(caption, color = Color.White.copy(alpha = .78f), style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable private fun SectionTitle(title: String, detail: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(title, color = Ink, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text(detail, color = Muted, style = MaterialTheme.typography.labelSmall)
    }
}

@Composable private fun SensorCard(title: String, kind: String, values: String) {
    Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(Modifier.fillMaxWidth().padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(title, color = Ink, fontWeight = FontWeight.SemiBold)
                Text("LIVE", color = Green, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
            }
            Text(kind, color = Muted, style = MaterialTheme.typography.labelSmall)
            Spacer(Modifier.height(12.dp))
            Text(values, color = Ink, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable private fun DataCard(title: String, detail: String, subdetail: String) {
    Card(shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 13.dp)) {
            Text(title, color = Ink, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(3.dp))
            Text(detail, color = Green, style = MaterialTheme.typography.bodySmall)
            Text(subdetail, color = Muted, style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable private fun EmptyCard(message: String) {
    Card(shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Text(message, Modifier.fillMaxWidth().padding(16.dp), color = Muted, style = MaterialTheme.typography.bodySmall)
    }
}

private fun fmt(value: Float) = String.format("%.2f", value)
