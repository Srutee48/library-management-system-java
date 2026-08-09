package com.library;

import com.library.model.Book;
import com.library.service.Library;
import com.library.model.Member;
import java.util.ArrayList;

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

        System.out.println("\n---- Searching for 'java'----");
        ArrayList<Book> results = library.searchBooks("java");
        if (results.isEmpty()){
            System.out.println("No matching books found.");
        } else {
            for ( Book book : results){
                System.out.println(book);
            }
        }

        System.out.println("\n ---- Searching for 'xyz' (should find nothing)----");
        ArrayList<Book> noResults = library.searchBooks("xyz");
        if ( noResults.isEmpty()){
            System.out.println("No matching books found.");
        }

        System.out.println("\n--- Adding Members ----");
        Member m1 = library.addMember("Soniya patra", "987456123");
        Member m2 = library.addMember("Ravi Kumar","123654789");

        System.out.println("Added: " + m1);
        System.out.println("Added: " + m2);

        System.out.println("\n---- All Members ----");
        library.viewAllMembers();
    }
}
