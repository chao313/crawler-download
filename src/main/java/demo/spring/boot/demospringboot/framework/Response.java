package demo.spring.boot.demospringboot.framework;

import java.util.ArrayList;
import java.util.List;

public class Response<T> {
    private String code;
    private String msg;
    private String error;
    private List<Exception> exceptions
            = new ArrayList<Exception>();
    private T content;

    public Response() {
    }

    public Response(String code, String msg, T content) {
        this.code = code;
        this.msg = msg;
        this.content = content;
    }

    public Response(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(String code, T content) {
        this.code = code;
        this.content = content;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Exception> getExceptions() {
        return exceptions;
    }

    public void addException(Exception exception) {
        this.exceptions.add(exception);
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public static Response fail(String msg) {
        return new Response(Code.System.FAIL, msg);
    }

    public static Response Ok(Object content) {
        return new Response(Code.System.OK, content);
    }
}