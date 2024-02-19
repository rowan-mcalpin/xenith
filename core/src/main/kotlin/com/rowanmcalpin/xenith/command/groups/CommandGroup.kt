package com.rowanmcalpin.xenith.command.groups

import com.rowanmcalpin.xenith.command.Command

/**
 * This is a special command that contains and manages other commands. Used by [SequentialGroup] and
 * [ParallelGroup].
 */
abstract class CommandGroup : Command() {
    /**
     * The list of commands that will be run.
     */
    val commands: MutableList<Command> = mutableListOf()

    /**
     * If there are no more commands to run, this command is finished.
     */
    override val finished: Boolean
        get() = commands.isEmpty()

    /**
     * Add a command to the list.
     */
    fun add(command: Command) {
        commands.add(command)
    }

    /**
     * Safely remove a command from the list.
     */
    fun remove(command: Command) {
        if (commands.contains(command)) {
            commands.remove(command)
        }
    }
}