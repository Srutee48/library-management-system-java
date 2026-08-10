package com.library.util;
import com.library.model.Book;
import com.library.model.Member;
import com.library.model.IssueRecord;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class FileManager {
    //------------ BOOKS ------------

    public void saveBooks( ArrayList<Book> books, String filePath){
        try ( BufferedWriter writer = new BufferedWriter ( new FileWriter(filePath))){
            for( Book book : books){
                String line = book.getIsbn() + "|" + book.getTitle() + "|" + book.getAuthor() + "|" + book.isAvailable();

                writer.write(line);
                writer.newLine();
            }
        } catch ( IOException e){
            System.out.println("Error saving books: " + e.getMessage());
        }
    }

    public ArrayList<Book> loadBooks(String filePath){
        ArrayList<Book> books = new ArrayList<>();
        File file = new File ( filePath);
        if( !file.exists()){
            return books;
        }

        try ( BufferedReader reader = new BufferedReader ( new FileReader ( filePath))){
            String line;
            while((line  = reader.readLine()) != null){
                if( line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                if ( parts.length != 4) continue;

                Book book = new Book ( parts[0], parts[1], parts[2]);
                book.setAvailable((Boolean.parseBoolean(parts[3])));
                books.add(book);
            }
        } catch ( IOException e){
            System.out.println("Error loading books: " + e.getMessage());
        }
        return books;
    }

    //------------ MEMBERS ------------

    public void saveMembers(ArrayList<Member> members,String filePath){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            for ( Member member : members){
                String line = member.getMemberId() + "|" + member.getName() + "|" + member.getContactNumber();
                writer.write(line);
                writer.newLine();

            }
        } catch ( IOException e){
            System.out.println("Error saving members: " + e.getMessage());
        }
    }

    public ArrayList<Member> loadMembers(String filePath){
        ArrayList<Member> members =new ArrayList<>();
        File file = new File ( filePath);
        if ( !file.exists()){
            return members;
        }

        try ( BufferedReader reader = new BufferedReader ( new FileReader (filePath))){
            String line;
            while ( ( line = reader.readLine()) != null){
                if(line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                if ( parts.length != 3) continue;

                members.add( new Member(parts[0], parts[1],parts[2]));
            }
        } catch ( IOException e){
            System.out.println("Error loading members: " + e.getMessage());
        }
        return members;
    }

    //------------ ISSUE RECORDS ------------
    public void saveIssueRecords(ArrayList<IssueRecord>records, String filePath){
        try( BufferedWriter writer = new BufferedWriter( new FileWriter( filePath))){
            for(IssueRecord record : records){
                String returnPart = record.isReturned() ? record.getReturnDate().toString() : "NULL";
                String line = record.getIsbn() + "|" + record.getMemberId() + "|" + record.getIssueDate() + "|" + returnPart;

                writer.write(line);
                writer.newLine();
            }
        } catch ( IOException e){
            System.out.println(" Error saving issue records: " + e.getMessage());
        }
    }

    public ArrayList<IssueRecord> loadIssueRecords( String filePath){
        ArrayList<IssueRecord> records = new ArrayList<>();
        File file = new File( filePath);
        if( !file.exists()){
            return records;
        }

        try ( BufferedReader reader = new BufferedReader( new FileReader (filePath))){
            String line;
            while( (line = reader.readLine()) != null){
                if(line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                if(parts.length != 4)continue;

                IssueRecord record = new IssueRecord( parts[0], parts[1], LocalDate.parse(parts[2]));
                if ( ! parts[3].equals("NULL")){
                    record.setReturnDate(LocalDate.parse(parts[3]));
                }
                records.add(record);
            }
        } catch ( IOException e){
            System.out.println("Error loading issue records: " + e.getMessage());
        }
        return records;
    }
    
}
