package com.library;

import com.library.model.Book;
import com.library.service.Library;
import com.library.model.Member;
import com.library.model.IssueRecord;
import java.util.ArrayList;

import com.library.exception.DuplicateISBNException;
import com.library.exception.BookNotFoundException;
import com.library.exception.BookUnavailableException;
import com.library.exception.MemberNotFoundException;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        // ---------------------------------------------------------------------------------------
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

        // ---------------------------------------------------------------------------------------
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

        // ---------------------------------------------------------------------------------------
        System.out.println("\n--- Adding Members ----");
        Member m1 = library.addMember("Soniya patra", "987456123");
        Member m2 = library.addMember("Ravi Kumar","123654789");

        System.out.println("Added: " + m1);
        System.out.println("Added: " + m2);

        System.out.println("\n---- All Members ----");
        library.viewAllMembers();


        // ---------------------------------------------------------------------------------------
        System.out.println("\n---- Issuing Books ----");
        try {
            IssueRecord record1 =library.issueBooks("978-0134685991", "M001");
            System.out.println("Issued: " + record1);
        } catch ( BookNotFoundException | BookUnavailableException | MemberNotFoundException  e){
            System.out.println("Error: " +e.getMessage());
        }

        try {
            IssueRecord record2 =library.issueBooks("978-0134685991", "M001");
            System.out.println("Issued: " + record2);
        } catch ( BookNotFoundException | BookUnavailableException | MemberNotFoundException  e){
            System.out.println("Error: " +e.getMessage());
        }

        try {
            IssueRecord record3 =library.issueBooks("000-0000000000", "M001");
            System.out.println("Issued: " + record3);
        } catch ( BookNotFoundException | BookUnavailableException | MemberNotFoundException  e){
            System.out.println("Error: " +e.getMessage());
        }

        System.out.println("\n ----- Books After Issuing ----");
        library.viewAllBooks();



        // ---------------------------------------------------------------------------------------
        System.out.println("\n---- Returning a Book -----");
        try {
            IssueRecord returned = library.returnBook("978-0134685991");
            System.out.println("Returned: " + returned);
        } catch ( BookNotFoundException e){
            System.out.println("Error: " + e.getMessage());
        }

        try {
            IssueRecord returned = library.returnBook("978-0134685991");
            System.out.println("Returned: " + returned);
        } catch ( BookNotFoundException e){
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n ---- Books After Return -----");
        library.viewAllBooks();
        

        

        // ---------------------------------------------------------------------------------------
        System.out.println("\n---- Issuing  another book for the report test ----");
        try {
            library.issueBooks("978-0596009205", "M002");
        } catch ( BookNotFoundException | BookUnavailableException | MemberNotFoundException  e){
            System.out.println("Error: " +e.getMessage());
        }

        System.out.println(" \n ----- Currently Issued Books ----");
        library.viewIssuedBooks();

        System.out.println(" \n ----- Full Issued History ----");
        library.viewAllIssueRecords();

    }
}
