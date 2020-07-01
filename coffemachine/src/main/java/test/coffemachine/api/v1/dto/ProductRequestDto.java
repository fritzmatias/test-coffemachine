package test.coffemachine.api.v1.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import test.coffemachine.business.model.ProductAttributes;

@Builder(toBuilder = true,builderClassName = "ProductRequestDtoBuilder")
@AllArgsConstructor
@Getter(onMethod = @__({@Override}))
@EqualsAndHashCode
@ToString
@JsonDeserialize(builder = ProductRequestDto.ProductRequestDtoBuilder.class)
public class ProductRequestDto implements ProductAttributes {
	
    @JsonPOJOBuilder(withPrefix = "")
	public static class ProductRequestDtoBuilder{		
	}

    @NotNull
	private String name;
	
    @NotNull
	private BigDecimal price;
    
    private String description;
    	
}
