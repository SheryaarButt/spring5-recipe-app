package guru.springframework.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UnitOfMeasure extends BaseEntity{
    private String uom;

    @Builder
    public UnitOfMeasure(Long id, String uom) {
        super(id);
        this.uom = uom;
    }
}
