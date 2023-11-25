package com.rowanmcalpin.xenith.hardware.control

/**
 * A class that stores the coefficient values used for controlling a PID-Based [MotorController]
 *
 * @param kP the proportional coefficient
 * @param kI the integral coefficient
 * @param kD the derivative coefficient
 */
data class PIDCoefficients(var kP: Double = 0.005, var kI: Double = 0.0, var kD: Double = 0.0)