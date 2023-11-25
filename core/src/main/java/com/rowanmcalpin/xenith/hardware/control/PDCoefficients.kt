package com.rowanmcalpin.xenith.hardware.control

/**
 * A class that stores the coefficient values used for controlling a PD-Based [MotorController]
 *
 * @param kP the proportional coefficient
 * @param kD the derivative coefficient
 */
data class PDCoefficients(var kP: Double = 0.005, var kD: Double = 0.0)