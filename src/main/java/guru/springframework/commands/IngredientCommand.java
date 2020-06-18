package guru.springframework.commands;

import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientCommand {
    private Long id;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasure unitOfMeasure;
    private Recipe recipe;
}
