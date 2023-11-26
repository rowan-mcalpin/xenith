package com.rowanmcalpin.xenith.subsystems

/**
 * Anything that extends subsystem can only exist in one [Command][com.rowanmcalpin.xenith.Command] at a time. This would usually be used in scenarios
 * like mechanisms, where there should only be one [Command][com.rowanmcalpin.xenith.Command] talking to it at any moment. Additionally, the
 * [CommandHandler][com.rowanmcalpin.xenith.CommandHandler] will run [continuous] every loop, and [activeContinuous] every loop that it is being used by a
 * [Command][com.rowanmcalpin.xenith.Command].
 */
interface Subsystem {
    /**
     * This function is run once, when the subsystem is registered. It would be used to do things that should only
     * be done once, like initialize motors, servos, etc.
     */
    fun initialize() { }

    /**
     * This function is run by the [CommandHandler][com.rowanmcalpin.xenith.CommandHandler] every loop.
     */
    fun continuous() { }

    /**
     * This function is run by the [CommandHandler][com.rowanmcalpin.xenith.CommandHandler] every loop that the subsystem class is being used by a [Command][com.rowanmcalpin.xenith.Command].
     */
    fun activeContinuous() { }
}