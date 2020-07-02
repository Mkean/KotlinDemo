package com.mkean.demo.entity;

import java.util.List;

public class MenuBean {

    /**
     * status : 200
     * message : OK
     * data : [{"image":"http://cdnqn-test.canslife.com/canshi-local/2020/06/24/1559543574488.png","name":"除尘除螨","color":"#333333","url":"","sort":"10"},{"image":"http://cdnqn-test.canslife.com/canshi-local/2020/06/24/1559587762756.png","name":"除甲醛","color":"#333333","url":"","sort":"10"},{"image":"http://cdnqn-test.canslife.com/canshi-local/2020/06/24/1600014549665.png","name":"家电清洗","color":"#333333","url":"","sort":"10"},{"image":"http://cdnqn-test.canslife.com/canshi-local/2020/06/24/1600099446882.png","name":"家政定制","color":"#333333","url":"","sort":"10"},{"image":"http://cdnqn-test.canslife.com/canshi-local/2020/06/24/1600132307479.png","name":"家政预约","color":"#333333","url":"","sort":"10"},{"image":"http://cdnqn-test.canslife.com/canshi-local/2020/06/24/1600181063597.png","name":"经典家","color":"#333333","url":"","sort":"10"},{"image":"http://cdnqn-test.canslife.com/canshi-local/2020/06/24/1600265454510.png","name":"养老护理","color":"#333333","url":"","sort":"10"},{"image":"http://cdnqn-test.canslife.com/canshi-local/2020/06/24/1600317348417.png","name":"衣物洗护","color":"#333333","url":"","sort":"10"},{"image":"http://cdnqn-test.canslife.com/canshi-local/2020/06/24/1600383093185.png","name":"月嫂","color":"#333333","url":"","sort":"10"},{"image":"http://cdnqn-test.canslife.com/canshi-local/2020/06/24/1600533213526.png","name":"钟点住家","color":"","url":"","sort":"10"},{"image":"http://cdnqn-test.canslife.com/canshi-local/2020/06/24/1600583197256.png","name":"除甲醛","color":"#333333","url":"","sort":"10"},{"image":"http://cdnqn-test.canslife.com/canshi-local/2020/06/24/1601018986510.png","name":"家政定制","color":"#333333","url":"","sort":"10"}]
     */

    private int status;
    private String message;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * image : http://cdnqn-test.canslife.com/canshi-local/2020/06/24/1559543574488.png
         * name : 除尘除螨
         * color : #333333
         * url :
         * sort : 10
         */

        private String image;
        private String name;
        private String color;
        private String url;
        private String sort;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }
    }
}
