package test.coffemachine;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.slf4j.Slf4j;
import test.coffemachine.api.v1.dto.ProductAssociationRequestDto;
import test.coffemachine.api.v1.dto.ProductRequestDto;
import test.coffemachine.business.model.Product;
import test.coffemachine.business.model.ProductAssociation;
import test.coffemachine.service.ProductService;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@TestPropertySource(properties = { "CORS_ORIGINS='http://localhost','http://localhost:4200'" })
@Transactional
public class TDDProductTest {

	private static final String URL_PRODUCT = "/product";
	private static final String URL_ASSOCIATE = "/productAssociations";
	private static final String URL_PRODUCT_DETAILS = "/product/1";
	@Autowired
	private MockMvc mockMvc;
	private ObjectWriter jsonWriter;
	private ObjectMapper mapper;
	
	@Autowired
	private ProductService productService;
	
	@Before
	public void setUp() throws Exception {
		mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		jsonWriter = mapper.writer().withDefaultPrettyPrinter();
	}

	@Test
	public void test_context_ok() {
		Assert.assertTrue("On load context", true);
	}

	@Test
	public void given_ImmutableRequestObject_Then_DeserializeWithBuilder_expecting_ok() throws IOException {
		String name = "Cacao";
		BigDecimal price = BigDecimal.valueOf(5.0);
		
		ProductRequestDto expected = ProductRequestDto.builder().name(name).price(price).build();
		String jsonBody = jsonWriter.writeValueAsString(expected);
		log.info("JSON msg: {}", jsonBody);
		
		ProductRequestDto actual = mapper.readValue(jsonBody, ProductRequestDto.class);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void given_NoProducts_then_createProduct_expecting_ok() throws Exception {
		String name = "Cacao";
		BigDecimal price = BigDecimal.valueOf(5.0);
		String description="lalala";

		ProductRequestDto product = ProductRequestDto.builder().name(name)
				.price(price)
				.description(description)
				.build();

		String jsonBody = jsonWriter.writeValueAsString(product);

		mockMvc.perform(post(URL_PRODUCT).contentType(MediaType.APPLICATION_JSON).content(jsonBody)
		).andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.name", is(name)))
		.andExpect(jsonPath("$.price", is(price.doubleValue())));
	}
	
	
	@Test
	public void given_3products_then_createSimpleProductAsociation_expecting_Ok() throws Exception {
		String expectedName = "Cacao";
		BigDecimal expectedPrice = BigDecimal.valueOf(5.0);
		ProductRequestDto productAttributes1 = ProductRequestDto.builder()
				.name(expectedName).price(expectedPrice).build();
		String expectedName2 = "Suggar";
		BigDecimal expectedPrice2 = BigDecimal.valueOf(1.0);
		ProductRequestDto productAttributes2 = ProductRequestDto.builder()
				.name(expectedName2).price(expectedPrice2).build();
		String expectedName3 = "Milk";
		BigDecimal expectedPrice3 = BigDecimal.valueOf(0.5);
		ProductRequestDto productAttributes3 = ProductRequestDto.builder()
				.name(expectedName3).price(expectedPrice3).build();

		Product product1=productService.createProduct(productAttributes1);
		Product product2=productService.createProduct(productAttributes2);
		Product product3=productService.createProduct(productAttributes3);
		
		
		ProductAssociationRequestDto associations=ProductAssociationRequestDto.builder()
				.productId(product1.getId())
				.addAssociation(product2.getId())
				.addAssociation(product3.getId())
				.build();
		
		String jsonBody = jsonWriter.writeValueAsString(associations);

		mockMvc.perform(post(URL_ASSOCIATE).contentType(MediaType.APPLICATION_JSON).content(jsonBody)
		).andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.name", is(expectedName)))
		.andExpect(jsonPath("$.price", is(expectedPrice.doubleValue())))
		.andExpect(jsonPath("$.associations[0].name", is(expectedName2)))
		.andExpect(jsonPath("$.associations[0].price", is(expectedPrice2.doubleValue())))
		.andExpect(jsonPath("$.associations[1].name", is(expectedName3)))
		.andExpect(jsonPath("$.associations[1].price", is(expectedPrice3.doubleValue())));
	}
	
	@Test
	public void given_2products_then_getProductAsociation_expecting_Ok() throws Exception {
		String expectedName = "Cacao";
		BigDecimal expectedPrice = BigDecimal.valueOf(5.0);
		String expectedName2 = "Suggar";
		BigDecimal expectedPrice2 = BigDecimal.valueOf(1.0);
		ProductRequestDto productAttributes1 = ProductRequestDto.builder()
				.name(expectedName).price(expectedPrice).build();
		ProductRequestDto productAttributes2 = ProductRequestDto.builder()
				.name(expectedName2).price(expectedPrice2).build();

		Product product1=productService.createProduct(productAttributes1);
		Product product2=productService.createProduct(productAttributes2);
		
		
		ProductAssociationRequestDto associations=ProductAssociationRequestDto.builder()
				.productId(product1.getId())
				.addAssociation(product2.getId())
				.build();
		
		log.info("Request Associations: {}",associations);
		ProductAssociation update = productService.updateProductAssociation(associations);
		log.info("Product Associations: {}",update);
		
		mockMvc.perform(get(URL_PRODUCT+'/'+product1.getId())
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.name", is(expectedName)))
		.andExpect(jsonPath("$.price", is(expectedPrice.doubleValue())))
		.andExpect(jsonPath("$.associations[0].name", is(expectedName2)))
		.andExpect(jsonPath("$.associations[0].price", is(expectedPrice2.doubleValue())));
	}
	
	@Test
	public void given_someProducts_then_detailsAreAvailable_expecting_ok() throws Exception {
		String expectedName = "Cacao";
		BigDecimal expectedPrice = BigDecimal.valueOf(5.0);
		ProductRequestDto productAttributes1 = ProductRequestDto.builder()
				.name(expectedName).price(expectedPrice).build();
		String expectedName2 = "Suggar";
		BigDecimal expectedPrice2 = BigDecimal.valueOf(1.0);
		ProductRequestDto productAttributes2 = ProductRequestDto.builder()
				.name(expectedName2).price(expectedPrice2).build();
		String expectedName3 = "Milk";
		BigDecimal expectedPrice3 = BigDecimal.valueOf(0.5);
		ProductRequestDto productAttributes3 = ProductRequestDto.builder()
				.name(expectedName3).price(expectedPrice3).build();

		Product product1=productService.createProduct(productAttributes1);
		Product product2=productService.createProduct(productAttributes2);
		Product product3=productService.createProduct(productAttributes3);
		
		
		ProductAssociationRequestDto associations=ProductAssociationRequestDto.builder()
				.productId(product1.getId())
				.addAssociation(product2.getId())
				.addAssociation(product3.getId())
				.build();
		
		String jsonBody = jsonWriter.writeValueAsString(associations);

		mockMvc.perform(post(URL_ASSOCIATE).contentType(MediaType.APPLICATION_JSON).content(jsonBody)
		).andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.name", is(expectedName)))
		.andExpect(jsonPath("$.price", is(expectedPrice.doubleValue())))
		.andExpect(jsonPath("$.associations[0].name", is(expectedName2)))
		.andExpect(jsonPath("$.associations[0].price", is(expectedPrice2.doubleValue())))
		.andExpect(jsonPath("$.associations[1].name", is(expectedName3)))
		.andExpect(jsonPath("$.associations[1].price", is(expectedPrice3.doubleValue())));
		
		
		mockMvc.perform(get(URL_PRODUCT+'/'+product1.getId())
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.associations", notNullValue()))
		.andExpect(jsonPath("$.associations[0].name", is(expectedName2)));

		
	}
}
