package dev.simpletimer.simpletimer_api.database.transaction

import dev.simpletimer.simpletimer_api.database.Connector
import dev.simpletimer.simpletimer_api.database.data.TokenData
import dev.simpletimer.simpletimer_api.database.table.TokenTable
import dev.simpletimer.simpletimer_api.database.table.TokenTable.description
import dev.simpletimer.simpletimer_api.database.table.TokenTable.permission
import dev.simpletimer.simpletimer_api.database.table.TokenTable.tokenId
import dev.simpletimer.simpletimer_api.util.TokenUtil
import kotlinx.serialization.builtins.LongAsStringSerializer
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

/**
 * [TokenTable]に対するトランザクションです。
 *
 * @author mqrimo
 */
object TokenTransaction {
    /**
     * トークンを生成します。
     *
     * @param permission トークンのアクセス権限です。[TokenData.Permission]
     * @return 生成したTokenとそれに紐づいたデータを[Pair]<[String], [TokenData]>で返します。
     * @author mqrimo
     */
    fun generateToken(permission: TokenData.Permission = TokenData.Permission.PUBLIC): Pair<String, TokenData> {
        //データベースへの接続をします。
        Connector.connect()

        //データベースのレコードを追加します。
        val tokenData = transaction {
            TokenTable.insert {
                it[TokenTable.permission] = permission
            }.let {
                TokenData(
                    it[tokenId],
                    it[description],
                    it[TokenTable.permission],
                )
            }
        }

        //トークンを生成します。
        val token = TokenUtil.generateToken(tokenData.tokenId, LongAsStringSerializer)

        //Pairで返します。
        return Pair(token, tokenData)
    }

    /**
     * Tokenから[TokenData]を返します。
     *
     * @param token 対象のTokenです。
     * @return 取得結果の[TokenData]です。無効なTokenの場合はnullを返します。
     * @author mqrimo
     */
    fun getTokenDataFromToken(token: String): TokenData? {
        val tokenId = TokenUtil.verifierToken(token, LongAsStringSerializer).getOrNull() ?: return null

        //データベースへの接続をします。
        Connector.connect()

        //データベースからTokenIDが一致するものを取得します。
        return transaction {
            TokenTable.selectAll().where { TokenTable.tokenId eq tokenId }.orderBy(TokenTable.tokenId).firstOrNull()
                ?.let {
                    TokenData(
                        it[TokenTable.tokenId],
                        it[description],
                        it[permission]
                    )
                }
        }
    }

    /**
     * [TokenData]の一覧を取得します。
     *
     * @return [List]で[TokenData]を返します。
     * @author mqrimo
     */
    fun getTokenDataList(): List<TokenData> {
        Connector.connect()

        //データーベースからすべてのTokenDataを取得します。
        return transaction {
            TokenTable.selectAll().map {
                TokenData(
                    it[tokenId],
                    it[description],
                    it[permission]
                )
            }
        }
    }

    /**
     * Tokenの説明文を設定します。
     *
     * @param tokenId 設定するTokenのidです。
     * @param description 説明文です。
     * @author mqrimo
     */
    fun setTokenDescription(tokenId: Long, description: String) {
        //データベースへの接続をします。
        Connector.connect()

        //データベースに更新をかけます。
        transaction {
            TokenTable.update({ TokenTable.tokenId eq tokenId }) {
                it[TokenTable.description] = description
            }
        }
    }

    /**
     * Tokenを無効化します。
     *
     * @param tokenId 対象のTokenのIdです。
     */
    fun deleteToken(tokenId: Long) {
        //データベースへの接続をします。
        Connector.connect()

        //DELETE
        transaction {
            TokenTable.deleteWhere { TokenTable.tokenId eq tokenId }
        }
    }
}