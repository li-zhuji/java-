package lzj.jsu.labsystem.controller;

import lzj.jsu.labsystem.pojo.Book;
import lzj.jsu.labsystem.service.BookService;
import lzj.jsu.labsystem.utils.ResultOBJ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adminapi/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getBookList(Book book){
        return bookService.getBookList(book);
    }

    @PostMapping
    public ResultOBJ addBookList(@RequestBody  Book book){
        try {
            bookService.addBookList(book);
            return ResultOBJ.ADD_SUCCESS;
        } catch (Exception e) {
//            throw new RuntimeException(e);
            return ResultOBJ.ADD_ERROR;
        }
    }

    @PostMapping("/select")
    //这里为了区分不同的post请求
    public List<Book> getSelectBookList(@RequestBody  Book book){
        return bookService.getBookList(book);
    }

    @PutMapping(value = "/{id}")
    public ResultOBJ updateBookList(@PathVariable Integer id, @RequestBody Book book){
        try {
            book.setId(id);
            bookService.updateBookList(book);
            return ResultOBJ.UPDATE_SUCCESS;
        } catch (Exception e) {
//            throw new RuntimeException(e);
            return ResultOBJ.UPDATE_ERROR;
        }
    }

    @DeleteMapping(value = "/{id}")
    //动态路由
    public ResultOBJ deleteBookList(@PathVariable Integer id){
        try {
            bookService.deleteBookList(id);
            return ResultOBJ.DELETE_SUCCESS;
        } catch (Exception e) {
//            throw new RuntimeException(e);
            return ResultOBJ.DELETE_ERROR;
        }
    }
}
