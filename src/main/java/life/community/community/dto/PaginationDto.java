package life.community.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

@Data
public class PaginationDto {
    private List<QuestionDto> questionDtoList = new ArrayList<QuestionDto>();
    private Boolean hasPrevious;
    private Boolean hasFirstPage;
    private Boolean hasNext;
    private Boolean hasEndPage;
    private Integer currentPage;
    private List<Integer> currentPageList = new ArrayList<Integer>();

    public void setPagination(Integer totalCount, Integer page, int size) {
        int totalPage = (int) Math.ceil(totalCount / 5.0);

        currentPage = page;
        int start = currentPage - 2 >= 1 ? currentPage - 2 : 1;
        int end = currentPage + 2 <= totalPage ? currentPage + 2 : totalPage;
        for (int j = start; j <= end; j++ ) {
            currentPageList.add(j);
        }


        // 是否展示上一页按钮
        if (currentPage == 1) {
            hasPrevious = false;
        } else {
            hasPrevious = true;
        }

        // 是否展示下一页按钮
        if (currentPage == totalPage) {
            hasNext = false;
        } else {
            hasNext = true;
        }


        // 是否展示首页按钮
        if (currentPageList.contains(1)) {
            hasFirstPage = false;
        } else {
            hasFirstPage = true;
        }

        // 是否展示尾页按钮
        if (currentPageList.contains(totalCount)) {
            hasEndPage = false;
        } else {
            hasEndPage = true;
        }

    }
}
