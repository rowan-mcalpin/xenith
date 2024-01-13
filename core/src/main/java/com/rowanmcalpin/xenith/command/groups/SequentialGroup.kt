package com.rowanmcalpin.xenith.command.groups

import com.rowanmcalpin.xenith.command.Command

/**
 * Runs a group of commands sequentially (one after the next).
 */
class SequentialGroup(vararg val command: Command): CommandGroup() {

    /**
     * Starts the first command in the sequence.
     */
    override fun onStart() {
        // Adds all commands to the list
        command.forEach {
            add(it)
        }
        // Start the first command
        if (commands.isNotEmpty()) {
            commands[0].start()
        }
    }

    /**
     * Sequentially updates each command in the order they are provided.
     */
    override fun onUpdate() {
        // Check if there are still commands to run
        if (commands.isNotEmpty()) {
            // Check if we've started the next command to run. If we haven't, start it.
            if (!commands[0].isStarted) {
                commands[0].start()
            }

            // Now that we know commands[0] has been started, call its update() function.
            commands[0].update()

            // Check to see if commands[0] is finished, and if it is, remove it.
            if (commands[0].finished) {
                commands[0].stop(false)
                remove(commands[0])
                // If we still have commands to run, start the next one
                if (commands.isNotEmpty()) {
                    commands[0].start()
                }
            }
        }
    }
}