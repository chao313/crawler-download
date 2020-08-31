package demo.spring.boot.demospringboot.util;

import com.alibaba.fastjson.JSONArray;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * 这里是依赖util
 * 这里依赖各个jar
 */
public class DependUtil {


    Logger LOGGER = LoggerFactory.getLogger(DependUtil.class);

    public static <E> HashSet<E> newHashSet(E... elements) {
        HashSet<E> set = new HashSet(elements.length);
        Collections.addAll(set, elements);
        return set;
    }

    /**
     * 功能: 根据jsonPath去获取节点的数据
     *
     * @param source
     * @param jsonPath
     * @return
     */
    public static List<Object> parse(String source, String jsonPath) {
        List<Object> result = new ArrayList<>();
        Object value = JsonPath.read(source, jsonPath);
        if (value instanceof JSONArray) {
            JSONArray JSONValue = (JSONArray) value;
            result = JSONValue.toJavaList(Object.class);
            return result;
        }
        return result;
    }

    /**
     * 功能: 根据jsonPath去获取节点的数据(复用上面的方法，只写获取第一个)
     *
     * @param source
     * @param jsonPath
     * @return
     */
    public static Object parseGetJSONArray(String source, String jsonPath) {
        Object read = JsonPath.read(source, jsonPath);
        return read;
    }

    /**
     * 半角转全角
     *
     * @param input String.
     * @return 全角字符串.
     */
    public static String ToSBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);

            }
        }
        return new String(c);
    }

    /**
     * 全角转半角
     * 举例:BC2DBC.ToDBC("茨城県日立市川尻町４丁目１番１３号");
     *
     * @param input String.
     * @return 半角字符串
     */
    public static String ToDBC(String input) {

        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);

            }
        }
        String returnString = new String(c);
        return returnString;
    }

    /**
     * 把字符串转换成Document
     *
     * @param content
     * @return
     */
    public static Document parseStr2Document(String content) {
        Document document = Jsoup.parse(content, "UTF-8");
        return document;
    }

    /**
     * 根据xpath获取内容
     *
     * @param content
     * @param xPath
     * @return
     * @throws Exception
     */
    public static Object getXpathValueByXpath(String content, String xPath) throws Exception {
        org.w3c.dom.Document doc = getw3cDocument(content);
        XPath xPather = XPathFactory.newInstance().newXPath();
        QName typeName = XPathConstants.NODESET;
        Object result = xPather.evaluate(xPath, doc, typeName);
        return result;
    }

    /**
     * 把input 转成 str
     *
     * @param inputStream
     * @param charset
     * @return
     * @throws IOException
     */
    public static String InputStreamToStr(InputStream inputStream, String charset) throws IOException {
        return IOUtils.toString(inputStream, Charsets.toCharset(charset));
    }

    /**
     * 功能描述:<获取w3cdocument文件对象>
     *
     * @param htmlText
     * @return Document
     * @throws ParserConfigurationException
     * @author mxli.polly@wind.com.cn
     */
    private static org.w3c.dom.Document getw3cDocument(String htmlText) throws Exception {
        org.w3c.dom.Document dom = null;
        if (StringUtils.isNotEmpty(htmlText)) {
            HtmlCleaner hc = new HtmlCleaner();
            TagNode tn = hc.clean(htmlText);
            dom = new DomSerializer(new CleanerProperties()).createDOM(tn);
        }
        return dom;
    }

}
