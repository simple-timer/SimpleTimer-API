package dev.simpletimer.simpletimer_api.data


/**
 * タイマーの基本データ
 *
 * @property channelId タイマーを動かすテキストチャンネル
 * @property numberIndex タイマーの番号
 * @property timerServiceData 稼働のデータ
 */
data class TimerData(
    val id: Long,
    val channelId: Long,
    val numberIndex: Int,
    var displayMessageBase: String? = null,
    val timerServiceData: TimerServiceData = TimerServiceData()
)