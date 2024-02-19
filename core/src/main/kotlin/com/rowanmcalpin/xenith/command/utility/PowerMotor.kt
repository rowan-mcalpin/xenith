package com.rowanmcalpin.xenith.command.utility

import com.qualcomm.robotcore.hardware.DcMotor
import com.rowanmcalpin.xenith.command.Command
import com.rowanmcalpin.xenith.hardware.MotorEx
import com.rowanmcalpin.xenith.subsystems.Subsystem

/**
 * Powers the motor with no internal PID keeping the speed consistent. Effectively is a direct pipeline to the motor
 * power.
 *
 * @param motor the motor to control
 * @param power the power to set the motor to
 * @param requirements the subsystems involved in the command
 */
class PowerMotor(
    private val motor: MotorEx,
    private val power: Double,
    override val requirements: List<Subsystem>
): Command() {
    /**
     * Powers the motor with no internal PID keeping the speed consistent. Effectively is a direct pipeline to the motor
     * power.
     *
     * @param motor the motor to control
     * @param power the power to set the motor to
     * @param requirement the subsystem involved in the command
     */
    constructor (motor: MotorEx, power: Double, requirement: Subsystem):
            this(motor, power, listOf(requirement))

    /**
     * Sets the motors mode and power to the desired power.
     */
    override fun onStart() {
        motor.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        motor.power = power
    }
}