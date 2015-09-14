package com.pascalabs.util.log.model;

import java.io.Serializable;

public class BeanLogAPI
    implements Serializable {

    private static final long serialVersionUID = 8130623756360893148L;
    private String params, method,header,fileparam,byteparam,url, statuscode, content;
    private Class shortenClassName;

    public BeanLogAPI() {}

    public Class getShortenClassName() {
        return shortenClassName;
    }

    public void setShortenClassName(Class shortenClassName) {
        this.shortenClassName = shortenClassName;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFileparam() {
        return fileparam;
    }

    public void setFileparam(String fileparam) {
        this.fileparam = fileparam;
    }

    public String getByteparam() {
        return byteparam;
    }

    public void setByteparam(String byteparam) {
        this.byteparam = byteparam;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
