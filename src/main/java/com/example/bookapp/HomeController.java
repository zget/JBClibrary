package com.example.bookapp;

import com.example.bookapp.Repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Field;

@Controller
public class HomeController {

    @Autowired
    BookRepository bookrepo;

    @RequestMapping("/")
    public String displayhomepage(Model model){


        return "homepage";
    }


    @GetMapping("/addbook")
    public String loadBookForm(Model model){

        model.addAttribute("book", new Book());
        return "bookform";
    }

    @PostMapping("/processbook")

    public String processBookForm(@Valid @ModelAttribute("book") Book book, BindingResult result){
        if(result.hasErrors())
            return "bookform";
        bookrepo.save(book);
        //bookrepo.findOne(book.getId()).setStatus(true);
        return "redirect:/booklist";
    }
    @RequestMapping("/booklist")
    public String bookListDisplay(Model model){
        model.addAttribute("books", bookrepo.findAll());
        return "booklist";
    }
    @RequestMapping("/borrowlist")
    public String BorrowListDisplay(Model model){
        model.addAttribute("books", bookrepo.findAll());
        return "borrowlist";
    }

    @GetMapping ("/borrow/{id}")
    public String borrowBook(@PathVariable("id") long id){

        Book tobeborrowed=bookrepo.findOne(id);
        tobeborrowed.setStatusF(false);
        bookrepo.save(tobeborrowed);
        return "redirect:/borrowlist";
    }

    @GetMapping ("/return/{id}")
    public String returnBook(@PathVariable("id") long id){

        Book tobereturned=bookrepo.findOne(id);
        tobereturned.setStatusF(true);
        bookrepo.save(tobereturned);
        return "redirect:/returnlist";
    }





}
