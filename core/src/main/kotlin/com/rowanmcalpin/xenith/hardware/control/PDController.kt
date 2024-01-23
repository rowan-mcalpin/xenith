package com.rowanmcalpin.xenith.hardware.control

import com.qualcomm.robotcore.util.ElapsedTime
import kotlin.math.abs

/**
 * This class controls a motor using proportional and derivative coefficients to calculate the power that the motor
 * should be set to during any given time.
 *
 * @param kP the proportional coefficient
 * @param kD the derivative coefficient
 */
@Suppress("unused")
class PDController(private val kP: () -> Double = { 0.005 }, private val kD: () -> Double = { 0.0 }): MotorController() {
    /**
     * This class controls a motor using proportional and derivative coefficients to calculate the power that the motor
     * should be set to during any given time.
     *
     * @param kP the proportional coefficient
     * @param kD the derivative coefficient
     */
    constructor(kP: Double, kD: Double): this({ kP },{ kD })
    /**
     * The target value (usually position or velocity) that the MotorController should be aiming for.
     */
    override var target: Double = 0.0

    /**
     * The timer used to keep track of time loss between loops.
     */
    private val timer: ElapsedTime = ElapsedTime()

    /**
     * The calculated error of the previous loop.
     */
    private var lastError: Double = 0.0

    /**
     * Initializes the motor controller.
     */
    override fun initialize() {
        timer.reset()
    }

    /**
     * Calculates the ideal motor power.
     *
     * @param state the current position of the motor
     * @return the calculated motor power
     */
    override fun calculate(state: Double): Double {
        val error = target - state
        val derivative = (error - lastError) * timer.seconds()
        lastError = error
        timer.reset()

        return (error * kP.invoke()) + (derivative * kD.invoke())
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
    override fun isWithinThreshold(state: Double, threshold: Double): Boolean {
        if (abs(target - state) < threshold) {
            return true
        }
        return false
    }
}