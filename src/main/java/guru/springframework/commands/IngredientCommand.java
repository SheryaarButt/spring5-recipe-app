package guru.springframework.commands;

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
    @Builder.Default
    private UnitOfMeasureCommand unitOfMeasure = new UnitOfMeasureCommand();
}
