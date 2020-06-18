package guru.springframework.commands;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UnitOfMeasureCommand {
    private Long id;
    private String uom;
}
