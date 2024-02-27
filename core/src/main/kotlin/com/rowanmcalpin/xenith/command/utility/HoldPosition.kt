package com.rowanmcalpin.xenith.command.utility

import com.rowanmcalpin.xenith.command.Command
import com.rowanmcalpin.xenith.hardware.MotorEx
import com.rowanmcalpin.xenith.hardware.control.ControlLoop

class HoldPosition(private val motor: MotorEx, private val controlLoop: ControlLoop): Command() {

    override val finished: Boolean = false

    private var position = 0.0

    override fun onStart() {
        position = motor.currentPosition.toDouble()
    }

    override fun onUpdate() {
        motor.power = controlLoop.calculate(motor.currentPosition.toDouble(), position)
    }
}