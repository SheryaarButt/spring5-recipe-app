package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter implements BaseConverter<Category, CategoryCommand> {

    @Override
    public CategoryCommand convertEntityToCommand(Category entity) {
        CategoryCommand returnCommand = new CategoryCommand();
        BeanUtils.copyProperties(entity,returnCommand);
        return returnCommand;
    }

    @Override
    public Category convertCommandToEntity(CategoryCommand command) {
        Category returnEntity = new Category();
        BeanUtils.copyProperties(command,returnEntity);
        return returnEntity;
    }
}
