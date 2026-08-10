package com.library.model;
import java.time.LocalDate;

public class IssueRecord {
    private String isbn;
    private String memberId;
    private LocalDate issueDate;
    private LocalDate returnDate;


    public IssueRecord (String isbn, String memberId, LocalDate issueDate){
        this.isbn = isbn;
        this.memberId = memberId;
        this.issueDate = issueDate;
        this.returnDate = null;
    }

    public String getIsbn(){
        return isbn;
    }

    public String getMemberId(){
        return memberId;
    }

    public LocalDate getIssueDate(){
        return issueDate;
    }
    public LocalDate getReturnDate(){
        return returnDate;
    }
    public void setReturnDate(LocalDate returnDate){
        this.returnDate = returnDate;
    }
    public boolean isReturned(){
        return returnDate != null;
    }

    @Override

    public String toString(){
        String status = isReturned() ? "Returned on " + returnDate : "Not yet returned";
        return "ISBN: " + isbn + 
               " | Member ID: " + memberId + 
               " | Issued: " + issueDate + 
               " | " + status;
    }
}
