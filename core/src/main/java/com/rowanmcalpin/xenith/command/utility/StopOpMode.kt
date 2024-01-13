package com.rowanmcalpin.xenith.command.utility

import com.rowanmcalpin.xenith.command.Command
import com.rowanmcalpin.xenith.opmode.OpModeInfo

/**
 * Requests the OpMode to be stopped.
 */
class StopOpMode: Command() {
    /**
     * Stops the OpMode when the command is run.
     */
    override fun onStart() {
        OpModeInfo.opMode.requestOpModeStop()
    }
}