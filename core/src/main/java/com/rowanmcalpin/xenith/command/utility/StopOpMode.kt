package com.rowanmcalpin.xenith.command.utility

import com.rowanmcalpin.xenith.Constants
import com.rowanmcalpin.xenith.command.Command

/**
 * Requests the OpMode to be stopped.
 */
class StopOpMode: Command() {
    /**
     * Stops the OpMode when the command is run.
     */
    override fun onStart() {
        Constants.opMode.requestOpModeStop()
    }
}