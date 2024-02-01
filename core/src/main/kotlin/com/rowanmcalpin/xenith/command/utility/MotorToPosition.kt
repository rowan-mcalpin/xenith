package com.rowanmcalpin.xenith.command.utility

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.util.Range
import com.rowanmcalpin.xenith.command.Command
import com.rowanmcalpin.xenith.hardware.MotorEx
import com.rowanmcalpin.xenith.hardware.control.MotorController
import com.rowanmcalpin.xenith.subsystems.Subsystem
import kotlin.math.max

/**
 * Moves the motor to a position using a specified [MotorController].
 *
 * @param motor the motor to control
 * @param controller the controller to use
 * @param target the target position in ticks
 * @param speed the maximum speed for the motor
 * @param requirements the subsystems involved in the command
 * @param protected whether this command is unable to be stopped by an incoming command with the same subsystem
 * @param errorThreshold the maximum distance from the target position to be considered correct (in ticks)
 * @param holdPosition whether the motor should actively hold its position once it has reached the target
 */
class MotorToPosition(
    private val motor: MotorEx,
    private val controller: MotorController,
    private val target: Int,
    private val speed: Double,
    override val requirements: List<Subsystem>,
    override val protected: Boolean = false,
    private val errorThreshold: Int = 30,
    private val holdPosition: Boolean = true
): Command() {
    constructor (
        motor: MotorEx,
        controller: MotorController,
        target: Int,
        speed: Double,
        requirement: Subsystem,
        protected: Boolean = false,
        errorThreshold: Int = 30,
        holdPosition: Boolean = true
    ): this(motor, controller, target, speed, listOf(requirement), protected, errorThreshold, holdPosition)

    override val finished
        get() = controller.isWithinThreshold(motor.currentPosition.toDouble(), errorThreshold.toDouble())

    override fun onStart() {
        controller.initialize()
        motor.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        controller.target = target.toDouble()
    }

    override fun onUpdate() {
        val idealPower = controller.calculate(motor.currentPosition.toDouble())
        val clippedPower = Range.clip(idealPower, -max(speed, 1.0), max(speed, 1.0))
        motor.power = clippedPower
    }

    override fun onStop(blocked: Boolean) {
        if(holdPosition) {
            motor.holdCurrentPosition()
        } else {
            motor.power = 0.0
        }
    }
}