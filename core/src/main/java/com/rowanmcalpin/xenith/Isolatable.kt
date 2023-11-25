package com.rowanmcalpin.xenith

/**
 * Anything that extends Isolatable can only exist in one [Command] at a time. This would usually be used in scenarios
 * like mechanisms, where there should only be one [Command] talking to it at any moment. Additionally, the
 * [CommandHandler] will run [continuous] every loop, and [activeContinuous] every loop that it is being used by a
 * [Command].
 */
interface Isolatable {
    /**
     * This function is run once, when the Isolatable is registered. It would be used to do things that should only
     * be done once, like initialize motors, servos, etc.
     */
    fun initialize() { }

    /**
     * This function is run by the [CommandHandler] every loop.
     */
    fun continuous() { }

    /**
     * This function is run by the [CommandHandler] every loop that the Isolatable class is being used by a [Command].
     */
    fun activeContinuous() { }
}