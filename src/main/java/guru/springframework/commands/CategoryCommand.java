package guru.springframework.commands;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCommand {
    private Long id;
    private String categoryName;
}
