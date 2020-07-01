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
import test.coffemachine.business.model.Product;

@Builder(toBuilder = true,builderClassName = "ProductResponseDtoBuilder")
@AllArgsConstructor
@Getter(onMethod = @__({@Override}))
@EqualsAndHashCode
@ToString
@JsonDeserialize(builder = ProductResponseDto.ProductResponseDtoBuilder.class)
public class ProductResponseDto implements Product {
	
    @JsonPOJOBuilder(withPrefix = "")
	public static class ProductResponseDtoBuilder{		
	}
    @NotNull
    private Long id;
    
    @NotNull
	private String name;
	
    @NotNull
	private BigDecimal price;
    
    @NotNull
    private String description;
}
