package com.onpier.demo.librarydemo.service;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onpier.demo.librarydemo.csv.CsvRepository;
import com.onpier.demo.librarydemo.csv.model.Book;
import com.onpier.demo.librarydemo.csv.model.Borrowed;
import com.onpier.demo.librarydemo.csv.model.User;


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
 * @author dostricki
 *
 */
@Service
public class LibraryService
{

   private static final Logger logger = LogManager.getLogger(LibraryService.class);

   private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

   @Autowired
   private CsvRepository csvRepository;

   //@formatter:off

   /**
    * 
    * @return a) returns all users who have actually borrowed at least one book
    */
   public Collection<User> getUsersWithBorrowedBooks()
   {
      Collection<User> usersWithBorrowedBooksToday = getUsersWithBorrowedBooksOnDate(LocalDate.now());
      return usersWithBorrowedBooksToday;
   }

   /**
    * 
    * @return b) returns all non-terminated users who have not currently borrowed anything
    */
   public Collection<User> getNonTerminatedUsersWithoutBorrowedBooks()
   {
      LocalDate today = LocalDate.now();
      
      Collection<User> usersWithBorrowedBooksToday = getUsersWithBorrowedBooksOnDate(LocalDate.now());
      
      Collection<User> usersWithoutBorrowedBooksToday =
            csvRepository.getAllUsers()
            .stream()
            .filter(user -> { // non terminated users
               LocalDate memberSince = parseDate(user.getMemberSince());
               LocalDate memberTill = Optional
                                          .ofNullable(user.getMemberTill())
                                          .filter(date -> !StringUtils.isEmpty(date))
                                          .map(date -> {
                                             return parseDate(date);
                                          })
                                          .orElse(null);
               return memberSince.isBefore(today) && (memberTill == null || memberTill.isAfter(today));
            })
            .filter(user -> {
               return !usersWithBorrowedBooksToday.contains(user);
            })
            .collect(Collectors.toSet());
      
      return usersWithoutBorrowedBooksToday;
   }

   /**
    * 
    * @return c) returns all users who have borrowed a book on a given date
    */
   public Collection<User> getUsersWithBorrowedBooksOnDate(LocalDate date)
   {

      Set<String> allBookBorrowerUserNames = 
            csvRepository.getAllBorrowedBooks()
            .stream()
            .filter(borrowed -> {
               
               LocalDate borrowedFrom = parseDate(borrowed.getBorrowedFrom());
               LocalDate borrowedTo = parseDate(borrowed.getBorrowedTo());
               
               return (borrowedFrom.isBefore(date) || borrowedFrom.isEqual(date) ) &&
                      (borrowedTo == null || borrowedTo.isAfter(date));
            })
            .map(borrowed -> {
               return borrowed.getBorrower().toLowerCase();
            })
            .collect(Collectors.toSet());
      
      Set<User> borrowingUsers = 
            csvRepository.getAllUsers()
               .stream()
               .filter(user -> {
                  String key = user.getName() + ","+ user.getFirstName();
                  return allBookBorrowerUserNames.contains(key.toLowerCase());
               })
               .collect(Collectors.toSet());
      
      return borrowingUsers;
   }

   /**
    * returns d) all books borrowed by a given user in a given date range
    * @return
    */
   public Collection<Book> getBorrowedBooksByUserAndDateRange(String firstName, String lastName, LocalDate fromIncl, LocalDate toExcl)
   {
      
      Set<String> allBorrowedBooks = 
            csvRepository.getAllBorrowedBooks()
            .stream()
            .filter(borrowed -> {
               String key = lastName + "," + firstName;
               return 
                  borrowed.getBorrower().equalsIgnoreCase(key.toLowerCase());
            })
            .filter(borrowed -> {
               
               LocalDate borrowedFrom = parseDate(borrowed.getBorrowedFrom());
               LocalDate borrowedTo = parseDate(borrowed.getBorrowedTo());
               
               boolean overlapLeft = borrowedFrom.isBefore(fromIncl) && (borrowedTo == null || borrowedTo.isBefore(toExcl));
               boolean overlapRight = (borrowedFrom.isAfter(fromIncl) || borrowedFrom.isEqual(fromIncl)) && (borrowedTo == null || borrowedTo.isAfter(toExcl));
               boolean overlapInsight = (borrowedFrom.isAfter(fromIncl) || borrowedFrom.isEqual(fromIncl)) && (borrowedTo != null && borrowedTo.isBefore(toExcl));
               return overlapLeft || overlapRight || overlapInsight;
            })
            .map(Borrowed::getBook)
            .distinct()
            .collect(Collectors.toSet());
      
      Set<Book> borrowedBooks = csvRepository.getAllBooks()
      .stream()
      .filter(book -> {
         return allBorrowedBooks.contains(book.getTitle());
      })
      .collect(Collectors.toSet());
      
      return borrowedBooks;
   }


   /**
    * returns e) returns all available (not borrowed) books
    * @return
    */
   public Collection<Book> getAvailableBooks()
   {
      LocalDate today = LocalDate.now();
      
      Set<String> allBorrowedBooks = 
            csvRepository.getAllBorrowedBooks()
            .stream()
            .filter(borrowed -> {
               
               LocalDate borrowedFrom = parseDate(borrowed.getBorrowedFrom());
               LocalDate borrowedTo = parseDate(borrowed.getBorrowedTo());
               
               boolean isBorrowed = borrowedFrom.isBefore(today) && (borrowedTo == null || borrowedTo.isAfter(today));
               
               return isBorrowed;
            })
            .map(borrowed -> borrowed.getBook())
            .collect(Collectors.toSet());
      
      List<Book> allAvailableBooks = csvRepository.getAllBooks()
         .stream()
         .filter(book -> {
            return !allBorrowedBooks.contains(book.getTitle());
         })
         .collect(Collectors.toList());
      
      return allAvailableBooks;
   }
   
   
   private LocalDate parseDate(String date) {
      
      if(StringUtils.isEmpty(date)) {
         return null;
      }
      return LocalDate.parse(date, formatter);
   }
   
   //@formatter:on
}
