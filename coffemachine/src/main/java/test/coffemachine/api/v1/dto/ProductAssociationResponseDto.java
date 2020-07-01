package test.coffemachine.api.v1.dto;


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import test.coffemachine.business.model.Product;
import test.coffemachine.business.model.ProductAssociation;

@Builder(toBuilder = true,builderClassName = "ProductAssociationRequestDtoDBuilder")
@AllArgsConstructor
@Getter(onMethod = @__(@Override))
@EqualsAndHashCode
@ToString
@JsonDeserialize(builder = ProductAssociationResponseDto.ProductAssociationRequestDtoDBuilder.class)
public class ProductAssociationResponseDto implements ProductAssociation {
	
    @JsonPOJOBuilder(withPrefix = "")
    public static class ProductAssociationRequestDtoDBuilder {
    	public ProductAssociationRequestDtoDBuilder addAssociation(Product product) {
    		if(this.associations==null) {
    			this.associations=new HashSet<Product>();
    		}
  			this.associations.add(product);
  			return this;
    	}
	}
    
    private Long id;
    private String name;
    private BigDecimal price;
    private Set<Product> associations;
    private String description;

    
}
