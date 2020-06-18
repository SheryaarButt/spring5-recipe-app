package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Category;
import guru.springframework.domain.Recipe;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter implements BaseConverter<Category, CategoryCommand> {

    @Override
    public CategoryCommand convertEntityToCommand(Category entity) {
        CategoryCommand returnCommand = new CategoryCommand();
        BeanUtils.copyProperties(entity,returnCommand,"recipes");
        entity.getRecipes().forEach(recipeEntity -> {
            RecipeCommand recipeCommand = new RecipeCommand();
            BeanUtils.copyProperties(recipeEntity,recipeCommand);
            returnCommand.getRecipes().add(recipeCommand);
        });
        return returnCommand;
    }

    @Override
    public Category convertCommandToEntity(CategoryCommand command) {
        Category returnEntity = new Category();
        BeanUtils.copyProperties(command,returnEntity,"recipes");
        command.getRecipes().forEach(recipeCommand -> {
            Recipe recipeEntity = new Recipe();
            BeanUtils.copyProperties(recipeCommand,recipeEntity);
            returnEntity.getRecipes().add(recipeEntity);
        });
        return returnEntity;
    }
}
