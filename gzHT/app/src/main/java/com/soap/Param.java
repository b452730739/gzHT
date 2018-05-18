package com.soap;
import java.lang.annotation.*;  

@Target(ElementType.PARAMETER)  
@Retention(RetentionPolicy.RUNTIME)  
@Documented  
public @interface Param {  
    String value();  
} 