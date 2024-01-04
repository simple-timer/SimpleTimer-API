package dev.simpletimer.simpletimer_api.query

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import dev.simpletimer.simpletimer_api.data.TimerData
import dev.simpletimer.simpletimer_api.database.Transaction

/**
 * [TimerData]に関するクエリー
 *
 */
class TimerQuery : Query {
    @GraphQLDescription("稼働しているタイマーの一覧を取得する")
    fun timers(channelIdInput: Long): List<TimerData> {
        return Transaction.getTimerData(channelIdInput)
    }
}