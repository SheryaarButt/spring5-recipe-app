package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter implements BaseConverter<Category, CategoryCommand> {

    @Nullable
    @Override
    public CategoryCommand convertEntityToCommand(Category entity) {
        if(entity == null){
            return null;
        }
        CategoryCommand returnCommand = new CategoryCommand();
        BeanUtils.copyProperties(entity,returnCommand);
        return returnCommand;
    }

    @Nullable
    @Override
    public Category convertCommandToEntity(CategoryCommand command) {
        if(command == null){
            return null;
        }
        Category returnEntity = new Category();
        BeanUtils.copyProperties(command,returnEntity);
        return returnEntity;
    }
}
