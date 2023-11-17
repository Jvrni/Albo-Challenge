# Problem Solving

### Given a situation where an Android application is facing memory issues due to incorrect handling of contexts, how would you fix it?

**Contexts**

Android SDK is full of classes that have Context as a parent class. Such classes and abstractions can be called “context-related”. Official documentation defines Context as such:
“Interface to global information about an application environment. This is an abstract class whose implementation is provided by the Android system. It allows access to application-specific resources and classes, as well as up-calls for application-level operations such as launching activities, broadcasting and receiving intents, etc.”

What we need to remember about memory leaks + context is that everything relating to Context, such as Activities, Services, Application, has a certain scope and a limited lifespan. So we can’t hold a reference to these types of classes because the state is rapidly changing.  
GC can’t clean resources when the reference of Activity or Context is present, but Activity is Destroyed and does not exist for us.

**You should not**  

 - Avoid using static variables for views or context-related references.
 - Pass a context-related reference to a Singleton class.
 - Improperly use Context. Use applicationContext() instead of activity context or view context when it’s possible. For example, for Toasts, Snackbars.
 - Use a weak reference of the context-related references when needed.



### If an application experiences delays due to network operations in the thread Mainly, how would I optimize the code?

 - Optimize your connection timeouts and retry accordingly. Its better to have short timeout and multiple retries on faster network vs longer timeout and less retries on slower network.
 - You can store the data offline and then when the network is stable we can upload the data.
 - Some networks are fast but they just have high latency period. You need to handle such cases accordingly.
