package com.rowanmcalpin.xenith.hardware.control

/**
 * The base class that all control loops inherit from.
 * A control loop is simply a class that takes a current and target value and calculates an output.
 */
abstract class ControlLoop {
    /**
     * Initializes the motor controller.
     */
    open fun initialize() {}

    /**
     * Calculates the ideal motor power.
     *
     * @param state the current state of the controlled item
     * @param target the target state of the controlled item
     * @return the calculated power for the motor
     */
    abstract fun calculate(state: Double, target: Double): Double

    /**
     * Checks if the loop is within a certain threshold of the target.
     *
     * @param state the current state of the control loop
     * @param target the target for the control loop
     * @param threshold the maximum distance from the target that should return true
     * @return whether the current state lies within the threshold from the target
     */
    abstract fun isWithinThreshold(state: Double, target: Double, threshold: Double): Boolean

    /**
     * Reset the control loop. Primarily used for timers but can be as complex as needed.
     */
    abstract fun reset()
}