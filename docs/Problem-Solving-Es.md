# Preguntas de Resolución de Problemas

### Dada una situación donde una aplicación Android se enfrenta a problemas de memoria debido al manejo incorrecto de contextos, ¿cómo lo solucionaría?

**Contextos**
El SDK de Android está lleno de clases que tienen Context como clase principal. Estas clases y abstracciones pueden denominarse "relacionadas con el contexto". La documentación oficial define Contexto como tal:
“Interfaz a información global sobre un entorno de aplicación. Esta es una clase abstracta cuya implementación la proporciona el sistema Android. Permite el acceso a recursos y clases específicos de la aplicación, así como llamadas para operaciones a nivel de aplicación, como actividades de lanzamiento, transmisión y recepción de intenciones, etc.

Lo que debemos recordar sobre las pérdidas de memoria + contexto es que todo lo relacionado con el contexto, como actividades, servicios y aplicaciones, tiene un alcance determinado y una vida útil limitada. Entonces no podemos hacer referencia a este tipo de clases porque el estado está cambiando rápidamente.
GC no puede limpiar recursos cuando la referencia de Actividad o Contexto está presente, pero la Actividad está Destruida y no existe para nosotros.

**Usted no debe**
- Evite el uso de variables estáticas para vistas o referencias relacionadas con el contexto.
- Pasar una referencia relacionada con el contexto a una clase Singleton.
- Utilizar incorrectamente el contexto. Utilice applicationContext() en lugar del contexto de actividad o vea el contexto cuando sea posible. Por ejemplo, para Tostadas, Snack-barras.
- Utilice una referencia débil de las referencias relacionadas con el contexto cuando sea necesario.



### Si una aplicación experimenta retrasos debido a operaciones de red en el hilo Principal, ¿cómo optimizaría el código?

- Optimice los tiempos de espera de su conexión y vuelva a intentarlo en consecuencia. Es mejor tener un tiempo de espera corto y múltiples reintentos en una red más rápida que un tiempo de espera más largo y menos reintentos en una red más lenta.
- Puede almacenar los datos sin conexión y luego, cuando la red esté estable, podremos cargar los datos.
- Algunas redes son rápidas pero tienen un período de latencia alto. Debe manejar estos casos en consecuencia.