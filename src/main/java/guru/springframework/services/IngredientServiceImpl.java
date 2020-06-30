package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientConverter;
import guru.springframework.repositories.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class IngredientServiceImpl implements IngredientService {

    private IngredientConverter ingredientConverter;
    private IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientConverter ingredientConverter, IngredientRepository ingredientRepository) {
        this.ingredientConverter = ingredientConverter;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Set<IngredientCommand> getIngredients() {
        Set<IngredientCommand> returnIngredients = new HashSet<>();
        ingredientRepository.findAll().forEach(foundIngredient -> {
            returnIngredients.add(ingredientConverter.convertEntityToCommand(foundIngredient));
        });
        return returnIngredients;
    }

    @Override
    public void deleteIngredient(Long id){
        ingredientRepository.deleteById(id);
    }

    @Override
    public IngredientCommand getIngredient(Long id) {
        return ingredientConverter.convertEntityToCommand(ingredientRepository.findById(id).orElse(null));
    }
}
