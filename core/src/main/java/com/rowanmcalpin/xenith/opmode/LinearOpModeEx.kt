package com.rowanmcalpin.xenith.opmode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.rowanmcalpin.xenith.CommandHandler
import com.rowanmcalpin.xenith.subsystems.Subsystem

abstract class LinearOpModeEx: LinearOpMode() {
    /**
     * The list of subsystems used by this OpMode.
     */
    val subsystems: MutableList<Subsystem> = mutableListOf()

    /**
     * Initialize all the [Subsystems][Subsystem].
     */
    fun initializeSubsystems() {
        subsystems.forEach {
            it.initialize()
        }
    }

    /**
     * Updates the [Subsystems][Subsystem].
     */
    private fun updateSubsystems() {
        subsystems.forEach {
            it.continuous()
            if(CommandHandler.hasActiveSubsystem(it)) it.activeContinuous()
        }
    }

    /**
     * Updates the [CommandHandler].
     */
    private fun updateCommandHandler() {
        CommandHandler.startCommands()
        CommandHandler.execute()
        CommandHandler.stopCommands()
    }

    /**
     * Updates all involved processes.
     */
    fun update() {
        updateSubsystems()
        updateCommandHandler()
    }
}