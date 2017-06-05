package com.onnisoft.wahoo.api.resource;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codahale.metrics.annotation.Timed;
import com.onnisoft.api.utils.security.dto.GenericResponseDTO;
import com.onnisoft.wahoo.api.request.BidCreationRequestDTO;
import com.onnisoft.wahoo.api.request.ProductDTO;
import com.onnisoft.wahoo.api.services.ProductService;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.dao.DaoHibernate;
import com.onnisoft.wahoo.model.document.Bid;
import com.onnisoft.wahoo.model.document.Comment;
import com.onnisoft.wahoo.model.document.Product;
import com.onnisoft.wahoo.model.document.Subscriber;
import com.onnisoft.wahoo.model.document.enums.SubscriberRoleEnum;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@Service
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Products API")
public class ProductApi extends AbstractApi {
	
	@Autowired
	private Dao<Product> productDao;
	
	@Autowired
	private Dao<Comment> commentDao;
	
	@Autowired
	private DaoHibernate<Bid> bidDao;

	@Autowired
	private ProductService productService;
	
	@GET
	@Path("/getAllProducts")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public @ResponseBody GenericResponseDTO<List<Product>> getAllProducts(@Context HttpServletRequest headers) {
		
		Subscriber subscriber = super.retrieveUserFromToken(headers);

		if (subscriber == null) {
			logger.warn("The subscriber couldn't be retrieved!");
			return GenericResponseDTO.createFailed("The subscriber couldn't be retrieved!");
		}
		
		List<Product> products = this.productDao.retrieveList(null);
		
		if(CollectionUtils.isEmpty(products)){
			logger.warn("There are no products to retrieve!");
			return GenericResponseDTO.createFailed("There are no products to retrieve!");
		}
		
		return GenericResponseDTO.createSuccess(products);
	}
	
	@POST
	@Path("/product")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public @ResponseBody GenericResponseDTO<Product> postProduct(@Context HttpServletRequest headers, ProductDTO productDTO){
		Subscriber subscriber = super.retrieveUserFromToken(headers);

		if (subscriber == null) {
			logger.warn("The subscriber couldn't be retrieved!");
			return GenericResponseDTO.createFailed("The subscriber couldn't be retrieved!");
		}
		
		if(!subscriber.getRole().equals(SubscriberRoleEnum.MASTER_ADMIN)){
			logger.warn("The subscriber is not an admin, subscriber:"+subscriber.getId());
			return GenericResponseDTO.createFailed("The subscriber doesn't have access to this section");
		}
		
		// TODO validate the product first
		
		
		Product product = this.productDao.create(new Product.Builder().name(productDTO.getName()).description(productDTO.getDescription()).imageURL(productDTO.getImageURL()).bidEndDate(productDTO.getBidEndDate()).startingPrice(productDTO.getStartingPrice()).highestPrice(productDTO.getHighestPrice()).quantity(productDTO.getQuantity()).toCreate().build());
		
		if(product == null){
			logger.warn("The product couldn't be saved!");
			return GenericResponseDTO.createFailed("The product couldn't be saved!");
		}
		
		return GenericResponseDTO.createSuccess(product);
	}
	
	@DELETE
	@Path("/product/id/{idProduct}")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public @ResponseBody GenericResponseDTO<Product> deleteProduct(@Context HttpServletRequest headers, @PathParam("idProduct")String idProduct){
		Subscriber subscriber = super.retrieveUserFromToken(headers);

		if (subscriber == null) {
			logger.warn("The subscriber couldn't be retrieved!");
			return GenericResponseDTO.createFailed("The subscriber couldn't be retrieved!");
		}
		
		Product product = this.productDao.retrieveById(idProduct);
		
		if(product == null){
			logger.warn("There is no product with the id:"+idProduct);
			return GenericResponseDTO.createFailed("There is no product with the id:"+idProduct);
		}
		
		boolean deleted = this.productDao.delete(new Product.Builder().id(idProduct).build());
		
		if(!deleted){
			logger.warn("The product couldn't be deleted!");
			return GenericResponseDTO.createFailed("The product couldn't be deleted!");
		}
		
		return GenericResponseDTO.createSuccess(product);
	}
	
