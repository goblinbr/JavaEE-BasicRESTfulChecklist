/*
 * The MIT License
 *
 * Copyright 2015 Kugel - Rodrigo de Bona Sartor.
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
package com.sartor.javaee.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rodrigo de Bona Sartor
 */
@Entity
@Table( name = "ITEM" )
@XmlRootElement
public class Item implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  @Basic( optional = false )
  @Column( name = "ID" )
  private Long id;

  @Basic( optional = false )
  @NotNull
  @Size( min = 10, max = 200 )
  @Column( name = "DESCRIPTION" )
  private String description;

  @Basic( optional = false )
  @NotNull
  @Column( name = "IS_CHECKED" )
  private Boolean isChecked;

  public Item() {
    this( "", false );
  }

  public Item( String description, Boolean isChecked ) {
    this.description = description;
    this.isChecked = isChecked;
  }

  public Long getId() {
    return id;
  }

  public void setId( Long id ) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription( String description ) {
    this.description = description;
  }

  public Boolean getIsChecked() {
    return isChecked;
  }

  public void setIsChecked( Boolean isChecked ) {
    this.isChecked = isChecked;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals( Object object ) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if ( !(object instanceof Item) ) {
      return false;
    }
    Item other = (Item) object;
    if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals( other.id )) ) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.sartor.javaee.entities.Item[ id=" + id + " ]";
  }

}
