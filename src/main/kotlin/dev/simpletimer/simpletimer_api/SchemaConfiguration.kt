package dev.simpletimer.simpletimer_api

import com.expediagroup.graphql.generator.SchemaGeneratorConfig
import com.expediagroup.graphql.generator.TopLevelObject
import com.expediagroup.graphql.generator.hooks.SchemaGeneratorHooks
import com.expediagroup.graphql.generator.toSchema
import dev.simpletimer.simpletimer_api.query.TimerQuery
import dev.simpletimer.simpletimer_api.scalar.LongScalar.longScalar
import graphql.schema.GraphQLSchema
import graphql.schema.GraphQLType
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.reflect.KClass
import kotlin.reflect.KType

/**
 * GraphQLを用いるためのコンフィグ
 *
 */
@Configuration
class SchemaConfiguration {
    @Bean
    fun graphQLSchema(): GraphQLSchema {
        val config = SchemaGeneratorConfig(
            supportedPackages = listOf("dev.simpletimer.simpletimer_api"),
            hooks = CustomSchemaGeneratorHooks()
        )

        return toSchema(queries = listOf(TopLevelObject(TimerQuery())), config = config)
    }

    /**
     * GraphQLでカスタムScalarを用いる際のhook
     *
     */
    class CustomSchemaGeneratorHooks : SchemaGeneratorHooks {
        override fun willGenerateGraphQLType(type: KType): GraphQLType? = when (type.classifier as? KClass<*>) {
            //Long
            Long::class -> longScalar

            else -> null
        }
    }
}