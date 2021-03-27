package com.example.lenovo.jiyiji.AddAccount;

public class BookBean {
    private int id;
    private String bookname;
    private String privacy;

    public String getBookname()
    {
        return bookname;
    }

    public String getPrivacy()
    {
        return privacy;
    }

    public int getId()
    {
        return id;
    }

    public BookBean(String bookname, String privacy)
    {
        this.bookname = bookname;
        this.privacy = privacy;
    }

    public BookBean(int id, String bookname, String privacy)
    {
        this.id = id;
        this.bookname = bookname;
        this.privacy = privacy;
    }

}
