package com.xh.basemodule.http.callback;

import io.reactivex.functions.Consumer;

/**
 */
public abstract class ErrorConsumer implements Consumer<Throwable> {

    @Override
    public void accept(Throwable throwable) throws Exception {
        throwable.printStackTrace();
        if(throwable instanceof DqException) {
            onFailure((DqException)throwable);
        }else {
            ErrorConsumer.DqException exception = new ErrorConsumer.DqException();
//            exception.setMessage(throwable.getMessage());
            exception.setMessage("网络异常");
            exception.setSuccess(0);
            exception.setCode("");
            onFailure(exception);
        }
    }
    public  void onFailure(DqException throwable){};

    //自定义异常，可以判断token过期
    public static class DqException extends Throwable{
        private int success;

        public int getSuccess() {
            return success;
        }

        public void setSuccess(int success) {
            this.success = success;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        private String code;

        @Override
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        private String message;


    }
}
