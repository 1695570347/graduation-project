package vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlateVo {
    private Integer pageNum;
    private Integer pageSize;
    private String nickname;
    private String pname;
    private String ptitle;
}
