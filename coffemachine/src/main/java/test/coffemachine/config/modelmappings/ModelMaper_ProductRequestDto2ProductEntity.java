package test.coffemachine.config.modelmappings;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import test.coffemachine.api.v1.dto.ProductRequestDto;
import test.coffemachine.model.entities.ProductEntity;

@Configuration
public class ModelMaper_ProductRequestDto2ProductEntity {
	
	private ModelMapper modelMapper;
	
	@Autowired
	public ModelMaper_ProductRequestDto2ProductEntity(ModelMapper modelMapper) {
		super();
		this.modelMapper = modelMapper;
		initMapperDefinition();
	}
	
	public void initMapperDefinition() {
		TypeMap<ProductRequestDto, ProductEntity> rateDTORateTypeMap = modelMapper.getTypeMap(ProductRequestDto.class,
				ProductEntity.class);
		if (rateDTORateTypeMap == null) {
			rateDTORateTypeMap = modelMapper.createTypeMap(ProductRequestDto.class
					, ProductEntity.class);
		}
		rateDTORateTypeMap.setProvider(request -> {
			ProductRequestDto source = ProductRequestDto.class.cast(request.getSource());
			return convert(source);
		});
	}

	public ProductEntity convert(ProductRequestDto source) {
		ProductEntity out = new ProductEntity();
				out.setName(source.getName());
				out.setPrice(source.getPrice());
				out.setDescription(source.getDescription());
		return out;
	}
	
}
