package test.coffemachine.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import test.coffemachine.api.v1.dto.ProductAssociationRequestDto;
import test.coffemachine.api.v1.dto.ProductAssociationResponseDto;
import test.coffemachine.api.v1.dto.ProductAssociationResponseDto.ProductAssociationRequestDtoDBuilder;
import test.coffemachine.api.v1.dto.ProductResponseDto;
import test.coffemachine.business.model.Product;
import test.coffemachine.business.model.ProductAssociation;
import test.coffemachine.business.model.ProductAttributes;
import test.coffemachine.model.entities.ProductEntity;
import test.coffemachine.repositories.ProductRepository;

@Service
@Slf4j
public class ProductService {

	private ProductRepository repository;
	private ModelMapper modelMapper;

	@Autowired
	public ProductService(ProductRepository repository, ModelMapper modelMapper) {
		super();
		this.repository = repository;
		this.modelMapper = modelMapper;
	}

	public Product createProduct(ProductAttributes attr) {
		ProductEntity o = modelMapper.map(attr, ProductEntity.class);
		o = repository.save(o);
		
		Product dto = this.modelMapper.map(o,ProductResponseDto.class);

		return dto;
	}

	@Transactional
	public ProductAssociation updateProductAssociation(ProductAssociationRequestDto req) {
		ProductEntity product = this.repository.findById(req.getProductId()).orElseThrow(IllegalArgumentException::new);
		List<ProductEntity> associations = this.repository.findAllById(req.getAssociations());
		if (associations.size() < req.getAssociations().size()) {
			throw new IllegalArgumentException("Some product(s) does not exist to replace the association");
		}

		product.setAssociations(associations);
		product = this.repository.saveAndFlush(product);
		
		ProductAssociation dto = convert(product, associations);
		return dto;
	}

	private ProductAssociation convert(ProductEntity product, Collection<ProductEntity> associations) {
		ProductAssociationRequestDtoDBuilder builder = ProductAssociationResponseDto.builder().id(product.getId()).name(product.getName())
				.price(product.getPrice());
		associations.forEach(p->builder.addAssociation(modelMapper.map(p, ProductResponseDto.class)));
		ProductAssociation dto=builder.build();
		return dto;
	}

	public ProductAssociation getProductAssociation(Long id) {
		ProductEntity product = this.repository.findById(id).orElse(null);
		if(product == null) return null;
		return convert(product, product.getAssociations());
	}

	public Collection<ProductResponseDto> getProductList() {
		List<ProductEntity> productList=this.repository.findAll();
		Collection<ProductResponseDto> products=productList.stream()
		.map(product->modelMapper.map(product,ProductResponseDto.class))
		.collect(Collectors.toList());
		return products;
	}

	public BigDecimal calculatePurchaseAmount(Collection<ProductResponseDto> items) {
		Map<Long,Long> quantityMap=items.stream().map(item -> item.getId())
				.collect(Collectors.groupingBy(e -> e, Collectors.counting()));
		
		List<ProductEntity> allEntities = this.repository.findAllById(quantityMap.keySet());
		BigDecimal totalAmmount = allEntities.stream().map( 
				product -> product.getPrice().multiply(BigDecimal.valueOf(quantityMap.get(product.getId())))
				).reduce(BigDecimal.ZERO, (a,b) -> a.add(b) );
		return totalAmmount;
	}

}
