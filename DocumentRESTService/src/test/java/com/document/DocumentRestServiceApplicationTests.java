package com.document;

import org.junit.After;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.document.domain.SearchCriteria;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.io.File;

/**
 * @author Mark Oprea
 * These tests are just a quick way to tell that all requests are working.
 * These test cases are just for proof of concept and are by no means meant to covert edge cases or unit tests.
 *
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class DocumentRestServiceApplicationTests {

	@Autowired
	private MockMvc mvc;
	@Autowired
	private WebApplicationContext webApplicationContext;
	@Autowired
    ObjectMapper objectMapper;

	private static final Logger log = LoggerFactory.getLogger(DocumentRestServiceApplicationTests.class);

	@Value("${com.document.DocumentStore}")
	private String datastoreLocation;

	@Test
	public void uploadDocumentTest() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("data", "test.txt", "text/plain", "TEST FIle".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"documentID\":0,\"author\":{\"firstName\":\"Mark\", \"lastName\":\"Oprea\"},\"name\":\"test.txt\",\"location\":null,\"description\":\"just a test file\",\"uploadTImestamp\":null}"
						.getBytes());

		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/Document").file(firstFile).file(jsonFile))
				.andExpect(status().is(200)).andExpect(content().string(
						"{\"documentMeta\":{\"documentID\":2,\"author\":{\"autorID\":1,\"firstName\":\"Mark\",\"lastName\":\"Oprea\"},\"name\":\"test.txt\",\"location\":\"C:\\\\develop\\\\DocumentREST\\\\DocumentRESTService\\\\DocStore\\\\test.txt\",\"description\":\"just a test file\"},\"documentContents\":null}"));
	}
	
	@Test
	public void downloadDocumentTest() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("data", "test2.doc", "text/plain", "TEST FIle".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"documentID\":0,\"author\":{\"firstName\":\"Mark\", \"lastName\":\"Oprea\"},\"name\":\"test2.doc\",\"location\":null,\"description\":\"just a test file\",\"uploadTImestamp\":null}"
						.getBytes());

		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/Document").file(firstFile).file(jsonFile))
				.andExpect(status().is(200)).andExpect(content().string(
						"{\"documentMeta\":{\"documentID\":4,\"author\":{\"autorID\":1,\"firstName\":\"Mark\",\"lastName\":\"Oprea\"},\"name\":\"test2.doc\",\"location\":\"C:\\\\develop\\\\DocumentREST\\\\DocumentRESTService\\\\DocStore\\\\test2.doc\",\"description\":\"just a test file\"},\"documentContents\":null}"));
		
		
		mockMvc.perform(MockMvcRequestBuilders.get("/Document/4/"))
				.andExpect(status().is(200));
	}
	
	
	
	
	@Test
	public void retriveMetadataTest() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("data", "test3.txt", "text/plain", "TEST FIle".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"documentID\":0,\"author\":{\"firstName\":\"Mark\", \"lastName\":\"Oprea\"},\"name\":\"test3.txt\",\"location\":null,\"description\":\"just a test file\",\"uploadTImestamp\":null}"
						.getBytes());

		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/Document").file(firstFile).file(jsonFile))
				.andExpect(status().is(200)).andExpect(content().string(
						"{\"documentMeta\":{\"documentID\":1,\"author\":{\"autorID\":1,\"firstName\":\"Mark\",\"lastName\":\"Oprea\"},\"name\":\"test3.txt\",\"location\":\"C:\\\\develop\\\\DocumentREST\\\\DocumentRESTService\\\\DocStore\\\\test3.txt\",\"description\":\"just a test file\"},\"documentContents\":null}"));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/Document/1/DocumentMeta")).andExpect(status().is(200)).andExpect(content().string(
				"{\"documentID\":1,\"author\":{\"autorID\":1,\"firstName\":\"Mark\",\"lastName\":\"Oprea\"},\"name\":\"test3.txt\",\"location\":\"C:\\\\develop\\\\DocumentREST\\\\DocumentRESTService\\\\DocStore\\\\test3.txt\",\"description\":\"just a test file\"}"));
	}
	
	
	@Test
	public void searchTest() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("data", "test4.txt", "text/plain", "TEST FIle".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"documentID\":0,\"author\":{\"firstName\":\"James\", \"lastName\":\"Smith\"},\"name\":\"test4.txt\",\"location\":null,\"description\":\"another test\",\"uploadTImestamp\":null}"
						.getBytes());

		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/Document").file(firstFile).file(jsonFile))
				.andExpect(status().is(200)).andExpect(content().string(
						"{\"documentMeta\":{\"documentID\":3,\"author\":{\"autorID\":2,\"firstName\":\"James\",\"lastName\":\"Smith\"},\"name\":\"test4.txt\",\"location\":\"C:\\\\develop\\\\DocumentREST\\\\DocumentRESTService\\\\DocStore\\\\test4.txt\",\"description\":\"another test\"},\"documentContents\":null}"));
		
		
		mockMvc.perform(MockMvcRequestBuilders.post("/Document/Search").contentType(MediaType.APPLICATION_JSON)
	            .content("{\"authorFirstName\":\"James\",\"authorLastName\":\"Smith\",\"fileName\":\"test4.txt\",\"fileDescription\":\"another test\"}")).andExpect(status().is(200)).andExpect(content().string("[3]"));

		mockMvc.perform(MockMvcRequestBuilders.post("/Document/Search").contentType(MediaType.APPLICATION_JSON)
	            .content("{\"authorFirstName\":\"Mark\",\"authorLastName\":\"\",\"fileName\":\"\",\"fileDescription\":\"\"}")).andExpect(status().is(200)).andExpect(content().string("[1,2]"));
	}
	

	@After
	public void clean() {

		File dir = new File(datastoreLocation);
		for (File file : dir.listFiles()) {
			file.delete();
		}
	}

}
