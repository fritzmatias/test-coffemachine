package test.coffemachine.config.modelmappings;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import test.coffemachine.api.v1.dto.ProductResponseDto;
import test.coffemachine.model.entities.ProductEntity;

@Configuration
public class ModelMaper_ProductEntity2ProductResponseDto {
	
	private ModelMapper modelMapper;
	
	@Autowired
	public ModelMaper_ProductEntity2ProductResponseDto(ModelMapper modelMapper) {
		super();
		this.modelMapper = modelMapper;
		initMapperDefinition();
	}
	
	public void initMapperDefinition() {
		TypeMap<ProductEntity, ProductResponseDto> rateDTORateTypeMap = modelMapper.getTypeMap(ProductEntity.class,
				ProductResponseDto.class);
		if (rateDTORateTypeMap == null) {
			rateDTORateTypeMap = modelMapper.createTypeMap(ProductEntity.class
					, ProductResponseDto.class);
		}
		rateDTORateTypeMap.setProvider(request -> {
			ProductEntity source = ProductEntity.class.cast(request.getSource());
			return convert(source);
		});
	}

	public ProductResponseDto convert(ProductEntity source) {
		ProductResponseDto out = ProductResponseDto.builder()
				.id(source.getId())
				.name(source.getName())
				.price(source.getPrice())
				.description(source.getDescription())
				.build();
		return out;
	}
	
}
