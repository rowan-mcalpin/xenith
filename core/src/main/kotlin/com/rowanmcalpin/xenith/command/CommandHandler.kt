package com.rowanmcalpin.xenith.command

import com.rowanmcalpin.xenith.subsystems.Subsystem

/**
 * This class handles the scheduling & running of [Commands][Command].
 */
@Suppress("unused")
object CommandHandler {
    /**
     * The [Commands][Command] currently running.
     */
    private var runningCommands: MutableList<Command> = mutableListOf()

    /**
     * The [Commands][Command] that need to be started.
     */
    private var commandsToStart: MutableList<Command> = mutableListOf()

    /**
     * The [Commands][Command] that need to be stopped. The type is a mutable list, of type Pair. The pair maps a
     * [Command] to a boolean that stores whether the [Command] was blocked, and forced to stop, or stopped because it
     * finished.
     */
    private var commandsToStop: MutableList<Pair<Command, Boolean>> = mutableListOf()

    /**
     * Add a command to run.
     */
    fun addCommand(command: Command) {
        val sharedSubsystems = sharedSubsystems(command)

        if(sharedSubsystems.isEmpty()) {
            commandsToStart.add(command)
            return
        }

        sharedSubsystems.forEach { other ->
            if (!other.protected) {
                commandsToStop.add(Pair(other, true))
            } else {
                // There is a protected command that shares a subsystem.
                // This command cannot be started.
                return
            }

            commandsToStart.add(command)
        }
    }

    /**
     * Starts any [Commands][Command] that need to be started, then clears [commandsToStart].
     */
    fun startCommands() {
        commandsToStart.forEach {
            it.start()
            runningCommands.add(it)
        }
        commandsToStart.clear()
    }

    /**
     * Calls the [update][Command.update] function of each [Command] that is currently scheduled, then stops any
     * [Commands][Command] that are finished running.
     */
    fun execute() {
        // Execute commands
        runningCommands.forEach {
            it.update()
            if (it.finished) {
                commandsToStop.add(Pair(it, false))
            }
        }
    }

    /**
     * Finalizes the stop process by calling [stop][Command.stop] for each [Command], then clearing the [commandsToStop]
     * list.
     */
    fun stopCommands() {
        // First, check each command that needs to be stopped, and see if it's currently running. If it is, remove it
        // from the list of running commands
        commandsToStop.forEach {
            // If the command is currently in the list of running commands, remove it
            if(runningCommands.contains(it.first)) {
                runningCommands.remove(it.first)
            }
            // Call the `stop()` function of each command to stop
            it.first.stop(it.second)
        }

        // Clear the list of commands to stop
        commandsToStop.clear()
    }

    /**
     * Checks each running [Command] to see if any of them share any [Subsystems][Subsystem] in their
     * [requirements][Command.requirements] with the input. If any do, it returns all the found [Commands][Command] as a
     * list.
     *
     * @param command the command to check for subsystem conflicts with
     * @return The list of all [Commands][Command] currently running that share a [Subsystem] in their
     * [requirements][Command.requirements]
     */
    private fun sharedSubsystems(command: Command): List<Command> {
        val results: MutableList<Command> = mutableListOf()
        runningCommands.forEach { other ->
            if(command.sharesSubsystem(other)) {
                results.add(other)
            }
        }
        return results
    }

    /**
     * Returns true if there is a currently running [Command] that uses a specific [Subsystem].
     *
     * @param subsystem the subsystem to check for
     * @return whether there is a [Command] using the [Subsystem]
     */
    fun hasActiveSubsystem(subsystem: Subsystem): Boolean {
        runningCommands.forEach {
            if(it.requirements.contains(subsystem)) return true
        }
        return false
    }
}