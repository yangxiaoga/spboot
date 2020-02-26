package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import pojo.Book;

/**
 * @author Administrator
 *
 */
@Service
public class BookServiceImpl implements BookService{

	@Autowired
	DAO dao;
	
	@Autowired
	RedisTemplate<String, Object>  template;
	
    public Book selectByPrimaryKey(String bid) {
    	Book b =  dao.selectByPrimaryKey(bid);
    	template.opsForValue().set("book", b);
    	return b;
    }

    public void clear() {
        System.out.println("什么东西都不要写");
    }

}
