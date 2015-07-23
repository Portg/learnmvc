package struts.config.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import struts.action.ActionMapping;

public class XmlParser {

    private static String config = "/WEB-INF/struts-config.xml";

    private static Map<String, String> formBeanMap = new HashMap<String, String>();

    private static Map<String, ActionMapping> actionMap = new HashMap<String, ActionMapping>();

    /** 
     * 根据xml文件路径取得document对象 
     * @param xmlPath 
     * @return 
     * @throws DocumentException 
     */
    public static Document getDocument(String xmlPath) {

        if(xmlPath==null || xmlPath.equals("")) {
            return null;
        }

        File file = new File(xmlPath);
        if(file.exists() == false) {
            return null;
        }
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(xmlPath);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }

    /** 
     *  
     * @方法功能描述:获取指定节点指定属性的值 
     * @方法名:attrValue 
     * @param e 
     * @param attrName 
     * @返回类型：String 
     * @时间：2011-4-14下午02:36:48 
     */
    public static String attrValue(Element e,String attrName) {
        attrName = attrName.trim();
        if(e == null) {
            return null;
        }
        if (attrName== null || attrName.equals("")) {
            return null;
        }
        return e.attributeValue(attrName);
    }

    /** 
     *  
     * @方法功能描述：得到指定节点的属性的迭代器 
     * @方法名:getAttrIterator 
     * @param e 
     * @返回类型：Iterator<Attribute> 
     * @时间：2011-4-14下午01:42:38 
     */
    @SuppressWarnings("unchecked")  
    public static Iterator<Attribute> getAttrIterator(Element e) {
        if(e==null) {
            return null;
        }
        Iterator<Attribute> attrIterator = e.attributeIterator();
        return attrIterator;
    }

    /** 
     *  
     * @方法功能描述：遍历指定节点的所有属性 
     * @方法名:getAttributeList 
     * @param e 
     * @return 节点属性的list集合 
     * @返回类型：List<Attribute> 
     * @时间：2011-4-14下午01:41:38 
     */
    public static List<Attribute> getAttributeList(Element e) {
        if(e==null) {
            return null;
        }
        List<Attribute> attributeList = new ArrayList<Attribute>();
        Iterator<Attribute> atrIterator = getAttrIterator(e);
        if(atrIterator == null) {
            return null;
        }
        while (atrIterator.hasNext()) {
            Attribute attribute = atrIterator.next();
            attributeList.add(attribute);
        }
        return attributeList;
    }

    /** 
     *  
     * @方法功能描述：得到指定节点的所有属性及属性值 
     * @方法名:getNodeAttrMap 
     * @return 属性集合 
     * @返回类型：Map<String,String> 
     * @时间：2011-4-15上午10:00:26 
     */
    public static Map<String,String> getNodeAttrMap(Element e) {
        Map<String,String> attrMap = new HashMap<String, String>();
        if (e == null) {
            return null;
        }
        List<Attribute> attributes = getAttributeList(e);
        if (attributes == null) {
            return null;
        }
        for (Attribute attribute:attributes) {
            String attrValueString = attrValue(e, attribute.getName());
            attrMap.put(attribute.getName(), attrValueString);
        }
        return attrMap;
    }

    /** 
     *  
     * @方法功能描述：得到根节点 
     * @方法名:getRootEleme 
     * @param DOC对象 
     * @返回类型：Element 
     * @时间：2011-4-8下午12:54:02 
     */
    public static Element getRootNode(Document document) {
        if(document==null) {
            return null; 
        }
        Element root = document.getRootElement();
        return root;
    }

    /** 
     *  
     * @方法功能描述:得到指定元素的迭代器 
     * @方法名:getIterator 
     * @param parent 
     * @返回类型：Iterator<Element> 
     * @时间：2011-4-14上午11:29:18 
     */
    @SuppressWarnings("unchecked")
    public static Iterator<Element> getIterator(Element parent) {
        if(parent == null)
            return null;
        Iterator<Element> iterator = parent.elementIterator();
        return iterator;
    }

    /** 
     *  
     * @方法功能描述：获取某节点下子节点列表
     * @方法名:getChildList 
     * @param node 
     * @return @参数描述 : 
     * @返回类型：List<Element> 
     * @时间：2011-4-14下午12:21:52 
     */
    public static  List<Element> getChildList(Element node) {
        if (node==null) {
            return null;
        }
        Iterator<Element> itr = getIterator(node);
        if(itr==null) {
            return null;
        }
        List<Element> childList = new ArrayList<Element>();
        while(itr.hasNext()) {
            Element kidElement = itr.next();
            if(kidElement!=null){
                childList.add(kidElement);
            }
        }
        return childList;
    }

    public static Map<String, ActionMapping> parse(String xmlPath) {
        if (xmlPath == null || xmlPath.equals("")) {
            xmlPath = config;
        }
        Document document = null;
        document = getDocument(xmlPath);
        parseFormBean(document);
        parseActionMapping(document);
        return actionMap;
    }

    private static void parseActionMapping(Document document){
        Element root = getRootNode(document);
        Element actions = root.element("action-mappings");
        List<Element> actionList = getChildList(actions);
        for(Element action : actionList){
            String name = attrValue(action, "name");
            String type = attrValue(action, "type");
            String path = attrValue(action, "path");
            ActionMapping actionMapping = new ActionMapping();
            actionMapping.setName(name);
            actionMapping.setType(type);
            actionMapping.setPath(path);
            String formType = formBeanMap.get(name);
            if(formType != null){
                actionMapping.setFormType(formType);
            }

            List<Element> forwardList = getChildList(action);
            Map<String, String> forwardMap = new HashMap<String, String>();
            for(Element forward : forwardList){
                String forwardName = attrValue(forward, "name");
                String forwardValue = attrValue(forward, "value");
                forwardMap.put(forwardName, forwardValue);
            }
            actionMapping.setForwardProperties(forwardMap);
            actionMap.put(path, actionMapping);
        }
    }

    /** 
     *  
     * @方法功能描述：解析form-bean
     * @方法名:parseFormBean 
     * @param document 
     */
    private static void parseFormBean(Document document){
        Element root = getRootNode(document);
        Element form = root.element("form-beans");
        List<Element> forms = getChildList(form);
        for(Element f : forms){
            String name = attrValue(f, "name");
            String clazz = attrValue(f, "type");
            formBeanMap.put(name, clazz);
        }
    }
}
