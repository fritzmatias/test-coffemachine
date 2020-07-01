package test.coffemachine.model.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;


@Entity(name = "ProductAssociation")
public class ProductAssociationEntity {
	
	@EmbeddedId
	private ProductAssociationCompositePK id;
	
	@ManyToOne
	@MapsId("product_id")
	@JoinColumn(name = "product_id")
	private ProductEntity product;
	
	@ManyToOne
	@MapsId("association_id")
	@JoinColumn(name = "association_id")
	private ProductEntity associations;

}
