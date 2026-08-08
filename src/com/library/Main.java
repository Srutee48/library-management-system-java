package com.library;

import com.library.model.Book;
import com.library.service.Library;
import com.library.exception.DuplicateISBNException;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        try{
            library.addBook(new Book("978-0134685991" , "Effective Java", "Joshua Bloch"));
            library.addBook(new Book("978-0596009205" , "Head First Design patterns", "Freeman & Robson"));
        } catch ( DuplicateISBNException e){
            System.out.println("Error: " + e.getMessage());
        }

        library.viewAllBooks();

        try{
            library.addBook(new Book ("978-0134685991" ,"Effective Java (2nd copy)","Joshua Bloch"));
        } catch (DuplicateISBNException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
