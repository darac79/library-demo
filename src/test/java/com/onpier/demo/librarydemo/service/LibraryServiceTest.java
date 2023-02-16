package com.onpier.demo.librarydemo.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.onpier.demo.librarydemo.csv.CsvRepository;
import com.onpier.demo.librarydemo.csv.model.Book;
import com.onpier.demo.librarydemo.csv.model.Borrowed;


@ExtendWith(MockitoExtension.class)
public class LibraryServiceTest
{

   @Mock
   private CsvRepository csvRepository;

   @InjectMocks
   private LibraryService libraryService;

   @Test
   public void getAvailableBooks()
   {
      List<Borrowed> borrowedBooks = new ArrayList<>();

      // is away
      Borrowed borrowed = new Borrowed();
      borrowed.setBorrowedFrom("05/05/2021");
      borrowed.setBorrowedTo(null);
      borrowed.setBook("Junglebook");
      borrowed.setBorrower("Donald Trump");
      borrowedBooks.add(borrowed);

      List<Book> allBooks = new ArrayList<>();

      // is away
      Book book1 = new Book();
      book1.setAuthor("bbbb");
      book1.setGenre("Kinderbuch");
      book1.setPublisher("Disney");
      book1.setTitle("Junglebook");

      // is available
      Book book2 = new Book();
      book2.setAuthor("Jules Verne");
      book2.setGenre("SciFi");
      book2.setPublisher("WerWeiss");
      book2.setTitle("Reise zum Mittelpunkt der Erde");

      allBooks.add(book1);
      allBooks.add(book2);

      MockitoAnnotations.openMocks(this);

      Mockito.when(csvRepository.getAllBorrowedBooks()).thenReturn(borrowedBooks);
      Mockito.when(csvRepository.getAllBooks()).thenReturn(allBooks);

      Collection<Book> availableBooks = libraryService.getAvailableBooks();
      System.out.println(availableBooks);
      Assertions.assertNotNull(availableBooks);
      Assertions.assertFalse(availableBooks.isEmpty());
      Assertions.assertTrue(availableBooks.size() == 1);
      Assertions.assertEquals("Reise zum Mittelpunkt der Erde", availableBooks.iterator().next().getTitle());
   }

   // mehr Zeit mehr Tests
}
