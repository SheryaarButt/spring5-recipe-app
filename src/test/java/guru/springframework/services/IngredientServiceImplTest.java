package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientConverter;
import guru.springframework.domain.Ingredient;
import guru.springframework.repositories.IngredientRepository;
import org.assertj.core.util.IterableUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {

    @Mock
    IngredientConverter ingredientConverter;

    @Mock
    IngredientRepository ingredientRepository;

    IngredientServiceImpl ingredientService;

    IngredientCommand testIngredient1;
    IngredientCommand testIngredient2;
    IngredientCommand testIngredient3;
    Set<IngredientCommand> testIngredients;

    Ingredient testEntity1;
    Ingredient testEntity2;
    Ingredient testEntity3;
    Iterable<Ingredient> testEntities;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        testIngredient1 = IngredientCommand.builder().id(1L).build();
        testIngredient2 = IngredientCommand.builder().id(2L).build();
        testIngredient3 = IngredientCommand.builder().id(3L).build();
        testIngredients = new HashSet<>();
        testIngredients.add(testIngredient1);
        testIngredients.add(testIngredient2);
        testIngredients.add(testIngredient3);

        testEntity1 = Ingredient.builder().id(1L).build();
        testEntity1 = Ingredient.builder().id(2L).build();
        testEntity1 = Ingredient.builder().id(3L).build();
        testEntities = IterableUtil.iterable(testEntity1,testEntity2,testEntity3);

        ingredientService = new IngredientServiceImpl(ingredientConverter,ingredientRepository);
    }

    @Test
    public void getIngredients() {
        when(ingredientRepository.findAll()).thenReturn(testEntities);
        when(ingredientConverter.convertEntityToCommand(testEntity1)).thenReturn(testIngredient1);
        when(ingredientConverter.convertEntityToCommand(testEntity2)).thenReturn(testIngredient1);
        when(ingredientConverter.convertEntityToCommand(testEntity3)).thenReturn(testIngredient1);

        assertTrue(testIngredients.containsAll(ingredientService.getIngredients()));

        verify(ingredientRepository,times(1)).findAll();
        verify(ingredientConverter,times(3)).convertEntityToCommand(any());
    }
}