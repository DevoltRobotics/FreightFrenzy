package com.github.serivesmejia.deltacommander.dsl

import com.github.serivesmejia.deltacommander.DeltaCommand
import com.github.serivesmejia.deltacommander.command.DeltaRunCmd
import com.github.serivesmejia.deltacommander.command.DeltaSequentialCmd
import com.github.serivesmejia.deltacommander.command.DeltaWaitCmd
import com.github.serivesmejia.deltacommander.command.DeltaWaitConditionCmd
import com.github.serivesmejia.deltadrive.utils.task.Task

class DeltaSequenceBuilder(private val block: DeltaSequenceBuilder.() -> Unit) {

    private val commands = mutableListOf<DeltaCommand>()

    operator fun <T : DeltaCommand> T.unaryMinus(): T {
        commands.add(this)
        return this
    }

    operator fun <T : Task<*>> T.unaryMinus(): T {
        commands.add(this.command)
        return this
    }

    fun DeltaCommand.dontBlock() = DeltaRunCmd(this::schedule)

    fun Task<*>.dontBlock() = DeltaRunCmd(this.command::schedule)

    fun waitFor(condition: () -> Boolean) = DeltaWaitConditionCmd(condition)

    fun waitForSeconds(seconds: Double) = DeltaWaitCmd(seconds)

    fun build(): DeltaSequentialCmd {
        block()
        return DeltaSequentialCmd(*commands.toTypedArray())
    }

}

fun deltaSequence(block: DeltaSequenceBuilder.() -> Unit) = DeltaSequenceBuilder(block).build()