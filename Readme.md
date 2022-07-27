# Handling Fail Cases in Spring Boot

Spring Boot provides us tools to handle exceptions beyond simple ‘try-catch’ blocks. To use these tools, we apply a couple of annotations that allow us to treat exception handling as a cross-cutting concern:

- @ResponseStatus
- @ExceptionHandler
- @ControllerAdvice

### @ResponseStatus
As the name suggests, @ResponseStatus allows us to modify the HTTP status of our response. It can be applied in the following places:

On the exception class itself
Along with the @ExceptionHandler annotation on methods
Along with the @ControllerAdvice annotation on classes

### @ExceptionHandler
The @ExceptionHandler annotation gives us a lot of flexibility in 
terms of handling exceptions. For starters, to use it, we simply need 
to create a method either in the controller itself or
in a @ControllerAdvice class and annotate it with @ExceptionHandler:
And we can also use it with built in error classes.

### @ControllerAdvice
Why It is Called `Controller Advice`
The term 'Advice' comes from Aspect-Oriented programming (AOP) which allows us to inject
cross -cutting code (called advice) around existing methods. A controller advice allows us to
intercept and modify the return values off controller methods, in our case to handle
exceptions.

Controller advices classes allows us to apply exeception handlers to more than one
or all controllers in our application.


Automatically Spring generates a default error for us, 
This is generated by the `BasicErrorController`
```html
Whitelabel Error Page
This application has no explicit mapping for /error, so you are seeing this as a fallback.

Wed Jul 27 12:03:53 WAT 2022
There was an unexpected error (type=Internal Server Error, status=500).
No value present
java.util.NoSuchElementException: No value present
```
#### Create an Error Handling Class

Option1:
- Head to Root Dir Create an `error` class.
- `error` class with entity (timestamp, status, message, path, next)
- Using the error class give method a Return type of `ResponseEntity`.
```
 AppError error = new AppError(404, "User not Found","users/" +id, "Head Back To Home Page");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
```

Option2:
- Pick a target a default error exception type.
- Create  a custom class to handle error whenever that error is prompted.
```
 @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public AppError handleNoSuchElementException(NoSuchElementException exception, HttpServletRequest request){
        AppError error = new AppError(404, "User not Found",request.getServletPath(), "Head Back To Home Page");
        return error;
    }
```
- In the above code we choose `NoSuchElementException` everytime this error occurs
Our custom error message overides it.

Option3
- Creating Our Own Exception
- Create a custom exeception class `NothingFoundException`
- Extend `NothingFoundException` to `RuntimeException` class
- create a constructor we can instatntiate it with an argument `(message)`
- Head to user service and switch the `.get()`
```
return this.userRepository.findById(id).get();
```
`.get()`: checks if a value is present, then it returns the value,
otherwise throws NoSuchElementException.

- `.get()` change to `orElseThrow()`
```
 public User getUserById(long id) {
        return this.userRepository.findById(id).orElseThrow(()-> new NothingFoundException("User Ain't Existing"));
    }
```
```
`orElseThrow()`: checks If a value is present, then it returns the value, 
otherwise throws an exception 
produced by the exception supplying function.
```


```
 @ExceptionHandler(NothingFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public AppError handleNothingFoundException(NothingFoundException exception, HttpServletRequest request){
        AppError error = new AppError(404, exception.getMessage(), request.getServletPath(), "Article Not Found");
        return error;
    }
```

Option4
- Instead of defining an ExceptionHandler in all our class
  - We can create a single class with `RestControllerAdvice` annotation
  - Which would be prompt when ever an error occurs

Using JsonInclude helps tell Json the entity to print out.
`@JsonInclude(JsonInclude.Include.NON_NULL)`
If there is an entity that is non-null it wont be printed out.


Spring Internally forwards an error to an endpoiint 
e.g: `/error`
This endpoint is been processed by a controller called `BasicErrorController`.
We can replace this controller with our own custom controller.
