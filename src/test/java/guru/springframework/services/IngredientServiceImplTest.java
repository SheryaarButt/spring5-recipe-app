package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientConverter;
import guru.springframework.domain.Ingredient;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.repositories.IngredientRepository;
import org.assertj.core.util.IterableUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
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
        testEntity2 = Ingredient.builder().id(2L).build();
        testEntity3 = Ingredient.builder().id(3L).build();
        testEntities = IterableUtil.iterable(testEntity1,testEntity2,testEntity3);

        ingredientService = new IngredientServiceImpl(ingredientConverter,ingredientRepository);
    }

    @Test
    public void getIngredients() {
        when(ingredientRepository.findAll()).thenReturn(testEntities);
        when(ingredientConverter.convertEntityToCommand(testEntity1)).thenReturn(testIngredient1);
        when(ingredientConverter.convertEntityToCommand(testEntity2)).thenReturn(testIngredient2);
        when(ingredientConverter.convertEntityToCommand(testEntity3)).thenReturn(testIngredient3);

        assertTrue(testIngredients.containsAll(ingredientService.getIngredients()));

        verify(ingredientRepository,times(1)).findAll();
        verify(ingredientConverter,times(3)).convertEntityToCommand(any());
    }

    @Test
    public void deleteIngredient(){
        ingredientService.deleteIngredient(1L);
        verify(ingredientRepository,times(1)).deleteById(1L);
    }

    @Test
    public void getIngredient() {
        when(ingredientRepository.findById(2L)).thenReturn(Optional.of(testEntity2));
        when(ingredientConverter.convertEntityToCommand(testEntity2)).thenReturn(testIngredient2);

        assertEquals(testIngredient2,ingredientService.getIngredient(2L));

        verify(ingredientRepository,times(1)).findById(2L);
        verify(ingredientConverter,times(1)).convertEntityToCommand(testEntity2);
    }

    @Test(expected = NotFoundException.class)
    public void getIngredientNotFound() {
        when(ingredientRepository.findById(4L)).thenReturn(Optional.empty());

        assertNull(ingredientService.getIngredient(4L));

        verify(ingredientRepository,times(1)).findById(4L);
    }

}