package com.rowanmcalpin.xenith

import com.rowanmcalpin.xenith.subsystems.Subsystem

/**
 * The command is the core unit in Xenith. It is very abstract, because there are intermediary steps between the basic
 * Command and the classes the user interacts with. It should be exceedingly rare that you will need to reference this
 * class directly.
 */
abstract class Command {
    /**
     * This variable keeps track of whether the command has finished or not. It is used by the [CommandHandler] to stop
     * finished commands.
     */
    open val finished: Boolean = true

    /**
     * This variable is for the [CommandHandler] to keep track of which commands are already running.
     */
    var isStarted = false

    /**
     *  The list of [Subsystems][Subsystem] that are used by this [Command]. There should only be one running [Command] at a time that
     *  uses a particular [Subsystem].
     */
    open val requirements: List<Subsystem> = listOf()

    /**
     * Whether the command should be protected.
     */
    open val protected = false

    /**
     * The start function that is called internally by the [CommandHandler]. It calls the customizable function
     * [onStart], and sets [isStarted] to true.
     */
    fun start() {
        isStarted = true
        onStart()
    }

    /**
     * Customizable function that runs once when the command is added to the list of running commands in the
     * [CommandHandler].
     */
    open fun onStart() { }

    /**
     * The update function that is called internally by the [CommandHandler]. It calls the customizable function
     * [onUpdate].
     */
    fun update() {
        onUpdate()
    }

    /**
     * Customizable function that runs repeatedly for the entire duration of the command's lifetime.
     */
    open fun onUpdate() { }

    /**
     * The stop function called internally by the command handler, which calls the customizable function
     * [onStop][Command.onStop].
     */
    fun stop(blocked: Boolean) {
        onStop(blocked)
    }

    /**
     * Customizable function that is called once when the command has finished running.
     *
     * @param blocked if the command was blocked by a different command using the same [Subsystem] class
     */
    open fun onStop(blocked: Boolean) { }

    /**
     * Whether this command shares any [Subsystem] requirements with any other running commands.
     *
     * @param other the other command to compare to
     * @return Whether the other command shares any [Subsystems][Subsystem] in its [requirements]
     */
    internal fun sharesSubsystem(other: Command): Boolean {
        other.requirements.forEach {
            if(requirements.contains(it)) {
                return true
            }
        }
        return false
    }
}