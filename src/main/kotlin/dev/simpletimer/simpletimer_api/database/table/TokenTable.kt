package dev.simpletimer.simpletimer_api.database.table

import dev.simpletimer.simpletimer_api.database.data.TokenData
import org.jetbrains.exposed.sql.Table

/**
 * アクセストークンが持つパーミッションのテーブル
 */
object TokenTable : Table("api_token") {
    val tokenId = long("token_id").autoIncrement()

    val description = text("name").default("")
    val permission = enumeration<TokenData.Permission>("permission")

    override val primaryKey = PrimaryKey(tokenId)
}