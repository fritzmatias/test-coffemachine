package test.coffemachine;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Files;
import java.nio.file.Paths;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@Transactional
public class TDDSetupTest {
	@Autowired
	private MockMvc mockMvc;
	private String endpoint="/setupProducts";

	@Test
	public void given_noProducts_then_setupProductArray_expecting_Ok() throws Exception {
		UriComponentsBuilder endpointBuilderPage;
		String mockedData;
		endpointBuilderPage=UriComponentsBuilder.fromUriString(endpoint);
		
		String jsonFile="product_setup_test.json";
		mockedData = new String(
				Files.readAllBytes(Paths.get(ClassLoader.getSystemResource(jsonFile).toURI())));
		Assert.assertNotNull(mockedData);
		
		mockMvc.perform(post(endpoint).contentType(MediaType.APPLICATION_JSON).content(mockedData)
				).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(3)));
	}
 
}
