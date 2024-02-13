package dev.simpletimer.simpletimer_api.database.data

/**
 * タイマーの状態
 *
 * @property seconds 秒数
 * @property isStarted 始まっているか
 * @property isMove 動いているか
 * @property isFinish 終了しているか
 * @property startMilliTime タイマーが開始した時間
 * @property adjustTime タイマーの調整値
 * @property stopTime 一時停止した時の時間を保管 調整に使用
 */
data class TimerServiceData(
    var seconds: Int = 0,
    var isStarted: Boolean = false,
    var isMove: Boolean = true,
    var isFinish: Boolean = false,
    var startMilliTime: Long = 0L,
    var adjustTime: Long = 0L,
    var stopTime: Long = 0L
)