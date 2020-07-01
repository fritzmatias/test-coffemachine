package test.coffemachine.api.v1.dto;


import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder(toBuilder = true,builderClassName = "ProductAssociationRequestDtoDBuilder")
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@JsonDeserialize(builder = ProductAssociationRequestDto.ProductAssociationRequestDtoDBuilder.class)
public class ProductAssociationRequestDto {
	
    @JsonPOJOBuilder(withPrefix = "")
    public static class ProductAssociationRequestDtoDBuilder {
    	public ProductAssociationRequestDtoDBuilder addAssociation(Long productId) {
    		if(this.associations==null) {
    			this.associations=new HashSet<Long>();
    		}
  			this.associations.add(productId);
  			return this;
    	}
	}
    
    private Long productId;
    private Set<Long> associations;
    
}
