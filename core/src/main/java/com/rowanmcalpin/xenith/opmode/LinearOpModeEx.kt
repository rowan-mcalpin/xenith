package com.rowanmcalpin.xenith.opmode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.rowanmcalpin.xenith.command.CommandHandler
import com.rowanmcalpin.xenith.command.Command
import com.rowanmcalpin.xenith.subsystems.Subsystem

/**
 * This class extends the functionality of [LinearOpMode] with functions and overrides related to
 * [Subsystems][Subsystem] and [Commands][Command].
 */
@Suppress("MemberVisibilityCanBePrivate")
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
    fun updateSubsystems() {
        subsystems.forEach {
            it.continuous()
            if(CommandHandler.hasActiveSubsystem(it)) it.activeContinuous()
        }
    }

    /**
     * Updates the [CommandHandler].
     */
    fun updateCommandHandler() {
        CommandHandler.startCommands()
        CommandHandler.execute()
        CommandHandler.stopCommands()
    }

    /**
     * This should be the only function that ever calls OpMode-specific functions.
     *
     * If it is not overridden, it will automatically call onInit once on initialization,
     * wait repeatedly before the OpMode is started, and update repeatedly once the OpMode has
     * started. If it is overridden, you will have to manually add in the checks to see if the
     * OpMode has been stopped, and you must manually update the command handler & subsystems.
     */
    override fun runOpMode() {
        // When the OpMode is initialized, call onInit().
        onInit()
        // Now call onWaitForStart() repeatedly until the OpMode is started.
        while(!isStarted && !isStopRequested) {
            updateSubsystems()
            updateCommandHandler()
            onWaitForStart()
        }
        // Now that the OpMode has been started, call update() repeatedly until it is stopped.
        while (!isStopRequested && isStarted) {
            updateSubsystems()
            updateCommandHandler()
            update()
        }
        // The OpMode has been stopped, so call onStop().
        onStop()
    }

    /**
     * This function is called once when the OpMode is first initialized.
     */
    open fun onInit() { }

    /**
     * This function is called repeatedly until the OpMode is started.
     */
    open fun onWaitForStart() { }

    /**
     * This function is called repeatedly once the OpMode is started & before it is stopped.
     */
    open fun update() { }

    /**
     * This function is called once once the stop button has been pressed. This function should
     * NEVER power motors, servos, or complete any robot motion. It should be used to deconstruct
     * members and safely stop the program.
     */
    open fun onStop() { }
}