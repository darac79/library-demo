package com.onpier.demo.librarydemo.web;


import java.time.LocalDate;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.onpier.demo.librarydemo.csv.model.Book;
import com.onpier.demo.librarydemo.csv.model.User;
import com.onpier.demo.librarydemo.service.LibraryService;


/**
 * Please create a library application.
 * This application should provide a REST API that satisfies the following requirements.
 * 
 * a) returns all users who have actually borrowed at least one book
 * b) returns all non-terminated users who have not currently borrowed anything
 * c) returns all users who have borrowed a book on a given date
 * d) returns all books borrowed by a given user in a given date range
 * e) returns all available (not borrowed) books
 * 
 * no swagger docu/javadoc/fine tuning/extensive testing -> no time
 *
 * @author dostricki
 *
 */
@Controller
public class LibraryController
{

   @Autowired
   private LibraryService libraryService;

   //@formatter:off

   /**
    * 
    * @return
    */
   @RequestMapping(method = RequestMethod.GET, value = "/users/borrowed", produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Collection<User>> getUsersWithBorrowedBooks()
   {
      return ResponseEntity
            .ok()
            .body(libraryService.getUsersWithBorrowedBooks()); 
   }

   /**
    * 
    * @return
    */
   @RequestMapping(method = RequestMethod.GET, value = "/users/nonborrowed", produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Collection<User>> getNonTerminatedUsersWithoutBorrowedBooks()
   {
      return ResponseEntity
            .ok()
            .body(libraryService.getNonTerminatedUsersWithoutBorrowedBooks());
   }

   /**
    * 
    * @param date
    * @return
    */
   @RequestMapping(method = RequestMethod.GET, value = "/users/borrowed/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Collection<User>> getUsersWithBorrowedBooksOnDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date)
   {
      return ResponseEntity
               .ok()
               .body(libraryService.getUsersWithBorrowedBooksOnDate(date));
   }

   /**
    * 
    * @param firstName
    * @param lastName
    * @param fromDateIncl
    * @param toDateExcl
    * @return
    */
   @RequestMapping(
         method = RequestMethod.GET, 
         value = "/books/borrowed/{firstName}/{lastName}/{fromDateIncl}/{toDateExcl}", 
         produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Collection<Book>> getBorrowedBooksByUserAndDateRange(@PathVariable 
                                                        String firstName,
                                                        @PathVariable 
                                                        String lastName,
                                                        @PathVariable 
                                                        @DateTimeFormat(pattern = "yyyy-MM-dd") 
                                                        LocalDate fromDateIncl, 
                                                        @PathVariable 
                                                        @DateTimeFormat(pattern = "yyyy-MM-dd") 
                                                        LocalDate toDateExcl)
   {
      
      return ResponseEntity
            .ok()
            .body(libraryService.getBorrowedBooksByUserAndDateRange(firstName, lastName, fromDateIncl, toDateExcl));
   }

   /**
    * 
    * @return
    */
   @RequestMapping(
         method = RequestMethod.GET, 
         value = "/books/available", 
         produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Collection<Book>> getAvailableBooks()
   {
      return ResponseEntity
            .ok()
            .body(libraryService.getAvailableBooks());
   }

   
   //@formatter:on
}
