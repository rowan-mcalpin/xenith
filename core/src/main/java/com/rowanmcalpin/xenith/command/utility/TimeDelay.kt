package com.rowanmcalpin.xenith.command.utility

import com.qualcomm.robotcore.util.ElapsedTime
import com.rowanmcalpin.xenith.command.Command

/**
 * This is a delay that waits for a certain number of seconds before finishing.
 */
class TimeDelay(private val seconds: Double): Command() {
    override val finished: Boolean
        get() = timer.seconds() >= seconds

    private val timer = ElapsedTime()

    /**
     * Resets the timer.
     */
    override fun onStart() {
        timer.reset()
    }
}