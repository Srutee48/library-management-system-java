package com.library.service;

import com.library.model.Book;
import com.library.model.Member;
import com.library.exception.DuplicateISBNException;
import java.util.ArrayList;

public class Library {
    private ArrayList <Book> books;
    private ArrayList <Member> members;
    private int memberIdCounter;

    public Library(){
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
        this.memberIdCounter = 1;
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
    public ArrayList<Book> searchBooks (String keyword){
        ArrayList<Book> results = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        for ( Book book : books){
            if(book.getTitle().toLowerCase().contains(lowerKeyword)
            || book.getAuthor().toLowerCase().contains(lowerKeyword)
            || book.getIsbn().toLowerCase().contains(lowerKeyword)){
                results.add(book);
            }
        }
        return results;
    }

    public Member addMember(String name, String contactNumber){
        String newId = "M" + String.format ("%03d", memberIdCounter);
        memberIdCounter++;

        Member newMember = new Member (newId, name, contactNumber);
        members.add(newMember);
        return newMember;
    }
    public void viewAllMembers(){
        if(members.isEmpty()){
            System.out.println("No members registered yet.");
            return;
        }
        System.out.println("----All Members (" + members.size() +") ----");
        for(Member member : members){
            System.out.println(member);
        }
    }
}
