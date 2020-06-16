package guru.springframework.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

@Getter
@Setter
@ToString(exclude = {"recipe"})
@Entity
public class Notes extends BaseEntity{

    @OneToOne
    private Recipe recipe;

    //Storing in CLOB field in database
    @Lob
    private String recipeNotes;

}
