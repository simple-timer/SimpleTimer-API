package dev.simpletimer.simpletimer_api.data

import com.expediagroup.graphql.generator.scalars.ID

/**
 * タイマーの基本データ
 *
 * @property channelId タイマーを動かすテキストチャンネル
 * @property numberIndex タイマーの番号
 * @property seconds 秒数
 * @property timerServiceData 稼働のデータ
 */
data class TimerData(
    val id: ID,
    val channelId: Long,
    val numberIndex: Int,
    var seconds: Int,
    var displayMessageBase: String? = null,
    val timerServiceData: TimerServiceData = TimerServiceData()
)