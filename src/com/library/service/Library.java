package com.library.service;

import com.library.model.Book;
import com.library.exception.DuplicateISBNException;
import java.util.ArrayList;

public class Library {
    private ArrayList <Book> books;

    public Library(){
        this.books = new ArrayList<>();
    }
    public void addBook( Book newBook) throws DuplicateISBNException {
        for ( Book existingBook : books){
            if(existingBook.getIsbn().equalsIgnoreCase(newBook.getIsbn())){
                throw new DuplicateISBNException("A book with ISBN " + newBook.getIsbn() + " already exists. ");
            }
        }
        books.add(newBook);    
    }
    public void viewAllBooks(){
        if ( books.isEmpty()){
            System.out.println("No books in the library yet");
            return;
        }
        System.out.println("----All Books (" + books.size() + ") ----");
        for ( Book book : books){
            System.out.println(book);
        }
    }
}
