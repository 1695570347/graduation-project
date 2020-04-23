package vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleVo {
    private Integer pageNum;
    private Integer pageSize;
    private String atitle;
    private String nickname;
    private Integer pid;
}
