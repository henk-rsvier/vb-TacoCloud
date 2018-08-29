package tacos.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
@WebMvcTest(DesignTacoController.class)
public class DesignTacoControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testShowHomePage() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(view().name("home"))
			.andExpect(content().string(containsString("Welcome to...")));
		
	}
	
	@Test
	public void testShowDesignForm() throws Exception {
		mockMvc.perform(get("/design"))
			.andExpect(status().isOk())
			.andExpect(view().name("design"))
			.andExpect(model().attributeExists("wrap"))
			.andExpect(model().attributeExists("design"));
	}

	@Test
	public void testProcessDesignHappyPath() throws Exception {
		String[] ingredients = {"Some ingredient"};
		
		mockMvc.perform(post("/design").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("name", "Some name")
				.param("ingredients", ingredients))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/orders/current"));
	}

}
