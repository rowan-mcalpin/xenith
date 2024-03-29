package com.rowanmcalpin.xenith.subsystems

import com.rowanmcalpin.xenith.opmode.OpModeInfo

/**
 * # NOT YET IMPLEMENTED
 */
class TelemetryController: Subsystem {
    data class Message(val message: String)

    val telemetryData: MutableList<Message> = mutableListOf()

    override fun continuous() {
        OpModeInfo.opMode.telemetry.clear()
        telemetryData.forEach {
            OpModeInfo.opMode.telemetry.addData(it.message, "")
        }
    }
}