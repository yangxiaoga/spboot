package config;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import pojo.Book;

/**
 * @author Administrator
 *
 */
@Mapper
public interface DAO {
    Book selectByPrimaryKey(@Param("bid") String bid);
}
