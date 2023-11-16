package com.jvrni.core.service

import okhttp3.Interceptor
import okhttp3.Response

/**
 * ENGLISH
 *
 * The purpose of the interceptor is to intercept all outgoing requests.
 * You can add your log/keys/tokens settings here, but remember, don't put your keys in your source code.
 * You can encrypt your keys or add them to your [local.properties].
 *
 * .
 *
 * .
 *
 * SPANISH
 *
 * El propósito del interceptor es interceptar todas las solicitudes salientes.
 * Puedes agregar tu configuración de registro/claves/tokens aquí, pero recuerda, no pongas tus claves en tu código fuente.
 * Puedes cifrar tus claves o agregarlas a tu [local.properties].
 */

class Interceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}