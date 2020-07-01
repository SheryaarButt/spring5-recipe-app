package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureConverter;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.assertj.core.util.IterableUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UnitOfMeasureServiceImplTest {

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;
    @Mock
    UnitOfMeasureConverter unitOfMeasureConverter;

    UnitOfMeasureServiceImpl unitOfMeasureService;

    UnitOfMeasure testUom1;
    UnitOfMeasure testUom2;
    UnitOfMeasure testUom3;

    UnitOfMeasureCommand testUom1Command;
    UnitOfMeasureCommand testUom2Command;
    UnitOfMeasureCommand testUom3Command;


    Iterable<UnitOfMeasure> uoms;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testUom1 = UnitOfMeasure.builder().id(1L).uom("tablespoon").build();
        testUom2 = UnitOfMeasure.builder().id(2L).uom("dollop").build();
        testUom3 = UnitOfMeasure.builder().id(3L).uom("cup").build();

        testUom1Command = UnitOfMeasureCommand.builder().id(1L).uom("tablespoon").build();
        testUom2Command = UnitOfMeasureCommand.builder().id(2L).uom("dollop").build();
        testUom3Command = UnitOfMeasureCommand.builder().id(3L).uom("cup").build();

        uoms = IterableUtil.iterable(testUom1,testUom2,testUom3);
        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository,unitOfMeasureConverter);
    }

    @Test
    public void getUomList() {

        when(unitOfMeasureRepository.findAll()).thenReturn(uoms);
        when(unitOfMeasureConverter.convertEntityToCommand(testUom1)).thenReturn(testUom1Command);
        when(unitOfMeasureConverter.convertEntityToCommand(testUom2)).thenReturn(testUom2Command);
        when(unitOfMeasureConverter.convertEntityToCommand(testUom3)).thenReturn(testUom3Command);

        List<UnitOfMeasureCommand> uomList = new ArrayList<>(unitOfMeasureService.getUoms());

        assertEquals(testUom3.getUom(),uomList.get(0).getUom());
        assertEquals(testUom3.getId(),uomList.get(0).getId());

        assertEquals(testUom2.getUom(),uomList.get(1).getUom());
        assertEquals(testUom2.getId(),uomList.get(1).getId());

        assertEquals(testUom1.getUom(),uomList.get(2).getUom());
        assertEquals(testUom1.getId(),uomList.get(2).getId());

        verify(unitOfMeasureRepository,times(1)).findAll();
        verify(unitOfMeasureConverter,times(3)).convertEntityToCommand(any());
    }
}