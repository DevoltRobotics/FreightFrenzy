package com.github.serivesmejia.deltacommander

import com.github.serivesmejia.deltacommander.command.DeltaRunCmd
import com.github.serivesmejia.deltacommander.command.DeltaWaitCmd
import com.github.serivesmejia.deltacommander.command.DeltaWaitConditionCmd
import com.github.serivesmejia.deltacommander.dsl.deltaSequence
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import kotlin.reflect.KClass

abstract class DeltaCommand {

    val name = this.javaClass.simpleName
    val requirements = mutableListOf<DeltaSubsystem>()

    var finishRequested = false
        internal set
    internal var endingCalled = false

    internal var allowRequire = true

    open fun init() {}

    abstract fun run()

    open fun ending() { }

    open fun end(interrupted: Boolean) {}

    fun require(vararg reqs: DeltaSubsystem) {
        if(!allowRequire) {
            throw IllegalStateException("Calling require() is not allowed on this point")
        }

        reqs.forEach {
            if (!requirements.contains(it))
                requirements.add(it)
        }
    }

    open fun endCondition() = true

    inline fun <reified S : DeltaSubsystem> require() = require(S::class)

    @Suppress("UNCHECKED_CAST")
    fun <S : DeltaSubsystem> require(clazz: KClass<S>): S {
        for(subsystem in deltaScheduler.subsystems) {
            if(subsystem::class == clazz) {
                require(subsystem)
                return subsystem as S
            }
        }

        throw IllegalArgumentException("Unable to find subsystem ${clazz::class.simpleName} in DeltaScheduler")
    }

    fun requestFinish() {
        finishRequested = true
    }

    fun schedule(isInterruptible: Boolean = true) = deltaScheduler.schedule(this, isInterruptible)

    fun stopAfter(timeSecs: Double): DeltaCommand {
        + deltaSequence {
            - DeltaWaitCmd(timeSecs)
            - DeltaRunCmd(::requestFinish)
        }

        return this
    }

    fun stopOn(condition: () -> Boolean): DeltaCommand {
        + deltaSequence {
            - DeltaWaitConditionCmd(condition)
            - DeltaRunCmd(::requestFinish)
        }

        return this
    }

    fun waitFor() = DeltaWaitConditionCmd(this::isScheduled)

    val isScheduled
        get() = deltaScheduler.commands.contains(this) || deltaScheduler.queuedCommands.contains(this)

    operator fun unaryPlus() = schedule()

    data class State(val interruptible: Boolean)

}