package dev.simpletimer.simpletimer_api.controllers

import dev.simpletimer.simpletimer_api.annotation.Authorize
import dev.simpletimer.simpletimer_api.database.data.TimerData
import dev.simpletimer.simpletimer_api.database.data.TokenData
import dev.simpletimer.simpletimer_api.database.transaction.TimerDataTransaction
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

/**
 * [TimerData]に関するクエリー
 *
 */
@RestController
class TimerController {
    @CrossOrigin
    @GetMapping("/timers/{channelId}")
    @Authorize(TokenData.Permission.PUBLIC)
    fun timers(@PathVariable channelId: Long): List<TimerData> {
        return TimerDataTransaction.getTimerData(channelId)
    }
}