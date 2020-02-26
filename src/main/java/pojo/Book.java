package pojo;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class Book implements Serializable{
	private static final long serialVersionUID = 1L;
	private String bid;
	
	public Book(String bid) {
		this.bid = bid;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
