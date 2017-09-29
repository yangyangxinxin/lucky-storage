package com.luckysweetheart.storage.exception;

/**
 * Created by yangxin on 2017/9/21.
 */
public class StorageException extends Exception {

    private String code;

    private String msg;

    public StorageException(){
        super();
        this.msg = "";
        this.code = "";
    }

    public StorageException(String msg){
        super(msg);
        this.msg = msg;
        this.code = "";
    }

    public StorageException(String msg,String code){
        super(msg);
        this.msg = msg;
        this.code = code;
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
}
