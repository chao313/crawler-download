package demo.spring.boot.demospringboot.controller.wind;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Stack;


@Slf4j
public class JsonUtil {


    public static <T> T jsonToObject(String json, Class<T> clazz) {
        T obj = null;
        try {
            obj = (T) JSON.parseObject(json.trim(), clazz);
        } catch (Exception e) {
            log.error("json转换错误，参数：" + json);
        }
        return obj;
    }

    public static String objectToJson(Object obj) {
        return objectToJson(obj, null);
    }


    public static String objectToJson(Object obj, String charsetName) {
        String json = StringUtils.EMPTY;
        try {
            json = JSON.toJSONString(obj);
            if (StringUtils.isNotEmpty(charsetName)) {
                json = new String(json.getBytes(), charsetName);
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
        return json;
    }

    public static JSONArray jsonToArray(String json) {
        if (StringUtils.isEmpty(json))
            return null;

        JSONArray jsonArr = null;
        try {
            jsonArr = JSON.parseArray(json.trim());
        } catch (Exception e) {
            log.error("json转换错误，参数：" + json);
        }
        return jsonArr;
    }

    // 将字符串转换成Json：
    public static JSONObject strToJsonObj(String jsonstr) {
        if (StringUtils.isEmpty(jsonstr))
            return null;

        JSONObject jsonObj = null;
        try {
            //

            jsonObj = JSON.parseObject(jsonstr.replace("\"{", "{").replace("}\"", "}").replaceAll("\\\\", ""));
        } catch (Exception e) {
            log.error("json转换错误，参数：" + jsonstr);
        }
        return jsonObj;
    }

    /**
     * add by ljl on 20171225
     * 判断一个字符串是否满足Json的基本格式
     * 1、"{"、"}"和"["、"]"作为两个成对出现的字符
     * 2、不会出现{[}]这种错位的情况
     * 3、双引号内的内容不参加比较{"{]djsavnk"}
     *
     * @param str {["{"]}
     * @return
     */
    public static boolean isJsonStr(String str) {
        boolean ret = false;
        Stack<Character> stack = new Stack<Character>();
        if (StringUtils.isEmpty(str)) {
            return ret;
        }

        for (int x = 0; x < str.length(); x++) {
            char ch = str.charAt(x);
            char lastChar = '\0';
            if (!stack.isEmpty()) {
                lastChar = stack.lastElement();
            }

            switch (ch) {
                case '{':
                    // 上一个压入字符不为"
                    if (lastChar != '"') {
                        stack.push(ch);
                    }
                    break;
                case '}':
                    if (lastChar != '"') {
                        if (lastChar == '{') {
                            stack.pop();
                        }
                    }
                    break;
                case '[':
                    // 上一个压入字符不为"
                    if (lastChar != '"') {
                        stack.push(ch);
                    }
                    break;
                case ']':
                    if (lastChar != '"') {
                        if (lastChar == '[') {
                            stack.pop();
                        }
                    }
                    break;
                case '"':
                    if (lastChar == '"') {
                        stack.pop();
                    } else {
                        stack.push(ch);
                    }
            }
        }

        if (stack.isEmpty() && str.contains("{")) {
            // System.out.print("该字符满足Json基本条件");
            ret = true;
        } else {
            // System.out.print("该字符不满足Json基本条件");
            ret = false;
        }

        return ret;
    }

    public static void main(String[] args) {
//        Queue<String> queue = new LinkedList<String>();
//        queue.offer("Hello");
//        queue.offer("World!");
//        queue.offer("你好！");
//        System.out.println(queue.size());
//        String str;
//        while((str=queue.poll())!=null){
//            System.out.print(str);
//        }
//        System.out.println();
//        System.out.println(queue.size());
//        System.out.println(queue.poll());
//        
//        
//        Stack<Character> stack = new Stack<Character>(); 
//        stack.push('a');
//        stack.push('b');
//        stack.push('c');
//        System.out.println(stack.lastElement());
//        System.out.println(stack.lastElement());
        // String ss = "[1, {\"a\": 2},{\"a\": {}}, {\"a\": []}, {\"a\": [{}]}, {\"{[\"a\": \"\\\"2,:{}3,\" a \":33}]\"}]";
        String ss = "[[], []]";
        JsonUtil.isJsonStr(ss);
    }

}
