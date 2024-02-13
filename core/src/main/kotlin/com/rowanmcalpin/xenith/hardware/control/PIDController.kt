package com.rowanmcalpin.xenith.hardware.control

import com.qualcomm.robotcore.util.ElapsedTime
import kotlin.math.abs

/**
 * This is a control class using proportional, integral, and derivative coefficients to calculate an optimal output.
 *
 * @param kP the proportional coefficient
 * @param kI the integral coefficient
 * @param kD the derivative coefficient
 */
@Suppress("unused")
class PIDController(
    private val kP: () -> Double = { 0.005 },
    private val kI: () -> Double = { 0.0 },
    private val kD: () -> Double = { 0.0 }): ControlLoop() {
    /**
     * This is a control class using proportional, integral, and derivative coefficients to calculate an optimal output.
     *
     * @param kP the proportional coefficient
     * @param kI the integral coefficient
     * @param kD the derivative coefficient
     */
    constructor(kP: Double, kI: Double, kD: Double): this({ kP },{ kI },{ kD })

    /**
     * This is a control class using proportional, integral, and derivative coefficients to calculate an optimal output.
     *
     * @param coefficients the triplet of coefficients for this PID controller
     */
    constructor(coefficients: PIDTriplet): this(coefficients.kP, coefficients.kI, coefficients.kD)

    /**
     * The sum of the error over time.
     */
    private var integralSum: Double = 0.0

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
    override fun calculate(state: Double, target: Double): Double {
        // Calculate motor power
        val error: Double = target - state
        integralSum += error * timer.seconds()
        val derivative = (error - lastError) * timer.seconds()
        lastError = error
        timer.reset()

        return (error * kP.invoke()) + (derivative * kD.invoke()) + (integralSum * kI.invoke())
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
        timer.reset()
    }
}

/**
 * This is a triplet of values used to set the constants of a PID controller in a separate grouping (so they can be more
 * easily modified by other classes).
 *
 * @param kP the proportional coefficient
 * @param kI the integral coefficient
 * @param kD the derivative coefficient
 */
data class PIDTriplet(val kP: () -> Double, val kI: () -> Double, val kD: () -> Double) {
    constructor(kP: Double, kI: Double, kD: Double): this({ kP }, { kI }, { kD })
}