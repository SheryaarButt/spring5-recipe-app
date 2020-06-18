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
public class Category extends BaseEntity{

    private String categoryName;

    @Builder
    public Category(Long id, String categoryName) {
        super(id);
        this.categoryName = categoryName;
    }
}
