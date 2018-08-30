package tacos.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import tacos.domain.Taco;
import tacos.repositories.IngredientRepository;
import tacos.repositories.TacoRepository;
import tacos.domain.Ingredient;
import tacos.domain.Ingredient.Type;
import tacos.domain.Order;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {
	
	private final TacoRepository tacoRepository;
	private final IngredientRepository ingredientRepository;
	
	
	@Autowired
	public DesignTacoController(TacoRepository tacoRepository, IngredientRepository ingredientRepository) {
		this.tacoRepository = tacoRepository;
		this.ingredientRepository = ingredientRepository;
	}
	
	@ModelAttribute(name="order")
	public Order order() {
		return new Order();
	}

	@GetMapping
	public String showDesignForm(Model model) {
		
		// Filter a list of all ingredients by type and add those lists to the model
		Iterable<Ingredient> ingredients = ingredientRepository.findAll();	
		
		Type[] types = Ingredient.Type.values();
		for (Type type : types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		}		
		model.addAttribute("taco", new Taco());
		return "design";
	}
	
	@PostMapping
	public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order, Model model) {
		if (errors.hasErrors()) {
			
			// TODO: check for other ways to make the ingredients visible after submitting an invalid form
			// Filter a list of all ingredients by type and add those lists to the model
			Iterable<Ingredient> ingredients = ingredientRepository.findAll();
			Type[] types = Ingredient.Type.values();
			for (Type type : types) {
				model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
			}
			
			log.info("Processing design contains errors: {}", design);
			return "design";
		}
		
		log.info("Processing design: {}", design);
		Taco savedTaco = tacoRepository.save(design);
		order.addTaco(savedTaco);
		log.info("Processed design: {}", savedTaco);
		
		return "redirect:/orders/current";
	}
	
	private List<Ingredient> filterByType(Iterable<Ingredient> ingredients, Type type) {
		return ((List<Ingredient>)ingredients)
				.stream()
				.filter(x -> x.getType().equals(type))
				.collect(Collectors.toList());
	}

}
