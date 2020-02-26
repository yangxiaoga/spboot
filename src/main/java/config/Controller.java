package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pojo.Book;

@RestController
@ResponseBody
public class Controller {
	@Autowired
	BookService bookService;
	
	@RequestMapping("/book")
	public Book getBook() {
		Book book1 = bookService.selectByPrimaryKey("a12334fges");
		Book book2 = bookService.selectByPrimaryKey("a12334fges");
		Book book3 = bookService.selectByPrimaryKey("a12334fges");
		System.out.println(book2==book3);
		
		return book1;
	}

}
