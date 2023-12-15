package dev.simpletimer.simpletimer_api.query

import com.expediagroup.graphql.server.operations.Query
import org.springframework.stereotype.Controller

@Controller
class TimerQuery : Query {
    fun hello() = "Hello World!"
}