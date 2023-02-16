package com.onpier.demo.librarydemo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(basePackages = "com.onpier.demo.librarydemo")
@SpringBootApplication
public class StartLibraryApplication
{

   public static void main(String[] args)
   {
      SpringApplication.run(StartLibraryApplication.class, args);
   }

}
