 
package com.huasisoft.flow.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.SelectTag;
import org.htmlparser.tags.TextareaTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.huasisoft.flow.form.vo.HtmlElementBean;
import com.huasisoft.flow.form.vo.exception.HtmlEleRepeatException;
import com.huasisoft.flow.form.vo.exception.SmartFormExcepCodeConst;

/**
 *  HTML工具类，用来处理表单解析HTML元素
 *
 */
public class HtmlUtil {

	/*  元素名称 */
	public static final String NAME = "name" ;
	/*  元素标识 ID */
	public static final String ID = "id" ;
	/*  元素内联样式 */
	public static final String STYLE = "style" ;
	/*  元素类别 */
	public static final String TYPE = "type" ;
	/*  样式名称 */
	public static final String CLASS = "class" ;
	/*  元素值 */
	public static final String VALUE = "value" ;
	/*  文字区 */
	public static final String TEXTAREA = "textarea" ;
	/*  按钮 */
	public static final String BUTTON = "button" ;
	/*  单选按钮 */
	public static final String RADIO = "radio" ;
	/*  复选按钮 */
	public static final String CHECKBOX = "checkbox";
	/*  下拉框 */
	public static final String SELECT = "select" ;
	/*  表单提交 */
	public static final String SUBMIT = "submit" ;
	/*  表单重置 */
	public static final String RESET = "reset" ;
	
	/**
	 * 判断表单HTML内容里面,是否有名称重复的元素,无重复返回false ,如果有重复的,抛出异常
	 * 若表单内容为空，返回false
	 * @param htmlContent
	 * @param charset
	 * @throws ParserException
	 * @throws HtmlEleRepeatException
	 */
	public static void checkRepeatElement(String htmlContent,String charset) throws ParserException, HtmlEleRepeatException {
		if (StringUtils.isEmpty(htmlContent))
			return ;
		Parser parser = Parser.createParser(htmlContent, charset);
		NodeFilter inputFilter = new NodeClassFilter(InputTag.class);
		NodeFilter selectFilter = new NodeClassFilter(SelectTag.class);		
		NodeFilter textAreaFilter = new NodeClassFilter(TextareaTag.class);
		OrFilter orFilter = new OrFilter();
		orFilter.setPredicates(new NodeFilter[] { selectFilter, inputFilter , textAreaFilter });
		NodeList nodeList = parser.parse(orFilter);
		Node[] nodeArray = nodeList.toNodeArray() ;
		List<HtmlElementBean> htmlElementsList = new ArrayList<HtmlElementBean>() ;
		for(Node node : nodeArray) {
			Tag tmpTag = (Tag) node ;
			String name = tmpTag.getAttribute(NAME) ;
			String type = tmpTag.getAttribute(TYPE) ;
			
			HtmlElementBean htmlElement = new HtmlElementBean() ;
			htmlElement.setName(name) ;
			if(node instanceof SelectTag) {
				htmlElement.setType(SELECT) ;
			}else if(node instanceof TextareaTag) {
				htmlElement.setType(TEXTAREA) ;
			}else {
				htmlElement.setType(type) ;
			}
			if(StringUtils.isNotEmpty(type) && htmlElementsList.contains(htmlElement) && !type.equalsIgnoreCase(RADIO) && !type.equalsIgnoreCase(CHECKBOX) ){
				throw new HtmlEleRepeatException(SmartFormExcepCodeConst.HTML_ELE_REPEAT_EXCEP.getState(),name,"This element already exists ,smartForm can't support repetitive element in the same form :" + name );
			}
			htmlElementsList.add(htmlElement);
		}
	}

	 /**
	  *  对表单HTML内容进行解析,返回解析后的表单元素集合
	 * @param htmlContent
	 * @param charset
	 * @return
	 * @throws ParserException
	 */
	public static List<HtmlElementBean> parserHTML(String htmlContent,String charset) throws ParserException {
		if (StringUtils.isEmpty(htmlContent))
			return null;
		Parser parser = Parser.createParser(htmlContent, charset);
		OrFilter orFilter = new OrFilter();
		orFilter.setPredicates(new NodeFilter[] { new NodeClassFilter(SelectTag.class), new NodeClassFilter(InputTag.class) , new NodeClassFilter(TextareaTag.class) });
		NodeList nodeList = parser.parse(orFilter);
		List<HtmlElementBean> htmlElementsList = new ArrayList<HtmlElementBean>() ;
		
		Arrays.stream(nodeList.toNodeArray()).forEach((node)->{
			Tag tmpTag = (Tag) node ;
			String name = tmpTag.getAttribute(NAME) ;
			String type = tmpTag.getAttribute(TYPE) ;
			if(StringUtils.isEmpty(name) || BUTTON.equalsIgnoreCase(type) || SUBMIT.equalsIgnoreCase(type) || RESET.equalsIgnoreCase(type)) {
				return ;
			}
			
			HtmlElementBean htmlElement = new HtmlElementBean(name,tmpTag.getAttribute(ID),tmpTag.getAttribute(STYLE),tmpTag.getAttribute(CLASS),tmpTag.getAttribute(VALUE)) ;
			 
			if(node instanceof SelectTag) {
				htmlElement.setType(SELECT) ;
			}else if(node instanceof TextareaTag) {
				htmlElement.setType(TEXTAREA) ;
			}else {
				htmlElement.setType(type) ;
				if(StringUtils.isEmpty(type) || ( (type.equalsIgnoreCase(RADIO) || type.equalsIgnoreCase(CHECKBOX)) && htmlElementsList.contains(htmlElement)))
					return ;
			}
			htmlElementsList.add(htmlElement) ;
		});
		 
		return htmlElementsList ;
	}

}
