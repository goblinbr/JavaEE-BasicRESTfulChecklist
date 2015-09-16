/*
 * The MIT License
 *
 * Copyright 2015 Rodrigo de Bona Sartor.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.sartor.javaee.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Rodrigo de Bona Sartor
 * @param <T> DB Entity class
 * @param <P> Primary key type
 */
public abstract class BaseDao<T, P> {

  @PersistenceContext( unitName = "SAMPLE_PU" ) // Unit name from persistence.xml
  private EntityManager em;

  private final Class<T> entityClass;

  public BaseDao( Class<T> entityClass ) {
    this.entityClass = entityClass;
  }

  public void create( T entity ) {
    this.em.persist( entity );
  }

  public void edit( T entity ) {
    this.em.merge( entity );
  }

  public void remove( T entity ) {
    this.em.remove( this.em.merge( entity ) );
  }

  /**
   *
   * @param id
   * @return If id exists return an instance of T, else return null
   */
  public T find( P id ) {
    return this.em.find( this.entityClass, id );
  }

  /**
   *
   * @return List with all the results, if there is no result returns an empty list
   */
  public List<T> findAll() {
    return findAll( 0, 0 );
  }

  /**
   *
   * @param qtyByPage How many rows it will fetch, if qtyByPage is equals or less than zero it will fetch all rows
   * @param page Page number of the fetch starting from one, if page is equals or less than zero it will fetch all rows
   * @return List with all the results, if there is no result returns an empty list
   */
  public List<T> findAll( int qtyByPage, int page ) {
    CriteriaBuilder cb = this.em.getCriteriaBuilder();
    CriteriaQuery cq = cb.createQuery();
    Root rootSelect = cq.from( this.entityClass );
    cq.select( rootSelect );
    return findAll( cq, qtyByPage, page );
  }

  /**
   *
   * @param cq CriteriaQuery
   * @param qtyByPage How many rows it will fetch, if qtyByPage is equals or less than zero it will fetch all rows
   * @param page Page number of the fetch starting from one, if page is equals or less than zero it will fetch all rows
   * @return List with all the results, if there is no result returns an empty list
   */
  protected List<T> findAll( CriteriaQuery cq, int qtyByPage, int page ) {
    TypedQuery query = this.em.createQuery( cq );
    if ( qtyByPage > 0 && page > 0 ) {
      int first = (qtyByPage * page) - qtyByPage;
      query.setFirstResult( first );
      query.setMaxResults( qtyByPage );
    }
    return query.getResultList();
  }

  /**
   *
   * @return Row count
   */
  public int count() {
    CriteriaBuilder cb = this.em.getCriteriaBuilder();
    CriteriaQuery cq = cb.createQuery();
    Root rootSelect = cq.from( this.entityClass );
    cq.select( cb.count( rootSelect ) );
    javax.persistence.Query q = this.em.createQuery( cq );
    return ((Long) q.getSingleResult()).intValue();
  }

}
