package guru.springframework.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Getter
@Setter
@ToString(exclude = {"recipe"})
@Entity
public class Ingredient extends BaseEntity{

    private String description;
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.EAGER)
    private UnitOfMeasure unitOfMeasure;

    @ManyToOne
    private Recipe recipe;

}
