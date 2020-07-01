package test.coffemachine.model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductAssociationCompositePK implements Serializable{
	private static final long serialVersionUID = 8260730700147613204L;

	@Column(name="product_id")
	private Long productId;
	@Column(name="association_id")
	private Long association_id;	
}
