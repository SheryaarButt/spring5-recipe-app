package guru.springframework.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

@Getter
@Setter
@ToString(exclude = {"recipe"})
@NoArgsConstructor
@Entity
public class Notes extends BaseEntity{

    @OneToOne
    private Recipe recipe;

    //Storing in CLOB field in database
    @Lob
    private String recipeNotes;

    @Builder
    public Notes(Long id, Recipe recipe, String recipeNotes) {
        super(id);
        this.recipe = recipe;
        this.recipeNotes = recipeNotes;
    }
}
