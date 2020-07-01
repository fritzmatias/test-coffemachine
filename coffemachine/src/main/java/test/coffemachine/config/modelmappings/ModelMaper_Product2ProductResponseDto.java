package test.coffemachine.config.modelmappings;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;

import test.coffemachine.api.v1.dto.ProductResponseDto;
import test.coffemachine.business.model.Product;

//@Configuration
public class ModelMaper_Product2ProductResponseDto {
	
	private ModelMapper modelMapper;
	
	@Autowired
	public ModelMaper_Product2ProductResponseDto(ModelMapper modelMapper) {
		super();
		this.modelMapper = modelMapper;
		initMapperDefinition();
	}
	
	public void initMapperDefinition() {
		TypeMap<Product, ProductResponseDto> rateDTORateTypeMap = modelMapper.getTypeMap(Product.class,
				ProductResponseDto.class);
		if (rateDTORateTypeMap == null) {
			rateDTORateTypeMap = modelMapper.createTypeMap(Product.class
					, ProductResponseDto.class);
		}
		rateDTORateTypeMap.setProvider(request -> {
			Product source = Product.class.cast(request.getSource());
			return convert(source);
		});
	}

	public ProductResponseDto convert(Product source) {
		ProductResponseDto out = ProductResponseDto.builder()
				.name(source.getName())
				.price(source.getPrice())
				.build();
		return out;
	}
	
}
