package guru.springframework.commands;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UnitOfMeasureCommand implements Comparable<UnitOfMeasureCommand> {
    private Long id;
    private String uom;

    @Override
    public int compareTo(UnitOfMeasureCommand unitOfMeasureCommand) {
        return uom.compareToIgnoreCase(unitOfMeasureCommand.uom);
    }
}
