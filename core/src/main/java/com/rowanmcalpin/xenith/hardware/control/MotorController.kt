package com.rowanmcalpin.xenith.hardware.control

/**
 * The base class that all motor controllers inherit from.
 * A motor controller is simply a class that calculates the power to set a motor to.
 */
abstract class MotorController {
    /**
     * The target value (usually position or velocity) that the MotorController should be aiming for.
     */
    abstract var target: Double

    /**
     * Initializes the motor controller.
     */
    open fun initialize() { }

    /**
     * Calculates the ideal motor power.
     *
     * @param state the current state of the MotorController
     * @return the calculated power for the motor
     */
    abstract fun calculate(state: Double): Double
}