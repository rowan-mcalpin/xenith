package com.rowanmcalpin.xenith.hardware

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorController
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType
import com.rowanmcalpin.xenith.command.Command
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit
import com.rowanmcalpin.xenith.opmode.OpModeInfo
import com.rowanmcalpin.xenith.hardware.control.ControlLoop

/**
 * This class is the Xenith extension of the DcMotorEx class. It avoids having to manually run the `hardwareMap.get()`
 * function. It is recommended to use with [ControlLoop]s
 *
 * @param deviceName the name of the motor, specified by the configuration in the driver station app
 * @param type the type of internal motor used
 * @param gearRatio the total gear ratio of the motor (internal gearing + any external gearing)
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
open class MotorEx(
    val deviceName: String,
    val type: Type = Type.GOBILDA_YELLOWJACKET,
    val gearRatio: Double = 19.2,
    val direction: Direction = Direction.FORWARD,
    val resetEncoderOnInit: () -> Boolean = { true }) {

    //region Extended functionality
    /**
     * The reference to the motor.
     */
    private lateinit var motor: DcMotorEx

    /**
     * Initializes the motor by calling hardwareMap.get(), then calls the [onInitialize] function.
     */
    fun initialize() {
        motor = OpModeInfo.opMode.hardwareMap.get(DcMotorEx::class.java, deviceName)
        motor.direction = direction
        if (resetEncoderOnInit.invoke())
            motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        onInitialize()
    }

    /**
     * User-customizable function that runs when the MotorEx is initialized.
     */
    open fun onInitialize() { }

    /**
     * Gives the number of ticks per revolution this motor has.
     */
    open val ticksPerRevolution
        get() = type.ticksPerRevolution * gearRatio

    //endregion

    //region DcMotorEx members
    open fun setMotorEnable() = motor.setMotorEnable()
    open fun setMotorDisable() = motor.setMotorDisable()
    open val isMotorEnabled: Boolean
        get() = motor.isMotorEnabled
    open fun setVelocity(angularRate: Double, unit: AngleUnit) { motor.setVelocity(angularRate, unit) }
    open var velocity
        get() = motor.velocity
        set(it) { motor.velocity = it }
    open fun getVelocity(unit: AngleUnit) = motor.getVelocity(unit)
    open fun getCurrent(unit: CurrentUnit) = motor.getCurrent(unit)
    open fun getCurrentAlert(unit: CurrentUnit) = motor.getCurrentAlert(unit)
    open fun setCurrentAlert(current: Double, unit: CurrentUnit) = motor.setCurrentAlert(current, unit)
    open val isOverCurrent
        get() = motor.isOverCurrent
    //endregion

    //region DcMotor members
    open var motorType: MotorConfigurationType
        get() = motor.motorType
        set(it) { motor.motorType = it }
    open val controller: DcMotorController
        get() = motor.controller
    open val portNumber
        get() = motor.portNumber
    open var zeroPowerBehavior: DcMotor.ZeroPowerBehavior
        get() = motor.zeroPowerBehavior
        set(it) { motor.zeroPowerBehavior = it }
    open val isBusy
        get() = motor.isBusy
    open val currentPosition
        get() = motor.currentPosition
    open var mode: DcMotor.RunMode
        get() = motor.mode
        set(it) { motor.mode = it }
    //endregion

    //region DcMotorSimple members
    open var power
        get() = motor.power
        set(it) { motor.power = it }
    //endregion

    /**
     * This enum stores the information of each different type of motor.
     *
     * @param ticksPerRevolution the number of encoder ticks per revolution of the internal motor (not the output shaft)
     * @param maxRPM the no-load rpm of the internal motor (not the output shaft)
     * @param stallTorque the torque at which the motor will stall (torque of the internal motor, not the output shaft)
     */
    enum class Type(
        val ticksPerRevolution: Int,
        val maxRPM: Double,
        val stallTorque: Double
    ) {
        TETRIX_MAX_TORQUENADO(24, 6000.0, 11.666667),
        ANDYMARK_NEVEREST(28, 6600.0, 8.75),
        MODERN_ROBOTICS_MATRIX(28, 6000.0, 20.45),
        GOBILDA_YELLOWJACKET(28, 6000.0, 20.45),
        REV_ROBOTICS_HD_HEX(28, 6000.0, 14.8693),
        REV_ROBOTICS_CORE_HEX(4, 9000.0, 6.2939),
    }
}