package com.library.service;

import com.library.model.Book;
import com.library.model.Member;
import com.library.model.IssueRecord;
import com.library.exception.DuplicateISBNException;
import com.library.exception.MemberNotFoundException;
import com.library.exception.BookUnavailableException;
import com.library.exception.BookNotFoundException;
import java.util.ArrayList;
import java.time.LocalDate;
public class Library {
    private ArrayList <Book> books;
    private ArrayList <Member> members;
    private ArrayList<IssueRecord> issueRecords;
    private int memberIdCounter;

    public Library(){
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
        this.issueRecords = new ArrayList<>();
        this.memberIdCounter = 1;
    }

    public ArrayList<Book> getBooks(){
        return books;
    }

    public ArrayList<Member> getMembers(){
        return members;
    }

    public ArrayList<IssueRecord> getIssueRecords(){
        return issueRecords;
    }

    public void loadData(ArrayList<Book> loadedBooks, ArrayList<Member> loadedMembers, ArrayList<IssueRecord> loadedRecords){
        this.books = loadedBooks;
        this.members = loadedMembers;
        this.issueRecords = loadedRecords;

        int maxId = 0;
        for( Member member : members){
            try {
                int idNumber = Integer.parseInt(member.getMemberId().substring(1));
                if( idNumber > maxId){
                    maxId = idNumber;
                }
            } catch ( NumberFormatException e){
                // Malformed member ID (doesn't start with "M" + digits) - skip it, don't crash
            }
        }
        this.memberIdCounter = maxId + 1;
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

    public IssueRecord issueBook ( String isbn, String memberId) throws BookNotFoundException, MemberNotFoundException, BookUnavailableException {
        Book targetBook = null;
        for ( Book book : books){
            if( book.getIsbn().equalsIgnoreCase(isbn)){
                targetBook = book;
                break;
            }
        }
        if ( targetBook == null){
            throw new BookNotFoundException ("No book found with ISBN: " + isbn);
        }

        Member targetMember = null;
        for ( Member member : members){
            if( member.getMemberId().equalsIgnoreCase(memberId)){
                targetMember = member;
                break;
            }
        }
        if ( targetMember == null){
            throw new MemberNotFoundException ("No member found with ID: " + memberId);
        }
        if ( !targetBook.isAvailable()){
            throw new BookUnavailableException("Book \"" + targetBook.getTitle() + "\" is currently not available.");
        }
        targetBook.setAvailable(false);
        IssueRecord record = new IssueRecord (isbn, memberId, LocalDate.now());
        issueRecords.add(record);
        return record;

    }

    public IssueRecord returnBook( String isbn) throws BookNotFoundException {
        IssueRecord activeRecord = null ;
        for ( IssueRecord record : issueRecords){
            if( record.getIsbn().equalsIgnoreCase(isbn) && !record.isReturned()){
                activeRecord = record ;
                break;
            }
        }
        if (activeRecord == null){
            throw new BookNotFoundException("No active issue record found for ISBN: " +isbn);
        }

        activeRecord.setReturnDate(LocalDate.now());

        for ( Book book : books){
            if ( book.getIsbn().equalsIgnoreCase(isbn)){
                book.setAvailable(true);
                break;
            }
        }
        return activeRecord;
    }

    public void viewIssuedBooks(){
        ArrayList<IssueRecord> activeRecords = new ArrayList<>();
        for ( IssueRecord record : issueRecords){
            if( ! record.isReturned()){
                activeRecords.add(record);
            }
        }
        if ( activeRecords.isEmpty()){
            System.out.println("No books are currently issued.");
            return;
        }

        System.out.println("---- Currently Issued Books (" + activeRecords.size() + ") ----");
        for( IssueRecord record : activeRecords){
            System.out.println(record);
        }
    }

    public void viewAllIssueRecords(){
        if ( issueRecords.isEmpty()){
            System.out.println("No issue record yet.");
            return;
        }

        System.out.println(" ---- Full Issue History (" + issueRecords.size() + ") ---- ");
        for ( IssueRecord record : issueRecords){
            System.out.println(record);
        }
    }
}
