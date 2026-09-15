package org.hit.NCKH.domain.dto.pagination;

import org.hit.NCKH.constant.CommonConstant;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
public class PaginationSearchRequestDto extends PaginationRequestDto {

  @Parameter(description = "Keyword to search")
  private String keyword = CommonConstant.EMPTY_STRING;

  public String getKeyword() {
    return keyword.trim();
  }

}
