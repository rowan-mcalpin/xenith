package com.rowanmcalpin.xenith.hardware.control

/**
 * This class controls a motor using a proportional coefficient that is applied to the error in order to calculate the
 * power that the motor should be set to during any given time.
 *
 * @param kP the proportional coefficient
 */
@Suppress("unused")
class PController(private val kP: () -> Double = { 0.005 }): MotorController() {
    /**
     * This class controls a motor using a proportional coefficient that is applied to the error in order to calculate the
     * power that the motor should be set to during any given time.
     * 
     * @param kP the proportional coefficient
     */
    constructor(kP: Double = 0.005): this({ kP })

    /**
     * The target value (usually position or velocity) that the MotorController should be aiming for.
     */
    override var target: Double = 0.0

    /**
     * Calculates the ideal motor power.
     *
     * @param state the current position of the motor
     * @return the calculated motor power
     */
    override fun calculate(state: Double): Double {
        val error: Double = target - state

        return (error * kP.invoke())
    }
}