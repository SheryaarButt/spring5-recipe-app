package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoryConverterTest {

    Category testEntity;
    CategoryCommand testCommand;

    CategoryConverter categoryConverter;

    @Before
    public void setUp() throws Exception {
        testEntity = Category.builder().id(1L).categoryName("TestEntity").build();
        testCommand = CategoryCommand.builder().id(2L).categoryName("TestCommand").build();
        categoryConverter = new CategoryConverter();
    }

    @Test
    public void convertEntityToCommand() {
        CategoryCommand returnCommand = categoryConverter.convertEntityToCommand(testEntity);
        assertEquals(testEntity.getId(),returnCommand.getId());
        assertEquals(testEntity.getCategoryName(),returnCommand.getCategoryName());
    }

    @Test
    public void convertCommandToEntity() {
        Category returnEntity = categoryConverter.convertCommandToEntity(testCommand);
        assertEquals(testCommand.getId(),returnEntity.getId());
        assertEquals(testCommand.getCategoryName(),returnEntity.getCategoryName());
    }
}