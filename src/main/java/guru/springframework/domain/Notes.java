package guru.springframework.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Notes extends BaseEntity{
    //Storing in CLOB field in database
    @Lob
    private String recipeNotes;

    @Builder
    public Notes(Long id, String recipeNotes) {
        super(id);
        this.recipeNotes = recipeNotes;
    }
}
