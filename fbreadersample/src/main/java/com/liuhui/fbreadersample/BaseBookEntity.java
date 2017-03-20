package com.liuhui.fbreadersample;

import java.io.Serializable;

/**
 *
 * 书籍类
 *
 * 作者：ahq on 16/12/1 23:01
 * 邮箱：anhuiqing888@163.com
 */
public class BaseBookEntity implements Serializable {
    public static final long serialVersionUID = 1L;
    private long book_id;
    private String book_name;
    private String author;
    private String book_path;
    private int book_type;
    public long getBook_id() {
        return book_id;
    }

    public void setBook_id(long book_id) {
        this.book_id = book_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBook_path() {
        return book_path;
    }

    public void setBook_path(String book_path) {
        this.book_path = book_path;
    }

    public int getBook_type() {
        return book_type;
    }

    public void setBook_type(int book_type) {
        this.book_type = book_type;
    }
}
