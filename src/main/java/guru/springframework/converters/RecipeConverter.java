package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.NotesCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Category;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Notes;
import guru.springframework.domain.Recipe;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RecipeConverter implements BaseConverter<Recipe, RecipeCommand> {

    private IngredientConverter ingredientConverter;
    private CategoryConverter categoryConverter;
    private NotesConverter notesConverter;

    public RecipeConverter(IngredientConverter ingredientConverter, CategoryConverter categoryConverter, NotesConverter notesConverter) {
        this.ingredientConverter = ingredientConverter;
        this.categoryConverter = categoryConverter;
        this.notesConverter = notesConverter;
    }

    @Override
    public RecipeCommand convertEntityToCommand(Recipe entity) {
        RecipeCommand returnCommand = new RecipeCommand();
        BeanUtils.copyProperties(entity,returnCommand,"notes","categories","ingredients");

        Notes notesEntity = entity.getNotes();
        if(notesEntity != null){
            returnCommand.setNotes(notesConverter.convertEntityToCommand(notesEntity));
        }

        Set<Ingredient> ingredientEntities = entity.getIngredients();
        if(!ingredientEntities.isEmpty()){
            ingredientEntities.forEach(ingredientEntity -> 
                returnCommand.addIngredient(ingredientConverter.convertEntityToCommand(ingredientEntity))
            );
        }

        Set<Category> categoryEntities = entity.getCategories();
        if(!categoryEntities.isEmpty()){
            categoryEntities.forEach(categoryEntity -> 
                returnCommand.addCategory(categoryConverter.convertEntityToCommand(categoryEntity))
            );
        }
        
        return returnCommand;
    }

    @Override
    public Recipe convertCommandToEntity(RecipeCommand command) {
        Recipe returnEntity = new Recipe();
        BeanUtils.copyProperties(command,returnEntity,"notes","categories","ingredients");

        NotesCommand notesCommand = command.getNotes();
        if(notesCommand != null){
            returnEntity.setNotes(notesConverter.convertCommandToEntity(notesCommand));
        }

        Set<IngredientCommand> ingredientCommands = command.getIngredients();
        if(!ingredientCommands.isEmpty()) {
            ingredientCommands.forEach(ingredientCommand ->
                    returnEntity.addIngredient(ingredientConverter.convertCommandToEntity(ingredientCommand))
            );
        }

        Set<CategoryCommand> categoryCommands = command.getCategories();
        if(!categoryCommands.isEmpty()) {
            categoryCommands.forEach(categoryCommand ->
                    returnEntity.addCategory(categoryConverter.convertCommandToEntity(categoryCommand))
            );
        }

        return returnEntity;
    }
}
