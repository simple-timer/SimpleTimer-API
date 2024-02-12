package dev.simpletimer.simpletimer_api.cli_command

import dev.simpletimer.simpletimer_api.database.data.TokenData
import dev.simpletimer.simpletimer_api.database.transaction.TokenTransaction

/**
 * tokenのコマンドの実装です
 */
object TokenCommand {
    private const val COMMAND_HELP = """
token generate - Tokenを生成します。
token list - Tokenの一覧を表示します。
token description <id> - Tokenの説明文を確認します。
token description <id> [description] - Tokenの説明文を変更します。
token delete <id> - Tokenを削除します。
"""

    /**
     * コマンドの入力を受け取り実行します。
     *
     * @param parts 入力の内容です。
     * @author mqrimo
     */
    fun run(vararg parts: String) {
        //サブコマンドの有無を確認します
        if (parts.size < 2) {
            println(COMMAND_HELP)
            return
        }
        when (parts[1]) {
            "generate", "gen" -> {
                if (parts.size < 3) {
                    generate(null)
                } else {
                    //引数をPermissionに変換します
                    val permission = if (TokenData.Permission.entries.map { toString() }.contains(parts[2])) {
                        TokenData.Permission.valueOf(parts[2])
                    } else {
                        null
                    }

                    generate(permission)
                }
            }

            "list", "ls" -> {
                list()
            }

            "description" -> {
                when (parts.size) {
                    2 -> {
                        println(COMMAND_HELP)
                    }

                    3 -> {
                        val tokenId = toLong(parts[2]) ?: return
                        description(tokenId, null)
                    }

                    else -> {
                        val tokenId = toLong(parts[2]) ?: return
                        description(tokenId, parts[3])
                    }
                }
            }

            "delete", "del" -> {
                val tokenId = toLong(parts[2]) ?: return
                delete(tokenId)
            }

            else -> {
                println(COMMAND_HELP)
            }
        }
    }

    /**
     * Tokenの生成を行い、結果をコンソールへ出力します。
     *
     * @param permission 生成に用いる[TokenData.Permission]です。
     * @author mqrimo
     */
    private fun generate(permission: TokenData.Permission?) {
        //データベースでトークンの発行を行います。
        val (token, tokenData) = if (permission == null) {
            TokenTransaction.generateToken()
        } else {
            TokenTransaction.generateToken(permission)
        }
        //こんそーるへ出力をします。
        println(
            """
                                Tokenを生成しました。
                                ID: ${tokenData.tokenId}
                                TOKEN: ${token}
                                DESCRIPTION: ${tokenData.description}
                                PERMISSION: ${tokenData.permission}
                            """.trimIndent()
        )
    }

    /**
     * 有効なTokenの一覧を表示します。
     *
     * @author mqrimo
     */
    private fun list() {
        //データベースへ問い合わせを行います。
        TokenTransaction.getTokenDataList().apply {
            //ヘッダーを出力します。
            println(String.format("%9s %10s %S", "ID", "PERMISSION", "DESCRIPTION"))
            println("--------------------------------------")
        }.forEach {
            //コンソールへ出力を行います。
            println(String.format("%9d %10S %S", it.tokenId, it.permission, it.description))
        }
    }

    /**
     * Tokenの説明文の確認と変更を行います。
     *
     * @param tokenId 対象のTokenのIDです。
     * @param newDescription 新しい説明文。確認の場合はnullを渡してください。
     * @author mqrimo
     */
    private fun description(tokenId: Long, newDescription: String?) {
        //説明文の更新かを確認します。
        if (newDescription == null) {
            //データベースへ問い合わせをし、トークンの説明文をコンソールに出力します。
            TokenTransaction.getTokenDataList().firstOrNull { it.tokenId == tokenId }
                ?.let {
                    println(it.description)
                } ?: println("無効なIDです。")
        } else {
            //データベースへ変更を要求します。
            TokenTransaction.setTokenDescription(tokenId, newDescription)
        }
    }

    /**
     * Tokenの削除を行います
     *
     * @param tokenId 対象のTokenのIDです。
     * @author mqrimo
     */
    private fun delete(tokenId: Long) {
        //データーベースへ削除を要求します。
        TokenTransaction.deleteToken(tokenId)
    }

    /**
     * 文字列をLongへ変更します。
     * 失敗した場合はコンソールへコマンドのヘルプを出力します。
     *
     * @param value 変更対象の文字列です。
     * @return 変更結果です。失敗した場合はnullを返します。
     * @author mqrimo
     */
    private fun toLong(value: String): Long? {
        val result = value.toLongOrNull()
        if (result == null) println(COMMAND_HELP)
        return result
    }
}