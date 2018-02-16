/**
 * 
 */
package com.cs.je.auth.service;

import java.io.Serializable;
import java.util.List;

/**
 * @author sawai
 *
 */

public interface BaseService<E, K extends Serializable> {
	
	public E create (E entity) throws Exception;
	
	public E getById(K key);
	
	public E update(K key, E entity) throws Exception;
	
	public void delete(K key);
	
	public List<E> getAll();

}









