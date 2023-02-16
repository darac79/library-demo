package com.onpier.demo.librarydemo.csv.model;


import java.util.Objects;

import com.opencsv.bean.CsvBindByPosition;


public class User
{

   @CsvBindByPosition(position = 0)
   private String name;

   @CsvBindByPosition(position = 1)
   private String firstName;

   @CsvBindByPosition(position = 2)
   private String memberSince;

   @CsvBindByPosition(position = 3)
   private String memberTill;

   @CsvBindByPosition(position = 4)
   private String gender;

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getFirstName()
   {
      return firstName;
   }

   public void setFirstName(String firstName)
   {
      this.firstName = firstName;
   }

   public String getMemberSince()
   {
      return memberSince;
   }

   public void setMemberSince(String memberSince)
   {
      this.memberSince = memberSince;
   }

   public String getMemberTill()
   {
      return memberTill;
   }

   public void setMemberTill(String memberTill)
   {
      this.memberTill = memberTill;
   }

   public String getGender()
   {
      return gender;
   }

   public void setGender(String gender)
   {
      this.gender = gender;
   }

   @Override
   public int hashCode()
   {
      return Objects.hash(firstName, gender, memberSince, memberTill, name);
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
      User other = (User) obj;
      return Objects.equals(firstName, other.firstName) && Objects.equals(gender, other.gender)
            && Objects.equals(memberSince, other.memberSince) && Objects.equals(memberTill, other.memberTill)
            && Objects.equals(name, other.name);
   }

   @Override
   public String toString()
   {
      return "User [name=" + name + ", firstName=" + firstName + ", memberSince=" + memberSince + ", memberTill=" + memberTill
            + ", gender=" + gender + "]";
   }
}
