package dev.simpletimer.simpletimer_api.database

import dev.simpletimer.simpletimer_api.data.TimerData
import dev.simpletimer.simpletimer_api.data.TimerServiceData
import dev.simpletimer.simpletimer_api.database.dummy.GuildMessageChannel
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * DBへのトランザクションまとめ
 */
object Transaction {
    /**
     * チャンネルのIDからタイマーのデータを取得する
     *
     * @param channelId チャンネルのId
     * @return [List]<[TimerData]>
     */
    fun getTimerData(channelId: Long): List<TimerData> {
        //接続
        Connector.connect()

        //結果用変数
        val result: MutableList<TimerData> = mutableListOf()

        //SELECT
        transaction {
            TimerDataTable.select {
                //チャンネルと終了済みじゃないかを確認
                TimerDataTable.channel.eq(GuildMessageChannel(channelId)) and TimerDataTable.isFinish.eq(false)
            }.forEach {
                //結果用変数に追加
                result.add(
                    TimerData(
                        it[TimerDataTable.timerDataId],
                        it[TimerDataTable.channel].channelId,
                        it[TimerDataTable.numberIndex],
                        it[TimerDataTable.seconds],
                        it[TimerDataTable.displayMessageBase],
                        TimerServiceData(
                            it[TimerDataTable.isStarted],
                            it[TimerDataTable.isMove],
                            it[TimerDataTable.isFinish],
                            it[TimerDataTable.startNanoTime],
                            it[TimerDataTable.adjustTime],
                            it[TimerDataTable.stopTime]
                        )
                    )
                )
            }
        }

        return result
    }
}