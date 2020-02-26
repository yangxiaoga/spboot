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
		Book book = null;
		for (int i=0;i <20;i++) {
			book = bookService.selectByPrimaryKey("a12334fges");
		}
		return book;
	}

}
