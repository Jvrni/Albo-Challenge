# Albo-Challenge

**Explique los principios de la arquitectura MVVM. ¿Por qué es preferible sobre MVC en
desarrollo Android?**
```
El principio clave detrás de MVVM es el desacoplamiento de View y ViewModel.
Esta separación permite una mejor organización, capacidad de prueba y mantenimiento del código.

Por qué MVVM es mejor para dividir sus aplicaciones en componentes modulares de propósito único, como:
- La lógica empresarial está desacoplada de Ul.
- Fácil de mantener y probar
- Componentes fáciles de reutilizar
- Arquitectura débilmente acoplada: MVVM hace que la arquitectura de su aplicación esté débilmente acoplada.
- Puede escribir casos de prueba unitaria tanto para el modelo de vista como para la capa Modelo sin la necesidad de hacer referencia a la Vista.
```

**Describa cómo Koin facilita la inyección de dependencia en aplicaciones Android.**
```
La inyección de dependencia es una técnica poderosa para gestionar dependencias en aplicaciones de Android, y Koin ofrece un enfoque simple y pragmático para lograrlo.
Al utilizar Koin, los desarrolladores pueden crear código más modular y fácil de mantener.

**Configuración:** 
- Koin utiliza un enfoque de configuración simple basado en DSL, lo que facilita su configuración y comprensión.

**Curva de aprendizaje:**
- Koin es conocido por su simplicidad y su naturaleza amigable para principiantes. Su sintaxis DSL y su sencilla configuración hacen que sea fácil de entender.

**Rendimiento:**
- La naturaleza liviana de Koin lo hace rápido y eficiente, ya que evita la generación y reflexión de código.
```

**Compare Retrofit y Volley para realizar llamadas de red en Android. ¿Cuáles son las
ventajas de usar uno sobre el otro?**
```
**RETROFIT**

1. Retrofit puede analizar muchos otros tipos de respuestas automáticamente, como:
Boolean: la respuesta de la API web debe ser booleana.
Integer: la respuesta de la API web debe ser un número entero.
Date: la respuesta de la API web debe ser una fecha en formato largo.
String: la respuesta de la API web debe estar en formato de cadena.
Object: la respuesta de la API web debe estar en un objeto JSON.
Collections: la respuesta de la API web debe estar en formato de cadena.
Cargando imagen.

2. La actualización no admite el almacenamiento en caché.

3. La modernización no admite ningún mecanismo de reintento. Pero se puede lograr manualmente haciendo algún código adicional.

4. Por otro lado, Retrofit tiene soporte completo para solicitudes de publicación y cargas de varias partes.


**VOLLEY**

1. StringRequest: este tipo de solicitud convierte la respuesta en una cadena.
JsonObjectRequest: este tipo de solicitud y respuesta se convierte automáticamente en un JSONObject.
JsonArrayRequest: este tipo de solicitud y respuesta se convierte automáticamente en un JSONArray.
ImageRequest: este tipo de solicitud convierte la respuesta en un mapa de bits decodificado.

2. Almacenamiento en caché: Android Volley tiene un mecanismo de almacenamiento en caché flexible.
Cuando se realiza una solicitud a través de volley primero, se verifica el caché para obtener una respuesta adecuada, si se encuentra allí, se devuelve y se analiza; de lo contrario, se realiza un acceso a la red.

3. Mecanismo de reintento: con volley, podemos establecer una política de reintento utilizando el método setRetryPolicy. Admite el tiempo de espera de solicitud personalizado, el número de reintentos y el multiplicador de retrasos.

4.Solicitudes de publicaciones y cargas de varias partes: Volley admite solicitudes de publicaciones y cargas de varias partes, pero para las solicitudes de publicaciones, tenemos que convertir nuestros objetos java a JSONObject. También para cargas de varias partes, tenemos que hacer algo de código adicional y usar algunas clases adicionales.
```
