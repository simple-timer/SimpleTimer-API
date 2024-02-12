package dev.simpletimer.simpletimer_api.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

/**
 * Tokenの生成と検証を行うユーティリティです
 */
object TokenUtil {
    //Token生成に用いるアルゴリズムです。
    private val algorithm = Algorithm.HMAC256(System.getenv("JTW_SECRET") ?: "secret")

    //検証に用いるインスタンスです。
    private val verifier = JWT.require(algorithm).withIssuer("oauth0").build()

    /**
     * Tokenを生成します。
     *
     * @param T contentsの型です。
     * @param contents Token内に包含するデータです。
     * @param serializer contentsをJsonにするのに使用する[KSerializer]です。
     * @return 生成されたTokenです。
     * @author mqrimo
     */
    fun <T> generateToken(contents: T, serializer: KSerializer<T>): String {
        //contentsをJsonにします。
        val contentsJson = Json.Default.encodeToString(serializer, contents)

        //JWTのトークンを生成します。
        return JWT.create()
            .withIssuer("oauth0")
            .withClaim("contents", contentsJson)
            .sign(algorithm)
    }

    /**
     * Tokenの検証を行います。
     *
     * @param T token内に包含されているデータの型です。
     * @param token 検証するTokenです。
     * @param serializer token内に包含されているデータをTにデコードする際に使用する[KSerializer]です。
     * @return 懸賞の結果を[Result]で返します。成功の場合は、Token生成時に包含したデータが含まれて返されます。
     * @author mqrimo
     */
    fun <T> verifierToken(token: String, serializer: KSerializer<T>): Result<T> {
        return try {
            //検証を行い、データを取得します。
            val contents = verifier.verify(token).claims["contents"]?.asString()
                ?: throw JWTVerificationException("Invalid verification request.")
            //Jsonからデコードを行い、Resultで返します。
            Result.success(Json.Default.decodeFromString(serializer, contents))
        } catch (exception: JWTVerificationException) {
            //失敗のResultを返します。
            Result.failure(exception)
        }
    }
}