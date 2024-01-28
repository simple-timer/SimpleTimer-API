package dev.simpletimer.simpletimer_api.controllers

import dev.simpletimer.simpletimer_api.data.TimerData
import dev.simpletimer.simpletimer_api.database.Transaction
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
    fun timers(@PathVariable channelId: Long): List<TimerData> {
        return Transaction.getTimerData(channelId)
    }
}