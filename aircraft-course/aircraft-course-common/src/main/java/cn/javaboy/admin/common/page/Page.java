package cn.javaboy.admin.common.page;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页查询时的辅助工具类
 * @author linzongxue
 */
@SuppressWarnings("rawtypes")
public class Page {
    protected int limit = 100; //每页显示条数
    protected int start = 0;  //起始行号
    protected long total = -1; //总数
    protected List result = new ArrayList(); //结果集
    protected int no = 1;   //第几页
    protected int pages = 0; //总页面码, jqGrid要求给出总页数，超级无聊

    public Page() {
        this.total = 0;
    }

    public Page(Integer pageNo, Integer pageSize) {
        this.limit = pageSize;
        setNo(pageNo);
    }

    /**
     * AllArgsConstructor
     *
     * @param start  起始行号
     * @param total  总数
     * @param no     第几页
     * @param limit  每页显示条数
     * @param pages  总页数
     * @param result 结果集
     */
    public Page(int start, long total, int no, int limit, int pages, List result) {
        this.start = start;
        this.total = total;
        this.no = no;
        this.limit = limit;
        this.pages = pages;
        this.result = result;
    }

    /**
     * Convert tk.mapper's Page Entity
     * @param page com.github.pagehelper.Page
     * @return com.hnisi.dep.base.util.Page
     */
    public static Page from(com.github.pagehelper.Page page) {
        return new Page(
                page.getStartRow(),
                page.getTotal(),
                page.getPageNum(),
                page.getPageSize(),
                page.getPages(),
                page.getResult()
        );
    }

    /**
     * 获得页内的记录列表.
     */
    public List getResult() {
        return result;
    }

    /**
     * 设置页内的记录列表.
     * @return
     */
    public Page setResult(final List result) {
        this.result = result;
        return this;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
        this.start = (no - 1) * limit;
    }

    public int getPages() {
        return (int) ((total / limit) + (total % limit > 0 ? 1 : 0));
    }
}
