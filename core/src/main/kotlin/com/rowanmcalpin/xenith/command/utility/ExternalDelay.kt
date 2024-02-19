package com.rowanmcalpin.xenith.command.utility

import com.rowanmcalpin.xenith.command.Command

/**
 * This is a delay that doesn't finish until a check returns true.
 *
 * @param check the check to invoke every time the [CommandHandler][com.rowanmcalpin.xenith.command.CommandHandler]
 * checks if the command is finished
 */
class ExternalDelay(private val check: () -> Boolean): Command() {

    /**
     * Checks if the check returned true this time.
     */
    override val finished: Boolean
        get() = check.invoke()
}