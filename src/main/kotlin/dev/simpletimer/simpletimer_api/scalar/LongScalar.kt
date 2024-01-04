package dev.simpletimer.simpletimer_api.scalar

import graphql.GraphQLContext
import graphql.execution.CoercedVariables
import graphql.language.StringValue
import graphql.language.Value
import graphql.schema.Coercing
import graphql.schema.GraphQLScalarType
import java.util.*

/**
 * [Long]をGraphQLで扱うためのカスタムScalar
 */
object LongScalar {
    //SchemaGeneratorHooksで使うScalarのインスタンス
    val longScalar: GraphQLScalarType =
        GraphQLScalarType.newScalar().name("PrimitiveLong").description("kotlin.Long").coercing(LongScalarCoercing)
            .build()


    /**
     * [longScalar]の作成に用いる[Coercing]
     * 内部では[String]を用いて、[Long]と変換している
     */
    object LongScalarCoercing : Coercing<Long, String> {
        override fun parseValue(input: Any, graphQLContext: GraphQLContext, locale: Locale): Long? {
            return serialize(input, graphQLContext, locale).toLongOrNull()
        }

        override fun parseLiteral(
            input: Value<*>,
            variables: CoercedVariables,
            graphQLContext: GraphQLContext,
            locale: Locale
        ): Long? {
            return (input as? StringValue)?.value?.toLongOrNull()
        }

        override fun serialize(dataFetcherResult: Any, graphQLContext: GraphQLContext, locale: Locale): String =
            dataFetcherResult.toString()
    }
}