package tech.fastool.all.dto;

import lombok.*;

import java.util.List;

/**
 * 员工
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class EmployeeDto {

    private String no;

    private String name;

    private List<String> positions;

    private Double salary;

}
