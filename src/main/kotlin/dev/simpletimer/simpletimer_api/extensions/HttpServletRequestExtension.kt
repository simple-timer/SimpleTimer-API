package dev.simpletimer.simpletimer_api.extensions

import dev.simpletimer.simpletimer_api.database.data.TokenData
import dev.simpletimer.simpletimer_api.database.transaction.TokenTransaction
import jakarta.servlet.http.HttpServletRequest


/**
 * [HttpServletRequest]の拡張です。
 * リクエストがTokenによる認可が通るかの確認を行います。
 *
 * @return 認可が通る場合は[TokenData]を返します。通らなかった場合はnullを返します。
 * @author mqrimo
 */
fun HttpServletRequest.authorize(): TokenData? {
    //HeaderからAuthorizationを取得し、データの確認を行います。
    val header = this.getHeader("Authorization")?.apply {
        if (!startsWith("Bearer")) return null
    } ?: return null

    //データベースにトークンが有効かを問い合わせてnullじゃない場合は有効なTokenです。
    return TokenTransaction.getTokenDataFromToken(header.substring(7))
}