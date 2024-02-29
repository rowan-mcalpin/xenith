package com.rowanmcalpin.xenith.command.utility

import com.rowanmcalpin.xenith.command.Command
import com.rowanmcalpin.xenith.hardware.MotorEx
import com.rowanmcalpin.xenith.hardware.control.ControlLoop
import com.rowanmcalpin.xenith.subsystems.Subsystem

class HoldPosition(private val motor: MotorEx, private val controlLoop: ControlLoop,
                   override val requirements: List<Subsystem>): Command() {
   constructor(motor: MotorEx, controlLoop: ControlLoop, requirement: Subsystem): this(motor, controlLoop, listOf(requirement))

    override val finished: Boolean = false

    private var position = 0.0

    override fun onStart() {
        position = motor.currentPosition.toDouble()
    }

    override fun onUpdate() {
        motor.power = controlLoop.calculate(motor.currentPosition.toDouble(), position)
    }
}