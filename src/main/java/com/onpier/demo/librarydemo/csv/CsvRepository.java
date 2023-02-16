package com.onpier.demo.librarydemo.csv;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import com.onpier.demo.librarydemo.csv.model.Book;
import com.onpier.demo.librarydemo.csv.model.Borrowed;
import com.onpier.demo.librarydemo.csv.model.User;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.CsvToBeanFilter;

import jakarta.annotation.PostConstruct;


@Repository
public class CsvRepository
{

   protected static final Logger logger = LogManager.getLogger(CsvRepository.class);

   private List<Book> allBooks;
   private List<Borrowed> allBorrowed;
   private List<User> allUsers;

   //@formatter:off

   private CsvToBeanFilter emptyLineFilter = new CsvToBeanFilter() {
      @Override
      public boolean allowLine(String[] stringValues) {
        return !Arrays.stream(stringValues)
           .filter(stringValue -> {
               return !StringUtils.isEmpty(stringValue);
           })
           .collect(Collectors.toSet())
           .isEmpty();
      }
   }; 
         
   
                                                     

   @PostConstruct
   private void initRepository()
      throws IllegalStateException, FileNotFoundException
   {

      
      allBooks = new CsvToBeanBuilder<Book>(new FileReader(ResourceUtils.getFile("classpath:data/books.csv")))
            .withSkipLines(1)
            .withType(Book.class)
            .withFilter(emptyLineFilter)
            .withSeparator(',')
            .build()
            .parse();

      allBorrowed = new CsvToBeanBuilder<Borrowed>(new FileReader(ResourceUtils.getFile("classpath:data/borrowed.csv")))
            .withSkipLines(1)
            .withType(Borrowed.class)
            .withFilter(emptyLineFilter)
            .withSeparator(',')
            .build()
            .parse();

      allUsers = new CsvToBeanBuilder<User>(new FileReader(ResourceUtils.getFile("classpath:data/user.csv")))
            .withSkipLines(1)
            .withType(User.class)
            .withFilter(emptyLineFilter)
            .withSeparator(',')
            .build()
            .parse();
      
      
      logger.info("books: {} ", allBooks);
      logger.info("borrowed: {} ", allBorrowed);
      logger.info("users: {} ", allUsers);
      
   
   }
   
   //@formatter:on

   public Collection<Book> getAllBooks()
   {
      return allBooks;
   }


   public Collection<User> getAllUsers()
   {
      return allUsers;
   }

   public Collection<Borrowed> getAllBorrowedBooks()
   {
      return allBorrowed;
   }

}
