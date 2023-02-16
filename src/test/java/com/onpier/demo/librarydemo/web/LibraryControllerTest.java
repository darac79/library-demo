package com.onpier.demo.librarydemo.web;


import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.onpier.demo.librarydemo.csv.model.Book;
import com.onpier.demo.librarydemo.service.LibraryService;


@WebMvcTest(LibraryController.class)
public class LibraryControllerTest
{


   @Autowired
   private MockMvc mockMvc;

   @MockBean
   private LibraryService libraryService;


   @Test
   public void greetingShouldReturnMessageFromService()
      throws Exception
   {

      ArrayList<Book> result = new ArrayList<Book>();
      Book book = new Book();
      book.setAuthor("Peter Scholl Latour");
      book.setGenre("Politics");
      book.setPublisher("I don't know");
      book.setTitle("Afrikanische Totenklage");
      result.add(book);


      when(libraryService.getAvailableBooks()).thenReturn(result);
      this.mockMvc.perform(MockMvcRequestBuilders.get("/books/available")).andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Afrikanische Totenklage")));
   }

   // more time more tests

}
