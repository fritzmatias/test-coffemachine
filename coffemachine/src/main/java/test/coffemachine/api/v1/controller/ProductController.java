package test.coffemachine.api.v1.controller;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;
import test.coffemachine.api.v1.dto.ProductAssociationRequestDto;
import test.coffemachine.api.v1.dto.ProductRequestDto;
import test.coffemachine.api.v1.dto.ProductResponseDto;
import test.coffemachine.business.model.Product;
import test.coffemachine.business.model.ProductAssociation;
import test.coffemachine.service.ProductService;

@Slf4j
@RestController
public class ProductController {
	private ProductService service;
	private ModelMapper modelMapper;
	
	@Autowired
	public ProductController(ProductService service,ModelMapper modelMapper) {
		super();
		this.service=service;
		this.modelMapper=modelMapper;
	}

	@PostMapping(path = "/product", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductResponseDto postProduct(@RequestBody @Validated ProductRequestDto req) {
		Product o = service.createProduct(req);
		ProductResponseDto response=convertToResponseDto(o);
		return response;
	}
	
	@PostMapping(path = "/productAssociations", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductAssociation postProductAssociation(@RequestBody @Validated ProductAssociationRequestDto req) {
		ProductAssociation o = service.updateProductAssociation(req);
		return o;
	}

	@GetMapping(path = "/product/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductAssociation getProduct(@PathVariable @Validated Long id) {
		log.info("requesting /product/{}",id);
		ProductAssociation o = service.getProductAssociation(id);
		if(o == null) {
			log.error("request /product/{}",id);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		log.info("answering /product/{}",id);
		return o;
	}
	
	@PostMapping(path = "/setupProducts", consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Product> getProducts(@RequestBody @Validated Collection<ProductRequestDto> request) {
		List<Product> response = request.stream().map(r->service.createProduct(r)).collect(Collectors.toList());
		return response;
	}
	
	@GetMapping(path = "/productList", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<ProductResponseDto> getProducts() {
		Collection<ProductResponseDto> o = service.getProductList();
		if(o == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		return o;
	}
	
	@PostMapping(path = "/calculatePurchase", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BigDecimal postCalculatePurchase(@RequestBody @Validated Collection<ProductResponseDto> items) {
		BigDecimal o = service.calculatePurchaseAmount(items);
		if(o == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		return o;
	}
	
	
	private ProductResponseDto convertToResponseDto(Product product) {
		ProductResponseDto postDto = modelMapper.map(product, ProductResponseDto.class);
		return postDto;
	}


}
