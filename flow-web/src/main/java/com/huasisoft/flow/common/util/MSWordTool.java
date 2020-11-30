package com.huasisoft.flow.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Bookmarks;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 * 使用POI,进行Word相关的操作
 *
 *
 *
 */
public class MSWordTool {

	/** 内部使用的文档对象 docx **/
	private XWPFDocument xwpfdocument;

	/** 内部使用的文档对象 doc **/
	private HWPFDocument hwpfdocument;

	private BookMarks bookMarks = null;

	/**
	 * 为文档设置模板
	 * 
	 * @param is
	 *            模板文件流
	 */
	public void setTemplate2007(InputStream is) {
		try {
			this.xwpfdocument = new XWPFDocument(is);
			bookMarks = new BookMarks(xwpfdocument);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 为文档设置模板
	 * 
	 * @param is
	 *            模板文件流
	 */
	public void setTemplate2003(InputStream is) {
		try {
			this.hwpfdocument = new HWPFDocument(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 为文档设置模板
	 * 
	 * @param templatePath
	 *            模板文件名称
	 */
	public void setTemplate2007(String templatePath) {
		try {
			this.xwpfdocument = new XWPFDocument(POIXMLDocument.openPackage(templatePath));
			bookMarks = new BookMarks(xwpfdocument);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void replaceText(Map<String, String> bookmarkMap, String bookMarkName) {
		// 首先得到标签
		BookMark bookMark = bookMarks.getBookmark(bookMarkName);
		// 获得书签标记的表格
		XWPFTable table = bookMark.getContainerTable();
		// 获得所有的表
		// Iterator<XWPFTable> it = document.getTablesIterator();

		if (table != null) {
			// 得到该表的所有行
			int rcount = table.getNumberOfRows();
			for (int i = 0; i < rcount; i++) {
				XWPFTableRow row = table.getRow(i);

				// 获到改行的所有单元格
				List<XWPFTableCell> cells = row.getTableCells();
				for (XWPFTableCell c : cells) {
					for (Entry<String, String> e : bookmarkMap.entrySet()) {
						if (c.getText().equals(e.getKey())) {
							// 删掉单元格内容
							c.removeParagraph(0);
							// 给单元格赋值
							c.setText(e.getValue());
						}
					}
				}
			}
		}
	}

	/**
	 * 得到所有的标签信息集合
	 *
	 * @return 缓存的标签信息集合
	 */
	public Collection<BookMark> getBookmarkList2007() {
		Collection<BookMark> bookMarkColl = bookMarks.getBookmarkList();
		try {
			xwpfdocument.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (xwpfdocument != null) {
				try {
					xwpfdocument.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return (bookMarkColl);
	}

	/**
	 * 得到所有的标签信息集合
	 *
	 * @return 缓存的标签信息集合
	 */
	public Bookmarks getBookmarkList2003() {
		Bookmarks bookmarks = hwpfdocument.getBookmarks();
		try {
			hwpfdocument.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (hwpfdocument != null) {
				try {
					hwpfdocument.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return (bookmarks);
	}
}
