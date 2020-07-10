package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientConverter;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.repositories.IngredientRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientConverter ingredientConverter;
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientConverter ingredientConverter, IngredientRepository ingredientRepository) {
        this.ingredientConverter = ingredientConverter;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public Set<IngredientCommand> getIngredients() {
        return StreamSupport.stream(ingredientRepository.findAll().spliterator(),true)
                    .map(ingredientConverter::convertEntityToCommand)
                    .collect(Collectors.toCollection(HashSet::new));
    }

    @Override
    @Transactional
    public void deleteIngredient(Long id){
        ingredientRepository.deleteById(id);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public IngredientCommand getIngredient(Long id) {
        return ingredientConverter.convertEntityToCommand(ingredientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ingredient not found. Ingredient ID: " + id.toString())));
    }
}
