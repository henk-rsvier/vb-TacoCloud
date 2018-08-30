package tacos.bootstrap;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import tacos.domain.Ingredient;
import tacos.repositories.IngredientRepository;

@Component
public class IngredientBootstrap implements ApplicationListener<ContextRefreshedEvent>{

	private final IngredientRepository ingredientRepository;
	
	public IngredientBootstrap(IngredientRepository ingredientRepository) {
		this.ingredientRepository = ingredientRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		
		ingredientRepository.saveAll(getIngredients());
	}

	private List<Ingredient> getIngredients() {
		List<Ingredient> ingredients = new ArrayList<>();
		ingredients.add(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
		ingredients.add(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
		ingredients.add(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
		ingredients.add(new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
		ingredients.add(new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
		ingredients.add(new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES));
		ingredients.add(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
		ingredients.add(new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
		ingredients.add(new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
		ingredients.add(new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));
		return ingredients;
	}
}
