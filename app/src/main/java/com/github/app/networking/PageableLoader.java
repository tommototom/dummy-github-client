package com.github.app.networking;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;


/**
 * indicates RetrofitLoaderManager that data is loaded with pagination
 */
@Target(value = TYPE)
@Retention(RUNTIME)
public @interface PageableLoader {
}
