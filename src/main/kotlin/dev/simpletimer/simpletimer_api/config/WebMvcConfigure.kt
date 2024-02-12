package dev.simpletimer.simpletimer_api.config

import dev.simpletimer.simpletimer_api.annotation.Authorize
import dev.simpletimer.simpletimer_api.database.data.TokenData
import dev.simpletimer.simpletimer_api.extensions.authorize
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * コントローラーの事前処理の設定です。
 *
 * @author mqrimo
 */
@Configuration
class WebMvcConfigure : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        //認可
        registry.addInterceptor(AuthorizationHandlerInterceptor)
    }


    /**
     * コントローラーの事前処理として認可を行います。
     *
     * @author mqrimo
     */
    object AuthorizationHandlerInterceptor : HandlerInterceptor {
        override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
            //認可が必要でない場合は許可。
            if (handler !is HandlerMethod) return true
            val authorizeAnnotation =
                AnnotationUtils.getAnnotation(handler.method, Authorize::class.java) ?: return true

            //認可の確認を行い、無効の場合はアクセスを許可しない。
            val authorizedTokenData = request.authorize() ?: return false

            //パーミッションを確認する
            return when (authorizeAnnotation.permission) {
                //対象がPUBLICのTokenでよい場合は許可
                TokenData.Permission.PUBLIC -> true
                //その他は、パーミッションレベルでの確認を行う。
                else -> {
                    authorizeAnnotation.permission.permissionLevel >= authorizedTokenData.permission.permissionLevel
                }
            }
        }
    }
}