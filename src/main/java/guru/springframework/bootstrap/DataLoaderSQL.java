package guru.springframework.bootstrap;

import guru.springframework.domain.Category;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("dev")
public class DataLoaderSQL implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public DataLoaderSQL(CategoryRepository categoryRepository,
                         UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        if(categoryRepository.count() == 0L){
            log.debug("Loading category data to SQL DB!");
            loadCategories();
        }
        if(unitOfMeasureRepository.count() == 0L){
            log.debug("Loading uom data to SQL DB!");
            loadUoms();
        }
    }

    private void loadCategories() {
        Category american = Category.builder().categoryName("American").build();
        Category mexican = Category.builder().categoryName("Mexican").build();
        Category spanish = Category.builder().categoryName("Spanish").build();
        Category veggie = Category.builder().categoryName("Vegetarian").build();

        categoryRepository.save(american);
        categoryRepository.save(mexican);
        categoryRepository.save(spanish);
        categoryRepository.save(veggie);
    }

    private void loadUoms() {
        UnitOfMeasure tsp = UnitOfMeasure.builder().uom("teaspoon").build();
        UnitOfMeasure tsb = UnitOfMeasure.builder().uom("tablespoon").build();
        UnitOfMeasure dash = UnitOfMeasure.builder().uom("dash").build();
        UnitOfMeasure each = UnitOfMeasure.builder().uom("each").build();

        unitOfMeasureRepository.save(tsp);
        unitOfMeasureRepository.save(tsb);
        unitOfMeasureRepository.save(dash);
        unitOfMeasureRepository.save(each);
    }

}
