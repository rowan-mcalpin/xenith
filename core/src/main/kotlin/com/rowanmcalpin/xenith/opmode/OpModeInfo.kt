package com.rowanmcalpin.xenith.opmode

import com.qualcomm.robotcore.eventloop.opmode.OpMode

/**
 * The class that contains things that should be able to be referenced from anywhere in the library. For example, a
 * reference to the active OpMode, or a reference to the GamepadEx instances.
 */
object OpModeInfo {
    /**
     * The currently running OpMode.
     */
    lateinit var opMode: OpMode

    /**
     * Gamepad1.
     */
    lateinit var gamepad1: GamepadEx

    /**
     * Gamepad2.
     */
    lateinit var gamepad2: GamepadEx
}