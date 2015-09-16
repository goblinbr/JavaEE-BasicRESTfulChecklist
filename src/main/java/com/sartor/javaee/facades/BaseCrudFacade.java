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
package com.sartor.javaee.facades;

import com.sartor.javaee.daos.BaseDao;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Rodrigo de Bona Sartor
 * @param <T> DB Entity class
 * @param <P> Primary key type
 */
public abstract class BaseCrudFacade<T, P> {

  protected abstract BaseDao<T, P> getDao();

  public List<T> findAll() {
    return getDao().findAll();
  }

  public List<T> findAll( int qtyByPage, int page ) {
    return getDao().findAll( qtyByPage, page );
  }

  public void edit( T entity ) {
    try {
      getDao().edit( entity );
    }
    catch ( ConstraintViolationException ex ) {
      transformToWebApplicationException( ex );
    }
  }

  public void remove( T entity ) {
    getDao().remove( entity );
  }

  public T find( P id ) {
    return getDao().find( id );
  }

  public void create( T entity ) {
    try {
      getDao().create( entity );
    }
    catch ( ConstraintViolationException ex ) {
      transformToWebApplicationException( ex );
    }
  }

  public int count() {
    return getDao().count();
  }

  private void transformToWebApplicationException( ConstraintViolationException ex ) {
    String msg = "";
    for ( ConstraintViolation constraintViolation : ex.getConstraintViolations() ) {
      String atrib = constraintViolation.getPropertyPath().toString();
      msg += atrib + " " + constraintViolation.getMessage() + ";";
    }
    throw new WebApplicationException( msg, ex, Response.status( Response.Status.NOT_ACCEPTABLE ).entity( msg ).build() );
  }
}
