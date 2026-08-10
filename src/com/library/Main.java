package com.library;

import com.library.model.Book;
import com.library.model.Member;
import com.library.model.IssueRecord;
import com.library.service.Library;
import com.library.util.FileManager;
import com.library.util.InputHelper;
import com.library.exception.DuplicateISBNException;
import com.library.exception.BookNotFoundException;
import com.library.exception.BookUnavailableException;
import com.library.exception.MemberNotFoundException;

import java.util.ArrayList;

public class Main {
           
    private static final String BOOKS_FILE = "data/books.txt";
    private static final String MEMBERS_FILE = "data/members.txt";
    private static final String ISSUED_FILE = "data/issued.txt";

    private static Library library;
    private static FileManager fileManager;
    private static InputHelper input;
    public static void main(String[] args) {
        library = new Library();
        fileManager = new FileManager();
        input = new InputHelper();

        loadAllData();


        System.out.println("=== Welcome to the Library Management System ===");

        while ( true ){
            printMenu();
            int choice = input.getValidInt("Enter your choice: ");

            switch( choice ){
                case 1 -> handleAddBook();
                case 2 -> library.viewAllBooks();
                case 3 -> handleSearchBooks();
                case 4 -> handleAddMember();
                case 5 -> library.viewAllMembers();
                case 6 -> handleIssueBook();
                case 7 -> handleReturnBook();
                case 8 -> library.viewIssuedBooks();
                case 9 -> library.viewAllIssueRecords();
                case 10 -> {
                    saveAllData();
                    System.out.println("Data saved. Goodbye!");
                    input.close();
                    return;                
                }
                default -> System.out.println("Invalid choice. Please select a number from the menu.");
            }
        }
    } 
    private static void printMenu(){
        System.out.println("\n ------------- MENU ------------");
        System.out.println("1. Add a book");
        System.out.println("2. View all books");
        System.out.println("3. Search books");
        System.out.println("4. Add a member");
        System.out.println("5. View all members");
        System.out.println("6. Issue a book");
        System.out.println("7. Return a book");
        System.out.println("8. View currently issued books");
        System.out.println("9. View full issue history");
        System.out.println("10. Exit");
        System.out.println("-----------------------------------");
    }

    private static void handleAddBook(){
        String isbn = input.getNonEmptyString("Enter ISBN: ");
        String title = input.getNonEmptyString("Enter title: ");
        String author = input.getNonEmptyString("Enter author: ");

        try{
            library.addBook(new Book(isbn, title, author));
            System.out.println("Book added successfully.");
        } catch ( DuplicateISBNException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void handleSearchBooks(){
        String keyword = input.getNonEmptyString("Enter title, author, or ISBN to search: ");
        ArrayList<Book> results = library.searchBooks(keyword);


        if( results.isEmpty()){
            System.out.println("No matching books found");
        } else {
            System.out.println("---- Search Results (" + results.size() + ")----");
            for ( Book book : results){
                System.out.println(book);
            }
        }
    }

    private static void handleAddMember(){
        String name = input.getNonEmptyString("Enter member name: ");
        String contact = input.getNonEmptyString("Enter contact number: ");

        Member newMember = library.addMember(name,contact);
        System.out.println("Member added successfully: " + newMember);
    }

    private static void handleIssueBook(){
        String isbn = input.getNonEmptyString("Enter book ISBN to issue: ");
        String memberId = input.getNonEmptyString("Enter member id: ");

        try{
            IssueRecord record = library.issueBook(isbn, memberId);
            System.out.println("Book issued successfully: " + record);
        } catch (BookNotFoundException | BookUnavailableException | MemberNotFoundException  e){
            System.out.println("Error: " +e.getMessage());
        }
    }

    private static void handleReturnBook(){
        String isbn = input.getNonEmptyString("Enter book ISBN to return: ");

        try{
            IssueRecord record = library.returnBook(isbn);
            System.out.println("Book returned successfully: " + record);
        } catch ( BookNotFoundException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void loadAllData(){
        ArrayList<Book> loadedBooks = fileManager.loadBooks(BOOKS_FILE);
        ArrayList<Member> loadedMembers = fileManager.loadMembers(MEMBERS_FILE);
        ArrayList<IssueRecord> loadedRecords = fileManager.loadIssueRecords(ISSUED_FILE);

        library.loadData ( loadedBooks, loadedMembers, loadedRecords);

    }
        
    private static void saveAllData(){
        fileManager.saveBooks(library.getBooks(), BOOKS_FILE);
        fileManager.saveMembers(library.getMembers(), MEMBERS_FILE);
        fileManager.saveIssueRecords(library.getIssueRecords(), ISSUED_FILE);
    }  
}  

