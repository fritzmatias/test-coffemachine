package test.coffemachine.business.model;

import java.util.Set;

public interface ProductAssociation extends Product {
	public Set<Product> getAssociations();

}
