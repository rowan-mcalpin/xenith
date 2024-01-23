package com.rowanmcalpin.xenith.command.utility

import com.qualcomm.robotcore.util.ElapsedTime
import com.rowanmcalpin.xenith.command.Command

/**
 * Displays a message on the driver station.
 *
 * @param message the message to display
 * @param time how long to display it, in seconds. 0 is infinite.
 */
class TelemetryCommand(private val message: () -> String, private val time: Double = 0.0): Command() {
    
    private val timer = ElapsedTime()

    /**
     * If the time is set to 0, return false. Otherwise, check if enough time has passed.
     */
    override val finished: Boolean
        get() = if (time != 0.0) timer.seconds() > time else false

    override fun onStart() {
        timer.reset()
    }

    override fun onUpdate() {

    }
}