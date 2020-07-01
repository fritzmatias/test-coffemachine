package test.coffemachine.model.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import test.coffemachine.business.model.ProductAttributes;

@Builder(toBuilder = true, builderClassName = "ProductDtoBuilder")
@AllArgsConstructor
@Getter(onMethod = @__({@Override}))
@ToString
public class ProductAttributesDto implements ProductAttributes {
	private String name;
	private BigDecimal price;
	private String description;
	public static class ProductDtoBuilder{
	}
	
}
