package guru.springframework.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Getter
@Setter
@ToString(exclude = {"recipe"})
@NoArgsConstructor
@Entity
public class Ingredient extends BaseEntity{

    private String description;
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.EAGER)
    private UnitOfMeasure unitOfMeasure;

    @ManyToOne
    private Recipe recipe;

    @Builder
    public Ingredient(Long id, String description, BigDecimal amount, UnitOfMeasure unitOfMeasure, Recipe recipe) {
        super(id);
        this.description = description;
        this.amount = amount;
        this.unitOfMeasure = unitOfMeasure;
        this.recipe = recipe;
    }
}
