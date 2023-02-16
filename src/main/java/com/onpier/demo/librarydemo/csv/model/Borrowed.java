package com.onpier.demo.librarydemo.csv.model;


import java.util.Objects;

import com.opencsv.bean.CsvBindByPosition;


public class Borrowed
{


   @CsvBindByPosition(position = 0)
   private String borrower;

   @CsvBindByPosition(position = 1)
   private String book;

   @CsvBindByPosition(position = 2)
   private String borrowedFrom;

   @CsvBindByPosition(position = 3)
   private String borrowedTo;


   public String getBorrower()
   {
      return borrower;
   }

   public void setBorrower(String borrower)
   {
      this.borrower = borrower;
   }

   public String getBook()
   {
      return book;
   }

   public void setBook(String book)
   {
      this.book = book;
   }

   public String getBorrowedFrom()
   {
      return borrowedFrom;
   }

   public void setBorrowedFrom(String borrowedFrom)
   {
      this.borrowedFrom = borrowedFrom;
   }

   public String getBorrowedTo()
   {
      return borrowedTo;
   }

   public void setBorrowedTo(String borrowedTo)
   {
      this.borrowedTo = borrowedTo;
   }

   @Override
   public int hashCode()
   {
      return Objects.hash(book, borrowedFrom, borrowedTo, borrower);
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj) {
         return true;
      }
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      Borrowed other = (Borrowed) obj;
      return Objects.equals(book, other.book) && Objects.equals(borrowedFrom, other.borrowedFrom)
            && Objects.equals(borrowedTo, other.borrowedTo) && Objects.equals(borrower, other.borrower);
   }

   @Override
   public String toString()
   {
      return "Borrowed [borrower=" + borrower + ", book=" + book + ", borrowedFrom=" + borrowedFrom + ", borrowedTo=" + borrowedTo
            + "]";
   }


}
