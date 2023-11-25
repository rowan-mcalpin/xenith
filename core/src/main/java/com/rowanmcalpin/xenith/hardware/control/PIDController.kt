package com.rowanmcalpin.xenith.hardware.control

import com.qualcomm.robotcore.util.ElapsedTime

/**
 * This class controls a motor using proportional, integral, and derivative coefficients to calculate the power that the
 * motor should be set to during any given time.
 *
 * @param kP the proportional coefficient
 * @param kI the integral coefficient
 * @param kD the derivative coefficient
 */
@Suppress("unused")
class PIDController(private val kP: () -> Double = { 0.005 }, private val kI: () -> Double = { 0.0 }, private val kD: () -> Double = { 0.0 }): MotorController() {
    /**
     * This class controls a motor using proportional, integral, and derivative coefficients to calculate the power that the
     * motor should be set to during any given time.
     *
     * @param kP the proportional coefficient
     * @param kI the integral coefficient
     * @param kD the derivative coefficient
     */
    constructor(kP: Double = 0.005, kI: Double = 0.0, kD: Double = 0.0): this({ kP },{ kI },{ kD })
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
     * The target value (usually position or velocity) that the MotorController should be aiming for.
     */
    override var target: Double = 0.0

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
        // Calculate motor power
        val error: Double = target - state
        integralSum += error * timer.seconds()
        val derivative = (error - lastError) * timer.seconds()
        lastError = error
        timer.reset()

        return (error * kP.invoke()) + (derivative * kD.invoke()) + (integralSum * kI.invoke())
    }
}