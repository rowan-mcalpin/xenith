package com.rowanmcalpin.xenith.system

/**
 * This class is used to define which optional features you want to use in the OpMode.
 *
 * @param selection The flags to enable
 */
open class Flags(vararg val selection: Flag)

/**
 * This is the default value for the flag selection. If a flag is not included in this list, it is not included by default.
 */
object DefaultFlags: Flags(Flag.SUBSYSTEM, Flag.COMMAND)

/**
 * This is the list of all possible flags you can enable.
 */
enum class Flag {
    SUBSYSTEM,
    COMMAND
}