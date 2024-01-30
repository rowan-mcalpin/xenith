package com.rowanmcalpin.xenith.system

/**
 * This is the default value for the flag selection. If a flag is not included in this list, it is not included by default.
 */
val defaultFlags: List<Flag> = listOf(Flag.SUBSYSTEM, Flag.COMMAND)

/**
 * This is the list of all possible flags you can enable.
 */
enum class Flag {
    SUBSYSTEM,
    COMMAND
}