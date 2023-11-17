# Questions

### Explain the principles of MVVM architecture. Why is it preferable over MVC in Android development?

The key principle behind MVVM is the decoupling of View and ViewModel.
This separation allows for better code organization, testability, and maintainability.

Why MVVM is best for breaking down your applications into single-purpose modular components, such as:
- Business logic is decoupled from Ul.
- Easy to maintain and test
- Easy to reuse components
- Loosely coupled architecture: MVVM makes your application architecture loosely coupled.
- You can write unit test cases for both the view model and the Model layer without the need to reference the View.


### Describe how Koin facilitates dependency injection in Android applications

Dependency injection is a powerful technique for managing dependencies in Android apps, and Koin offers a simple and pragmatic approach to achieving it.
By using Koin, developers can create more modular and maintainable code.

**Setting:**
- Koin uses a simple DSL-based configuration approach, making it easy to set up and understand.

**Learning curve:**
- Koin is known for its simplicity and beginner-friendly nature. Its DSL syntax and simple configuration make it easy to understand.

**Performance:**
- Koin's lightweight nature makes it fast and efficient as it avoids code generation and reflection.


### Compare Retrofit and Volley for making network calls on Android. What are the advantages of using one over the other?

**Retrofit**

- Retrofit can analyze many other types of responses automatically, such as:
  Boolean: The web API response must be boolean.
  Integer: The web API response must be an integer.
  Date: The web API response must be a long format date.
  String – The web API response must be in string format.
  Object: The web API response must be in a JSON object.
  Collections: The web API response must be in string format.
  Loading image.

- The update does not support caching.

- The upgrade does not support any retry mechanism. But it can be achieved manually by doing some additional code.

- On the other hand, Retrofit has full support for post requests and multi-part uploads.


**Volley**

- StringRequest: This type of request converts the response to a string.
  JsonObjectRequest: This type of request and response is automatically converted to a JSONObject.
  JsonArrayRequest: This type of request and response is automatically converted to a JSONArray.
  ImageRequest: This type of request converts the response into a decoded bitmap.

- Caching: Android Volley has a flexible caching mechanism.
  When a request is made through volley first the cache is checked for a suitable response, if it is found there it is returned and parsed; otherwise, a network access is made.

- Retry mechanism: With volley, we can set a retry policy using the setRetryPolicy method. Supports custom request timeout, retry count, and delay multiplier.

- Multi-Part Post and Upload Requests: Volley supports multi-part post and upload requests, but for post requests, we have to convert our java objects to JSONObject. Also for multi-part uploads, we have to do some extra code and use some extra classes.