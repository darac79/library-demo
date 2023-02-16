package com.onpier.demo.librarydemo.csv.model;


import java.util.Objects;

import com.opencsv.bean.CsvBindByPosition;


public class Book
{

   @CsvBindByPosition(position = 0)
   private String title;

   @CsvBindByPosition(position = 1)
   private String author;

   @CsvBindByPosition(position = 2)
   private String genre;

   @CsvBindByPosition(position = 3)
   private String publisher;

   public String getTitle()
   {
      return title;
   }

   public void setTitle(String title)
   {
      this.title = title;
   }

   public String getAuthor()
   {
      return author;
   }

   public void setAuthor(String author)
   {
      this.author = author;
   }

   public String getGenre()
   {
      return genre;
   }

   public void setGenre(String genre)
   {
      this.genre = genre;
   }

   public String getPublisher()
   {
      return publisher;
   }

   public void setPublisher(String publisher)
   {
      this.publisher = publisher;
   }

   @Override
   public int hashCode()
   {
      return Objects.hash(author, genre, publisher, title);
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
      Book other = (Book) obj;
      return Objects.equals(author, other.author) && Objects.equals(genre, other.genre)
            && Objects.equals(publisher, other.publisher) && Objects.equals(title, other.title);
   }

   @Override
   public String toString()
   {
      return "Books [title=" + title + ", author=" + author + ", genre=" + genre + ", publisher=" + publisher + "]";
   }


}
