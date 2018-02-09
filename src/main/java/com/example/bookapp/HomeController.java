package com.example.bookapp;

import com.example.bookapp.Repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    BookRepository bookrepo;


    @RequestMapping("/")
    public String displayhomeindex(Model model){

        return "index";
    }
    @RequestMapping("/home")
    public String displayhomepage(Model model){

        return "home";
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
        return "index";
    }
    @RequestMapping("/booklist")
    public String bookListDisplay(Model model){
        model.addAttribute("books", bookrepo.findAll());
        return "booklist";
    }


    @GetMapping ("/borrow/{id}")
    public String borrowBook(@PathVariable("id") long id){

        Book tobeborrowed=bookrepo.findOne(id);
        tobeborrowed.setStatus(false);
        bookrepo.save(tobeborrowed);

        return "redirect:/borrowlist";
    }


    @RequestMapping("/borrowlist")
    public String BorrowListDisplay(Model model){

        model.addAttribute("books", bookrepo.findAll());
        return "borrowlist";
    }

    @RequestMapping("/borrow")
    public String BorrowList(Model model){

        model.addAttribute("books", bookrepo.findByStatusTrue());

        return "borrowlist";
    }


    @GetMapping ("/return/{id}")
    public String returnBook(@PathVariable("id") long id){

        Book tobereturned=bookrepo.findOne(id);
        tobereturned.setStatus(true);
        bookrepo.save(tobereturned);
        return "redirect:/returnlist";
    }
    @RequestMapping("/returnlist")
    public String ReturnListDisplay(Model model){
        model.addAttribute("books", bookrepo.findAll());
        return "returnlist";
    }

    @RequestMapping("/return")
    public String ReturnList(Model model){
        model.addAttribute("books", bookrepo.findByStatusFalse());
        return "returnlist";
    }




}
