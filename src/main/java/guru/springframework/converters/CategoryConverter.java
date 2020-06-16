package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter implements BaseConverter<Category, CategoryCommand> {
    @Override
    public CategoryCommand convertEntityToCommand(Category entity) {
        return null;
    }

    @Override
    public Category convertCommandToEntity(CategoryCommand command) {
        return null;
    }
}
