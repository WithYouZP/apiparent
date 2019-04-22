package com.zp.api.common.parameter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.zp.api.common.util.ObjectUtils;


/**
 *  Title: Page.java
 *
 *  @author zp
 *
 *  Description: 分页类
 *
 *  Date:2017年11月30日
 */
public class Page<E> extends BasePage{
	private int firstEntityIndex;
	private int lastEntityIndex;

	private List<E> data;
	private int totalRecord;
	private int pageCount;
	
	public Page() {
		super();
	}

	/**
	 * @param pageSize
	 *            每页记录数
	 * @param pageNo
	 *            页号
	 */
	public Page(int pageSize, int pageNo) {
		if (pageNo > 1 && pageSize <= 0) {
			throw new IllegalArgumentException(
					"Illegal paging arguments. [pageSize=" + pageSize + ", pageIndex=" + pageNo + "]");
		}
		if (pageSize < 0){
            pageSize = 0;
        }
		if (pageNo < 1){
            pageNo = 1;
        }
		this.pageSize = pageSize;
		this.pageNo = pageNo;
		firstEntityIndex = (pageNo - 1) * pageSize;
		lastEntityIndex = pageNo * pageSize;
	}

	/**
	 * 返回每一页的大小，即每页的记录数。
	 */
	@Override
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 返回要提取的页的序号，该序号是从1开始计算的。
	 */
	@Override
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * 返回当前页中第一条记录对应的序号，该序号是从0开始计算的。<br>
	 * 注意，此处在计算firstEntityIndex是不考虑实际提取过程中当前页是否存在的。
	 */
	public int getFirstEntityIndex() {
		if (pageNo > 1 && pageSize <= 0) {
			throw new IllegalArgumentException(
					"Illegal paging arguments. [pageSize=" + pageSize + ", pageIndex=" + pageNo + "]");
		}

		if (pageSize < 0){
            return 0;
        }
		if (pageNo <= 1){
            return 0;
        }
		firstEntityIndex = (pageNo - 1) * pageSize;
		lastEntityIndex = pageNo * pageSize;
		return firstEntityIndex;
	}

	/**
	 * 返回当前页中最后一条记录对应的序号，该序号是从0开始计算的。<br>
	 * 注意，此处在计算lastEntityIndex是不考虑实际提取过程中当前页是否存在或者记录数是否可达到pageSize的。
	 */
	public int getLastEntityIndex() {
		return lastEntityIndex;
	}

	/**
	 * 设置当页数据。
	 */
	public void setData(List<E> data) {
		this.data = data;
	}

	/**
	 * 返回当页数据。
	 */
	public List<E> getData() {
		return (ObjectUtils.isNotEmpty(data)) ? data : new ArrayList<E>();
	}

	/**
	 * 获取总记录数。
	 * <p>
	 * 此处的总记录数并不是指当页数据的总数，而是指整个结果的总数。 即每一页数据累计的总数。
	 * </p>
	 */
	public int getTotalRecord() {
		return totalRecord;
	}

	/**
	 * 设置总的页数。
	 * <p>
	 * 此处的总记录数并不是指当页数据的总数，而是指整个结果的总数。即每一页数据累计的总数。
	 * </p>totalRecord总记录数
	 */
	public void setTotalRecord(int totalRecord) {
		if (totalRecord < 0) {
			throw new IllegalArgumentException("Illegal entityCount arguments. [entityCount=" + totalRecord + "]");
		}

		this.totalRecord = totalRecord;
		pageCount = ((totalRecord - 1) / pageSize) + 1;
	}

	/**
	 * 返回总的记录页数。
	 */
	public int getPageCount() {
		return pageCount;
	}

	/**
	 * 返回当页数据的迭代器。
	 */
	public Iterator<?> iterator() {
		if (ObjectUtils.isNotEmpty(data)) {
			return data.iterator();
		} else {
			return null;
		}
	}
}
