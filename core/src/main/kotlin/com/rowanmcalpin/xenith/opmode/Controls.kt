package com.rowanmcalpin.xenith.opmode

abstract class Controls {
    protected lateinit var gamepad1: GamepadEx
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

    fun initializeGamepads() {
        gamepad1 = GamepadEx(OpModeInfo.opMode.gamepad1, triggerThreshold, triggerMoveThreshold, joystickMoveThreshold, reverseYValues)
        gamepad2 = GamepadEx(OpModeInfo.opMode.gamepad2, triggerThreshold, triggerMoveThreshold, joystickMoveThreshold, reverseYValues)

        gamepad1.initialize()
        gamepad2.initialize()
    }

    fun updateGamepads() {
        gamepad1.update()
        gamepad2.update()
    }

    abstract fun bindCommands()
}