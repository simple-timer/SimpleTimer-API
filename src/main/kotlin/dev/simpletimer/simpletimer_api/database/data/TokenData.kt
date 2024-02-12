package dev.simpletimer.simpletimer_api.database.data

/**
 * Tokenに紐づいたデータです。
 *
 * @property tokenId トークンに割り振られたIDです。
 * @property description 説明文です。
 * @property permission トークンが持つ権限です。
 */
data class TokenData(
    val tokenId: Long,
    val description: String,
    val permission: Permission
) {
    /**
     * Tokenに付与する権限です。
     *
     * @property permissionLevel 権限の上位性を確認するための数値です。
     * @author mqrimo
     */
    enum class Permission(val permissionLevel: Int = 0) {
        //公開
        PUBLIC(0),
        //読み込み専用
        READONLY(5),
        //読み込み・書き込み
        READWRITE(30),
        //管理者
        ADMIN(9999)
    }
}