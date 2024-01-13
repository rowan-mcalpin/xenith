# Basic Usage

This is the extent that most people can expect to be using Xenith for. It does not cover custom motor
controllers, commands, etc.

## Installation

See [installation.md](https://github.com/rowan-mcalpin/xenith/blob/main/docs/pages/installation.md)

## Creating an OpMode

Xenith was designed with a low learning curve in mind. Creating OpModes are nearly the same as using
the stock SDK. However, there are some differences.

First, you should be aware of the `LinearOpModeEx` class. It extends LinearOpMode by adding functions
that are called during the loop. However, you can go the entirely custom route and override `runOpMode`
to do it all yourself. It will be called the exact same way the stock `runOpMode` is called, and
won't do anything special. However, if you override `runOpMode`, you will not be able to use the
other functions defined in `LinearOpModeEx`, such as `onInit`, `onWaitForStart`, `update`, and
`onStop`.

### `onInit`

OnInit is called *once*, when the OpMode is first initialized.

### `onWaitForStart`

OnWaitForStart is called *repeatedly* until the OpMode is started (or stopped).

### `update`

Update is called *repeatedly* once the OpMode is started, until it is stopped.

### `onStop`

OnStop is called *once* when the OpMode is stopped. 

**NOTE:** *NEVER* move motors, servos, etc. in this function. There should be no loops in this function,
and should finish as quickly as possible. 

## Creating a Subsystem

Subsystems are used for anything that should only be able to be used by one command at a time.

Most frequently, they are used for hardware devices like motors, servos, and sensors. That is the
extent this guide will cover. 

To create a subsystem, simply create an object that extends the `Subsystem` interface. Now you can override
various functions within the object: `initialize`, `continuous`, and `activeContinuous`. 

### `initialize`

Initialize is called once when the OpMode is started, provided that the subsystem is included in the
mechanism list. 

### `continuous`

Continuous is called every single update regardless of whether the subsystem is in an active command
at the time.

### `activeContinuous`

ActiveContinuous is called every update that the subsystem is being used by a command.

### Examples

Below is an example of a basic lift mechanism using the `Subsystem` interface.

**Lift**
```kotlin
object Lift : Subsystem {
    private val motor = MotorEx("lift")
    private val controller = PIDController()

    val toHigh: Command
        get() = MotorToPosition(motor, controller, 1000, 1.0, listOf(this))
    val toLow: Command
        get() = MotorToPosition(motor, controller, 0, 1.0, listOf(this))

    override fun initialize() {
        motor.initialize()
        motor.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        controller.initialize()
    }
}
```


*This documentation is not finished. Please check back later*