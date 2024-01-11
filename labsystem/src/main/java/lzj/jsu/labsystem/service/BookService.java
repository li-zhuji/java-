package lzj.jsu.labsystem.service;

import lzj.jsu.labsystem.pojo.Book;

import java.util.List;

public interface BookService {
    public void addBookList(Book book);

    List<Book> getBookList(Book book);

    void deleteBookList(Integer id);

    void updateBookList(Book book);
}
