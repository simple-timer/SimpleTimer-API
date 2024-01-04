package dev.simpletimer.simpletimer_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SimpleTimerApiApplication

fun main(args: Array<String>) {
    runApplication<SimpleTimerApiApplication>(*args)
}