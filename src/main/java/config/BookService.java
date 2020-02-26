package config;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import pojo.Book;

public interface BookService {
	
    @Cacheable(value = "my-redis-cache2",key = "'book'+#bid")
    Book selectByPrimaryKey(String bid);
    
    @CacheEvict(value = "my-redis-cache2",allEntries = true)
    void clear();
}
