package com.example.testprojectfinal.fingerprint

interface FingerprintCallback {

    fun onCollected(
        data: FingerprintData
    )

}