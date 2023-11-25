package com.rowanmcalpin.xenith.hardware.control

import com.qualcomm.robotcore.util.ElapsedTime

/**
 * This class controls a motor using proportional and derivative coefficients to calculate the power that the motor
 * should be set to during any given time.
 *
 * @param coefficients the coefficients for the PD controller
 */
class PDController(private val coefficients: PDCoefficients): MotorController() {
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

        return (error * coefficients.kP) + (derivative * coefficients.kD)
    }
}