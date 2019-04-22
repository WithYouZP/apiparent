package com.zp.api.common.parameter;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * Title: TreeNode.java
 *
 * @author zp
 *
 *         Description: 树形数据结构
 *
 *         Date:2018年2月8日
 * @param E
 *            树节点类型
 */
public class TreeNode<E extends Serializable> {

	@ApiModelProperty(value = "树节点ID", dataType = "E", required = false)
	private E value;
	@ApiModelProperty(value = "树节点名称", dataType = "String", required = false)
	private String title;
	@ApiModelProperty(value = "子节点数据", dataType = "List", required = false)
	private List<TreeNode<E>> children;

	public TreeNode() {
		super();
	}

	public TreeNode(E value, String title) {
		this(value, title, null);
	}

	public TreeNode(E value, String title, List<TreeNode<E>> children) {
		this.value = value;
		this.title = title;
		this.children = children;
	}

	public E getValue() {
		return value;
	}

	public void setValue(E value) {
		this.value = value;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<TreeNode<E>> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode<E>> children) {
		this.children = children;
	}
}
