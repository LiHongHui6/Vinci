package com.lihonghui.vinci.data.Entity;

/**
 * Created by yq05481 on 2016/12/9.
 */

public class Images {
        private String hidpi;

        private String normal;

        private String teaser;

        public void setHidpi(String hidpi){
            this.hidpi = hidpi;
        }
        public String getHidpi(){
            return this.hidpi;
        }
        public void setNormal(String normal){
            this.normal = normal;
        }
        public String getNormal(){
            return this.normal;
        }
        public void setTeaser(String teaser){
            this.teaser = teaser;
        }
        public String getTeaser(){
            return this.teaser;
        }

        @Override
        public String toString() {
            return "Images{" +
                    "hidpi='" + hidpi + '\'' +
                    ", normal='" + normal + '\'' +
                    ", teaser='" + teaser + '\'' +
                    '}';
        }

}
