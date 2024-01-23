package com.rowanmcalpin.xenith.command.groups

import com.rowanmcalpin.xenith.command.Command

/**
 * Runs a group of commands all at the same time. The main purpose for this is to place parallel blocks inside
 * [sequential groups][SequentialGroup].
 */
class ParallelGroup(vararg val command: Command): CommandGroup() {
    /**
     * A list of commands that need cancelling. The type is a mutable list, of type Pair. The pair maps a
     * [Command] to a boolean that stores whether the [Command] was blocked, and forced to stop, or stopped because it
     * finished.
     */
    private val commandsToStop: MutableList<Pair<Command, Boolean>> = mutableListOf()

    /**
     * Starts every command in the list.
     */
    override fun onStart() {
        // Add all the commands to the list.
        command.forEach {
            add(it)
        }

        // Start all commands
        commands.forEach {
            it.start()
        }
    }

    /**
     * Updates all commands simultaneously.
     */
    override fun onUpdate() {
        commands.forEach {
            if (!it.isStarted) {
                it.start()
            }
            it.update()
            if (it.finished) {
                commandsToStop.add(Pair(it, false))
            }
        }
        stopCommands()
    }

    /**
     * Remove any commands that have finished running.
     */
    private fun stopCommands() {
        commandsToStop.forEach {
            it.first.stop(it.second)
            remove(it.first)
        }
        commandsToStop.clear()
    }
}