package hwagae.psp.entity;

import hwagae.psp.dto.request.RequestProblemDto;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@Entity
@Getter
@DiscriminatorValue("multiple")
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class MultipleProblem extends Problem {
    List<Integer> selectNum;//객관식 번호
    List<String> selectContent;//객관식 보긴ㅇ
}