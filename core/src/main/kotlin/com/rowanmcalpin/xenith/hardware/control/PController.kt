package com.rowanmcalpin.xenith.hardware.control

import kotlin.math.abs

/**
 * This class controls a motor using a proportional coefficient that is applied to the error in order to calculate the
 * power that the motor should be set to during any given time.
 *
 * @param kP the proportional coefficient
 */
@Suppress("unused")
class PController(private val kP: () -> Double = { 0.005 }): ControlLoop() {
    /**
     * This class controls a motor using a proportional coefficient that is applied to the error in order to calculate the
     * power that the motor should be set to during any given time.
     * 
     * @param kP the proportional coefficient
     */
    constructor(kP: Double): this({ kP })

    /**
     * Calculates the ideal motor power.
     *
     * @param state the current position of the motor
     * @return the calculated motor power
     */
    override fun calculate(state: Double, target: Double): Double {
        val error: Double = target - state

        return (error * kP.invoke())
    }

    /**
     * Checks if the motor is within a certain threshold of the target. Used when calculating if the
     * motor should be running still. Checks the absolute value of the error (because it doesn't
     * need to be calculating the direction the motor needs to move) against a threshold.
     *
     * @param state the current position of the motor
     * @param threshold the maximum distance from the target that should return true
     * @return whether the current state lies within the threshold from the target.
     */
    override fun isWithinThreshold(state: Double, target: Double, threshold: Double): Boolean {
        if (abs(target - state) < threshold) {
            return true
        }
        return false
    }

    override fun reset() {
        // Nothing to reset
    }
}