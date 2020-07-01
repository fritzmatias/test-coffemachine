package test.coffemachine.model.entities;

import java.math.BigDecimal;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;
import test.coffemachine.business.model.Product;

@Getter
@Setter
@Entity(name="product")
public class ProductEntity implements Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "price")
	private BigDecimal price;
	@Column(name = "description")
	private String description;
	
	@ManyToMany
	private Collection<ProductEntity> associations;

}
