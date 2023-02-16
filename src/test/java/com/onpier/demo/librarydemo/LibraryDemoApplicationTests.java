package com.onpier.demo.librarydemo;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.onpier.demo.librarydemo.web.LibraryController;


@SpringBootTest
class LibraryDemoApplicationTests
{

   @Autowired
   private LibraryController controller;

   @Test
   void contextLoads()
   {


   }

}
