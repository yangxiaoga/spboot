package config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
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
	
	/**
	 * 从redis缓存中查找，如果有则取出，没有则从数据库中查询
	 */
    public Book selectByPrimaryKey(String bid) {
    	
    	ValueOperations<String, Object> operations = template.opsForValue();
    	String key = "book"+bid;
    	
    	boolean hasKey = template.hasKey(key);
    	 
        if (hasKey) {
            Book book = (Book) operations.get(key);
            System.out.println("从缓存中获得数据："+book.getBid());
            System.out.println("------------------------------------");
            return book;
        }else {
        	Book book = dao.selectByPrimaryKey(bid);
            System.out.println("查询数据库获得数据："+book.getBid());
            System.out.println("------------------------------------");
 
            // 写入缓存
            operations.set(key, book, 5, TimeUnit.HOURS);
            return book;
        }
    }

    public void clear() {
        System.out.println("什么东西都不要写");
    }

}
