package dev.simpletimer.simpletimer_api.annotation

import dev.simpletimer.simpletimer_api.database.data.TokenData


/**
 * 認可を必要とするメソッドに付与するアノテーション。
 *
 * @property permission アクセスに必要な[TokenData.Permission]
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Authorize(val permission: TokenData.Permission)