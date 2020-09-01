package com.huasisoft.flow;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**分组工具类
 * @author flq
 * @date 2020-08-6 14:33
 */
public class ListGroupUtil {

	/**
	 * 按多个字段分组
	 * @param list
	 * @param keyNames
	 * @return
	 * @throws Exception
	 */
    public static <T> List<ListItem<T>> groupMultiple(List<T> list, String... keyNames) throws Exception {
    	return groupMultiple(list,baseComparator(),keyNames);
    }
    
    public static <T> List<ListItem<T>> groupMultiple(List<T> list, Comparator<ListItem<T>> comparator,String... keyNames) throws Exception {
    	Map<Object,ListItem<T>> map = new HashMap<>();
        if(list != null && list.size() > 0) {
        	ListItem<T> temp = null;
        	StringBuilder  keyValue = null;
        	StringBuilder  keyNameSb = new StringBuilder("");
        	for (String string : keyNames) {
        		keyNameSb.append(":");
        		keyNameSb.append(string);
			}
        	String keyNameStr = keyNameSb.toString();
            for(T t : list) {
            	keyValue = new StringBuilder ("");
            	for (String keyName : keyNames) {
            		Field field = t.getClass().getDeclaredField(keyName);//获取私有字段
                 	field.setAccessible(true);//设置权限
                 	Object fieldValue = field.get(t);//该参数为想要获取值得对象
                 	
                 	keyValue.append(":");
                 	if(fieldValue != null) {
                 		keyValue.append("NULL");
                    }else {
                    	keyValue.append(fieldValue);
                    }
				}
                temp = map.get(keyValue.toString());
                if(temp == null) {
                	temp = new ListItem<>();
                    temp.setKeyName(keyNameStr);
                    temp.setKeyValue(keyValue);
                    map.put(keyValue, temp);
                }
                temp.getData().add(t);
            }
        }
        List<ListItem<T>> result = new ArrayList<>(map.values());
        Collections.sort(result,comparator);
        return result;
    }
    
    
    

    /**
     * 按一个字段分组
     * @param list
     * @param keyName
     * @return
     * @throws Exception
     */
    public static <T> List<ListItem<T>> group(List<T> list, String keyName) throws Exception {
    	return group(list,baseComparator(),keyName);
    }
    public static <T> List<ListItem<T>> group(List<T> list, Comparator<ListItem<T>> comparator,String keyName) throws Exception{
        Map<Object,ListItem<T>> map = new HashMap<>();
        if(list != null && list.size() > 0) {
        	ListItem<T> temp = null;
            for(T t : list) {
                Field field = t.getClass().getDeclaredField(keyName);//获取私有字段
            	field.setAccessible(true);//设置权限
            	Object fieldValue = field.get(t);//该参数为想要获取值得对象
                if(fieldValue == null) {
                    continue;
                }
                temp = map.get(fieldValue);
                if(temp == null) {
                	temp = new ListItem<>();
                    temp.setKeyName(keyName);
                    temp.setKeyValue(fieldValue);
                    map.put(fieldValue, temp);
                }
                temp.getData().add(t);
            }
        }
        List<ListItem<T>> result = new ArrayList<>(map.values());
        
        Collections.sort(result,comparator);
        return result;
    }
    
    public static <T> Comparator<ListItem<T>>  baseComparator() {
    	return new Comparator<ListItem<T>>() {
        	public int compare(ListItem<T> o1, ListItem<T> o2) {
                return o2.getSize() - o1.getSize();
            }
    	};
    }
    public static class ListItem<T> implements Serializable{
    	
		private static final long serialVersionUID = 1L;
		
		private String name;
    	private List<T> data;
    	private String keyName;
    	private Object keyValue;
    	
    	public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<T> getData() {
			return data==null?new ArrayList<>():data;
		}

		public void setData(List<T> data) {
			this.data = data;
		}

		public Integer getSize() {
			return this.getData().size();
		}

		public String getKeyName() {
			return keyName;
		}

		public void setKeyName(String keyName) {
			this.keyName = keyName;
		}

		public Object getKeyValue() {
			return keyValue;
		}

		public void setKeyValue(Object keyValue) {
			this.keyValue = keyValue;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

    }
    
public static class ListItemTree<T> implements Serializable,Comparable<ListItemTree<T>>{
    	
		private static final long serialVersionUID = 1L;
		
		private String name;
    	private List<T> data;
    	private String keyName;
    	private Object keyValue;
    	private List<ListItemTree<T>> childs;
    	
    	public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<T> getData() {
			return data==null?new ArrayList<>():data;
		}

		public void setData(List<T> data) {
			this.data = data;
		}

		public int getSize() {
			if(childs!=null) {
				return childs.size();
			}
			if(data!=null) {
				return data.size();
			}
			return 0;
		}

		public String getKeyName() {
			return keyName;
		}

		public void setKeyName(String keyName) {
			this.keyName = keyName;
		}

		public Object getKeyValue() {
			return keyValue;
		}

		public void setKeyValue(Object keyValue) {
			this.keyValue = keyValue;
		}

		public List<ListItemTree<T>> getChilds() {
			return childs;
		}

		public void setChilds(List<ListItemTree<T>> childs) {
			this.childs = childs;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		@Override
		public int compareTo(ListItemTree<T> o) {
			return o.getSize()-this.getSize();
		}
		
    }
}
