package com.rowanmcalpin.xenith.opmode

/**
 * This is the class used to define any commands that should be bound to gamepad buttons.
 */
abstract class Controls {
    /**
     * Reference to the GamepadEx for gamepad1.
     */
    protected lateinit var gamepad1: GamepadEx

    /**
     * Reference to the GamepadEx for gamepad2.
     */
    protected lateinit var gamepad2: GamepadEx

    /**
     * The minimum amount the triggers need to be pressed to be considered "pressed".
     */
    open val triggerThreshold = 0.6

    /**
     * The minimum amount the triggers need to move to be considered "moving".
     */
    open val triggerMoveThreshold = 0.1

    /**
     * The minimum amount the joysticks need to move to be considered "moving".
     */
    open val joystickMoveThreshold = 0.1

    /**
     * Whether to reverse the y values for the joysticks.
     */
    open val reverseYValues = true

    /**
     * Initializes the gamepads.
     */
    fun initializeGamepads() {
        gamepad1 = GamepadEx(OpModeInfo.opMode.gamepad1, triggerThreshold, triggerMoveThreshold, joystickMoveThreshold, reverseYValues)
        gamepad2 = GamepadEx(OpModeInfo.opMode.gamepad2, triggerThreshold, triggerMoveThreshold, joystickMoveThreshold, reverseYValues)

        gamepad1.initialize()
        gamepad2.initialize()
    }

    /**
     * Updates the gamepads.
     */
    fun updateGamepads() {
        gamepad1.update()
        gamepad2.update()
    }

    /**
     * User-customizable function for defining custom pairings of gamepad buttons to commands.
     */
    abstract fun bindCommands()
}