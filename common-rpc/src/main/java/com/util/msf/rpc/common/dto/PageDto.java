package com.util.msf.rpc.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wencheng on 2018/11/8.
 */
public class PageDto<E> implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 6165784890597577717L;
    private static int DEFAULT_PAGE_SIZE = 20;
    /**
     * 总记录数
     */
    private int recordCount = -1;
    /**
     * 每页个数
     */
    private int pageSize;
    /**
     * 页数
     */
    private int pageCount;
    /**
     * 当前页
     */
    private int currentPage;
    /**
     * 记录列表
     */
    private List<E> list;

    public PageDto() {
        this.pageSize = DEFAULT_PAGE_SIZE;
        this.pageCount = -1;
        this.currentPage = -1;
    }

    public PageDto(int currentPage, int recordCount, List<E> list) {
        this.pageSize = DEFAULT_PAGE_SIZE;
        this.currentPage = currentPage;
        this.recordCount = recordCount;
        this.list = list;
        rePage();
    }

    public PageDto(int pageSize, int currentPage, int recordCount, List<E> list) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.recordCount = recordCount;
        this.list = list;
        rePage();
    }

    public PageDto(int currentPage, int pageSize) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }

    public static <E> PageDto<E> of(int currentPage, int recordCount, List<E> list) {
        return new PageDto<E>(currentPage, recordCount, list);
    }

    public static <E> PageDto<E> of(int pageSize, int currentPage, int recordCount, List<E> list) {
        return new PageDto<E>(pageSize, currentPage, recordCount, list);
    }


    /**
     * 设置总记录数，并重新分页
     *
     * @param recordCount
     */
    public PageDto<E> with(int recordCount) {
        this.recordCount = recordCount;
        rePage();
        return this;
    }

    /**
     * 设置记录列表
     *
     * @param list
     * @return
     */
    public PageDto<E> with(List<E> list) {
        this.list = list;
        return this;
    }

    /**
     * 获取每页个数
     *
     * @return
     */
    public int getPageSize() {
        return this.pageSize < 1 ? DEFAULT_PAGE_SIZE : this.pageSize;
    }

    /**
     * 设置每页个数
     *
     * @param pageSize
     */
    public PageDto setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    /**
     * 获取页数
     *
     * @return
     */
    public int getPageCount() {
        return this.pageCount;
    }

    /**
     * 设置页数
     *
     * @param pageCount
     */
    public PageDto setPageCount(int pageCount) {
        this.pageCount = pageCount;
        return this;
    }

    /**
     * 获取当前页
     *
     * @return
     */
    public int getCurrentPage() {
        return this.currentPage;
    }

    /**
     * 设置当前页
     *
     * @param currentPage
     */
    public PageDto setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        return this;
    }

    public List<E> getList() {
        return list;
    }

    public PageDto setList(List<E> list) {
        this.list = list;
        return this;
    }

    public PageDto rePage() {
        if (this.pageSize * (this.currentPage - 1) > this.recordCount) {
            this.setCurrentPage((int) Math.ceil((double) this.recordCount / (double) this.pageSize));
        }

        if (this.pageSize == 0) {
            this.setPageSize(DEFAULT_PAGE_SIZE);
        }

        this.setPageCount((int) Math.ceil((double) this.recordCount / (double) this.pageSize));
        return this;
    }

    public void rePageCount(int recordCount, int currentPage, int pageSize) {
        this.recordCount = recordCount;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        if (pageSize * (currentPage - 1) > recordCount) {
            setCurrentPage((int) Math.ceil((double) recordCount / (double) pageSize));
        }
        if (pageSize == 0) {
            setPageSize(DEFAULT_PAGE_SIZE);
        }
        setPageCount((int) Math.ceil((double) recordCount / (double) pageSize));
    }

    /**
     * 内存分页：获取list的分页查询结果 <br/>
     *
     * @param list
     * @param pageIndex
     * @param pageSize
     * @return PageDto<T>
     * @author wei.guo
     * @created 2016-09-01
     * @since JDK 1.8
     */
    public static <T> PageDto<T> of(List<T> list, int pageIndex, int pageSize) {
        PageDto<T> pageDto = new PageDto<>();
        if (list == null || list.size() == 0) {
            pageDto.setList(new ArrayList<>());
            pageDto.rePageCount(0, pageIndex, pageSize);
        }
        int fromIndex = (pageIndex - 1) * pageSize;
        if (fromIndex > list.size() - 1) {
            pageDto.setList(new ArrayList<>());
        } else {
            int toIndex = pageIndex * pageSize;
            if (toIndex > list.size()) {
                toIndex = list.size();
            }
            pageDto.setList(list.subList(fromIndex, toIndex));
        }
        pageDto.rePageCount(list.size(), pageIndex, pageSize);
        return pageDto;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    @Override
    public String toString() {
        return "PageDto{" +
                "recordCount=" + recordCount +
                ", pageSize=" + pageSize +
                ", pageCount=" + pageCount +
                ", currentPage=" + currentPage +
                ", list=" + list +
                '}';
    }
}
