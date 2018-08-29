package tacos.controllers;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testOrderForm() throws Exception{
		mockMvc.perform(get("/orders/current"))
		.andExpect(status().isOk())
		.andExpect(view().name("orderform"))
		.andExpect(model().attributeExists("order"));
	}

	@Test
	public void testProcessOrderHappyPath() throws Exception {
		
		mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("name", "Some name")
				.param("street", "Some street")
				.param("city", "Some city")
				.param("state", "Some state")
				.param("zip", "Some zip")
				.param("ccNumber", "4556442061262741")
				.param("ccExpiration", "12/20")
				.param("ccCVV", "123"))		
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/"));
	}

}
