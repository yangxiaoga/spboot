package config;

import pojo.Book;

public interface BookService {
	
    Book selectByPrimaryKey(String bid);
    
    void clear();
}
