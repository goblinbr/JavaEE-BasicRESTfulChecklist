package com.sartor.javaee.facades;

import com.sartor.javaee.daos.BaseDao;
import com.sartor.javaee.daos.ItemDao;
import com.sartor.javaee.entities.Item;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author Kugel Soft Informática LTDA - Rodrigo de Bona Sartor
 */
@Stateless
@Path( "rest/item" )
public class ItemFacade extends BaseCrudFacade<Item, Long> {

  @Inject
  private ItemDao dao;

  @Override
  protected BaseDao<Item, Long> getDao() {
    return dao;
  }

  @POST
  @Override
  @Consumes( { "application/xml", "application/json" } )
  public void create( Item entity ) {
    super.create( entity );
  }

  @PUT
  @Path( "{id}" )
  @Consumes( { "application/xml", "application/json" } )
  public void edit( @PathParam( "id" ) Long id, Item entity ) {
    super.edit( entity );
  }

  @DELETE
  @Path( "{id}" )
  public void remove( @PathParam( "id" ) Long id ) {
    super.remove( super.find( id ) );
  }

  @GET
  @Path( "{id}" )
  @Produces( { "application/xml", "application/json" } )
  @Override
  public Item find( @PathParam( "id" ) Long id ) {
    return super.find( id );
  }

  @GET
  @Override
  @Produces( { "application/xml", "application/json" } )
  public List<Item> findAll() {
    return super.findAll();
  }

  @GET
  @Path( "{qtyByPage}/{page}" )
  @Produces( { "application/xml", "application/json" } )
  public List<Item> findAll( @PathParam( "qtyByPage" ) Integer qtyByPage, @PathParam( "page" ) Integer page ) {
    return super.findAll( qtyByPage, page );
  }

  @GET
  @Path( "count" )
  @Produces( "text/plain" )
  public String countREST() {
    return String.valueOf( super.count() );
  }
}