	@POST
	@Path("/comment")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public @ResponseBody GenericResponseDTO<Comment> postComment(@Context HttpServletRequest headers, @QueryParam("content")String content, @QueryParam("productId") String productId){
		Subscriber subscriber = super.retrieveUserFromToken(headers);

		if (subscriber == null) {
			logger.warn("The subscriber couldn't be retrieved!");
			return GenericResponseDTO.createFailed("The subscriber couldn't be retrieved!");
		}
		
		if(StringUtils.isEmpty(content)){
			logger.warn("The content is null or empty!");
			return GenericResponseDTO.createFailed("The content is null or empty!");
		}
		
		if(StringUtils.isEmpty(productId)){
			logger.warn("The product id is null or empty!");
			return GenericResponseDTO.createFailed("The product id is null or empty!");
		}
		
		Product product = this.productDao.retrieveById(productId);
		
		if(product == null){
			logger.warn("There is no product with the id:"+productId);
			return GenericResponseDTO.createFailed("There is no product with the id:"+productId);
		}
		
		Comment comment = this.commentDao.create(new Comment.Builder().content(content).subscriber(subscriber).toCreate().build());

		boolean updated = this.productDao.update(new Product.Builder().id(productId).comments(Collections.singletonList(comment)).build());
		if(!updated){
			logger.warn("The product couldn't be updated!");
			return GenericResponseDTO.createFailed("The product couldn't be updated!");
		}
		
		return GenericResponseDTO.createSuccess(comment);
	}
	
	@GET
	@Path("/product/id/{productId}")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
		@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
		@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public @ResponseBody GenericResponseDTO<Product> getProductById(@Context HttpServletRequest headers, @PathParam("productId")String productId) {
		Subscriber subscriber = this.retrieveUserFromToken(headers);
		
		if (subscriber == null) {
			logger.warn("The subscriber couldn't be retrieved!");
			return GenericResponseDTO.createFailed("The subscriber couldn't be retrieved!");
		}
		
		Product product = this.productDao.retrieveById(productId);
		
		if(product == null){
			logger.warn("There is no product with id:"+productId);
			return GenericResponseDTO.createFailed("There is no product with id:"+productId);
		}
		
		return GenericResponseDTO.createSuccess(product);
	}
	
	@POST
	@Path("/bid")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
		@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
		@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public @ResponseBody GenericResponseDTO<Bid> postBidForProduct(@Context HttpServletRequest headers, BidCreationRequestDTO bidDTO) {
		Subscriber subscriber = this.retrieveUserFromToken(headers);
		
		if (subscriber == null) {
			logger.warn("The subscriber couldn't be retrieved!");
			return GenericResponseDTO.createFailed("The subscriber couldn't be retrieved!");
		}
		
		if(StringUtils.isEmpty(bidDTO.getIdProduct())){
			logger.warn("The product id must not be empty!");
			return GenericResponseDTO.createFailed("The product id must not be empty!");
		}
		
		if(bidDTO.getBidValue() == 0){
			logger.warn("The bid value must be greater then 0!");
			return GenericResponseDTO.createFailed("The bid value must be greater then 0!");
		}
		
		Product product = this.productDao.retrieveById(bidDTO.getIdProduct());
		
		if(product == null){
			logger.warn("There is no product with the provided id:"+bidDTO.getIdProduct());
			return GenericResponseDTO.createFailed("There is no product with the provided id:"+bidDTO.getIdProduct());
		}
		
		if(product.getHighestPrice() > bidDTO.getBidValue()){
			logger.warn("The bid value is lower then the products highest price!");
			return GenericResponseDTO.createFailed("The bid value is lower then the products highest price!");
		}
		
		// create transaction
		try {
			productService.createBidTransaction(bidDTO, subscriber);
			boolean updated = this.productDao.update(new Product.Builder().id(product.getId()).highestPrice(bidDTO.getBidValue()).build());
			if(!updated){
				logger.warn("The product couldn't be updated with the new value for highest bid!");
				return GenericResponseDTO.createFailed("The product couldn't be updated with the new value for highest bid!");
			}
		} catch (Exception e) {
			logger.warn("Error for subscriber:"+subscriber.getId()+" when bidding for product:"+bidDTO.getIdProduct()+" error:"+e.getMessage());
			return GenericResponseDTO.createFailed(e.getMessage());
		}	
		
		List<Bid> bidList = this.bidDao.findByProps(new Bid.Builder().idProduct(bidDTO.getIdProduct()).idSubscriber(subscriber.getId()).bidValue(bidDTO.getBidValue()).build());
		
		if(CollectionUtils.isEmpty(bidList)){
			logger.warn("There are no bids with the provided credentials!");
			return GenericResponseDTO.createFailed(("There was a problem with the transaction try again."));
		}
		
		return GenericResponseDTO.createSuccess(bidList.get(0));
	}
}
