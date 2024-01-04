package dev.simpletimer.simpletimer_api.database

import org.jetbrains.exposed.sql.Database

/**
 * データベースへの接続を行う
 */
object Connector {
    //接続に必要な環境変数から持ってくる
    //アドレス
    private val databaseAddress = System.getenv("SIMPLETIMER_DB_ADDRESS")

    //DB
    private val databaseScheme = System.getenv("SIMPLETIMER_DB_SCHEME")

    //ユーザー名
    private val databaseUser = System.getenv("SIMPLETIMER_DB_USER")

    //パスワード
    private val databasePassword = System.getenv("SIMPLETIMER_DB_PASS")


    /**
     * DBに接続
     *
     */
    fun connect() {
        Database.connect(
            "jdbc:postgresql://${databaseAddress}/${databaseScheme}",
            "org.postgresql.Driver",
            databaseUser,
            databasePassword
        )
    }
}