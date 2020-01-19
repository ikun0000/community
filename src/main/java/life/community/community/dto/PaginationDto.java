package life.community.community.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页对象，包含了当前页数，当前页数的文章，显示分页栏，是否由上一页或下一页
 */
@Data
public class PaginationDto implements Serializable {
    /**
     * 这一页包含的问题DTO
     * 不能让它为null，只能为0
     */
    private List<QuestionDto> questionDtoList = new ArrayList<QuestionDto>();
    /**
     * 是否展示上一页按钮
     */
    private Boolean hasPrevious;
    /**
     * 是否展示首页按钮
     */
    private Boolean hasFirstPage;
    /**
     * 是否展示下一页按钮
     */
    private Boolean hasNext;
    /**
     * 是否展示尾页按钮
     */
    private Boolean hasEndPage;
    /**
     * 当前所在的页数
     */
    private Integer currentPage;
    /**
     * 展示可以直接跳转的页数
     */
    private List<Integer> currentPageList = new ArrayList<Integer>();


    /**
     * 根据参数设置页对象的属性
     * 包括上下页，首尾页，展示的页列表，当前页
     * @param totalCount        问题的总数
     * @param page              当前页
     * @param size              一页的长度，暂时没用，不敢乱删
     */
    public void setPagination(Integer totalCount, Integer page, int size) {
        // 算出总页数
        int totalPage = (int) Math.ceil(totalCount / 5.0);

        currentPage = page;
        // 总共显示小于5页跳转页
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
