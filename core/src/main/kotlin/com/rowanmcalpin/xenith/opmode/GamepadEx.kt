package com.rowanmcalpin.xenith.opmode

import com.qualcomm.robotcore.hardware.Gamepad
import com.rowanmcalpin.xenith.command.Command
import com.rowanmcalpin.xenith.command.CommandHandler
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * This class is primarily used to carry the references to all of the [Button]s, [Trigger]s, and [Joystick]s of the gamepad. It
 * contains all the references to the various [Controls] that the gamepad contains.
 *
 * @param gamepad the Qualcomm gamepad reference
 * @param triggerThreshold the minimum amount the triggers need to be pressed to be considered "pressed"
 * @param triggerMoveThreshold the minimum amount the triggers need to move to be considered "moving"
 * @param joystickMoveThreshold the minimum amount the joysticks need to move to be considered "moving"
 * @param reverseYValues whether to reverse the y values for the joysticks
 */
class GamepadEx(private val gamepad: Gamepad,
                private val triggerThreshold: Double = 0.6,
                private val triggerMoveThreshold: Double = 0.1,
                private val joystickMoveThreshold: Double = 0.1,
                private val reverseYValues: Boolean = true
) {

    val a = Button { gamepad.a }
    val b = Button { gamepad.b }
    val x = Button { gamepad.x }
    val y = Button { gamepad.y }

    val dpadUp = Button { gamepad.dpad_up }
    val dpadDown = Button { gamepad.dpad_down }
    val dpadLeft = Button { gamepad.dpad_left }
    val dpadRight = Button { gamepad.dpad_right }

    val leftBumper = Button { gamepad.left_bumper }
    val rightBumper = Button { gamepad.right_bumper }

    val leftTrigger = Trigger { gamepad.left_trigger }
    val rightTrigger = Trigger { gamepad.right_trigger }

    val leftStick = Joystick({ gamepad.left_stick_x },
        { gamepad.left_stick_y },
        { gamepad.left_stick_button },
        reverseYValues)
    val rightStick = Joystick({ gamepad.right_stick_x },
        { gamepad.right_stick_y },
        { gamepad.right_stick_button },
        reverseYValues)

    val triggers = listOf(leftTrigger, rightTrigger)
    val sticks = listOf(leftStick, rightStick)

    val controls = listOf(a, b, x, y, dpadUp, dpadDown, dpadLeft, dpadRight, leftBumper, rightBumper,
        leftTrigger, rightTrigger, leftStick, rightStick)

    /**
     * Set the thresholds for the triggers and sticks.
     */
    fun initialize() {
        triggers.forEach {
            it.threshold = triggerThreshold
            it.moveThreshold = triggerMoveThreshold
        }

        sticks.forEach {
            it.moveThreshold = joystickMoveThreshold
        }
    }

    /**
     * Updates all controls
     */
    fun update() {
        controls.forEach {
            it.update()
        }
    }

    /**
     * This class corresponds to a single button on a gamepad. It extends the default Qualcomm functionality by having
     * variables for justPressed and justReleased, as well as commands that can be bound and automatically run when the
     * corresponding condition is met.
     *
     * @param reference The reference to the Qualcomm button variable
     */
    class Button(private val reference: () -> Boolean): Control() {
        /**
         * Whether the button is currently pressed.
         */
        var down = false

        /**
         * Whether the button was just pressed.
         */
        var justPressed = false

        /**
         * Whether the button was just released.
         */
        var justReleased = false

        /**
         * The command to add when the button is pressed.
         */
        var onPress: (() -> Command)? = null

        /**
         * The command to add when the button is released.
         */
        var onRelease: (() -> Command)? = null

        /**
         * This checks the current value of the button and runs the commands that have been bound to it.
         */
        override fun update() {
            val value = reference.invoke()
            justPressed = value && !down
            justReleased = !value && down

            if (onPress != null && justPressed) {
                CommandHandler.addCommand(onPress!!.invoke())
            }
            if (onRelease != null && justReleased) {
                CommandHandler.addCommand(onRelease!!.invoke())
            }

            down = value
        }
    }

    /**
     * This class corresponds to a single trigger (left or right) on a gamepad. It is slightly more complicated than a
     * [Button] because it has a value between 0 and 1 instead of just true or false. It also includes the justPressed
     * and justReleased variables and their corresponding commands, but also a publicly accessible [value] variable and
     * a command that is called whenever the trigger moves.
     *
     * @param reference The reference to the Qualcomm trigger variable
     */
    class Trigger(private val reference: () -> Float): Control() {
        /**
         * The current value of the trigger.
         */
        var value = 0.0

        /**
         * The minimum threshold for the trigger to be considered "down".
         */
        var threshold = 0.6

        /**
         * The minimum threshold for the trigger to be considered to have moved.
         */
        var moveThreshold = 0.1

        /**
         * Whether the value meets or exceeds the specified [threshold].
         */
        var down = false

        /**
         * Whether the trigger was just pressed past a certain [threshold].
         */
        var justPressed = false

        /**
         * Whether the trigger was just released below a certain [threshold].
         */
        var justReleased = false

        /**
         * The command to add when the trigger is pressed.
         */
        var onPress: (() -> Command)? = null

        /**
         * The command to add when the trigger is released.
         */
        var onRelease: (() -> Command)? = null

        /**
         * The command to add whenever the trigger moves.
         */
        var onMove: (() -> Command)? = null

        /**
         * This checks the current value of the trigger and runs the commands that have been bound to it.
         */
        override fun update() {
            val newValue = reference.invoke().toDouble()
            justPressed = newValue > threshold && !down
            justReleased = newValue < threshold && down

            if (onPress != null && justPressed) {
                CommandHandler.addCommand(onPress!!.invoke())
            }
            if (onRelease != null && justReleased) {
                CommandHandler.addCommand(onRelease!!.invoke())
            }
            if (onMove != null && abs(newValue - value) > moveThreshold) {
                CommandHandler.addCommand(onMove!!.invoke())
            }

            value = newValue
        }
    }

    /**
     * This is the most complicated of the control classes. It has a reference to the x and y values as well as a
     * built-in button and automatic reversing of the y-axis, if wanted.
     */
    class Joystick(private val referenceX: () -> Float,
                   private val referenceY: () -> Float,
                   referenceButton: () -> Boolean,
                   private val reverseYValues: Boolean = true): Control() {
        /**
         * The current state of the x-axis.
         */
        var xValue = 0.0

        /**
         * The current state of the y-axis.
         */
        var yValue = 0.0

        /**
         * The built-in button.
         */
        var button = Button(referenceButton)

        /**
         * The minimum threshold for the joystick to be considered to have moved.
         */
        var moveThreshold = 0.1

        /**
         * Whether the joystick is centered.
         */
        var centered = true

        /**
         * Whether the joystick just moved.
         */
        var justMoved = false

        /**
         * The command to add whenever the Joystick moves.
         */
        var onMove: (() -> Command)? = null

        /**
         * This checks & updates the current state of the Joystick and runs the commands
         */
        override fun update() {
            button.update()

            val newX = referenceX.invoke().toDouble()
            val newY = referenceY.invoke().toDouble() * if (reverseYValues) -1 else 1
            val magnitude = sqrt(newX.pow(2) + newY.pow(2))
            val oldMagnitude = sqrt(xValue.pow(2) + yValue.pow(2))

            centered = magnitude >= moveThreshold
            justMoved = if (abs(magnitude - oldMagnitude) >= moveThreshold) true else false

            if (onMove != null && justMoved) {
                CommandHandler.addCommand(onMove!!.invoke())
            }

            xValue = newX
            yValue = newY
        }
    }

    abstract class Control() {
        abstract fun update()
    }
}