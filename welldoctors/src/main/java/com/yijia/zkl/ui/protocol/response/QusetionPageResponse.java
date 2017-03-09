package com.yijia.zkl.ui.protocol.response;

import com.zero.library.base.retrofit.DefaultResponse;

import java.util.List;

/**
 * Created by ZeroAries on 2016/3/22.
 * 医友提问列表
 */
public class QusetionPageResponse extends DefaultResponse {
    private Page page;

    public class Page {
        private List<Rows> rows;

        public class Rows {
            private String pkView;//主键
            private String pkUser;//视野主键
            private String pkQuestion;//用户主键
            private String questdate;//提问日期
            private String questcontent;//提问内容
            private int questnum;//同问数量
            private String questper;//同问人
            private String name;//用户姓名
            private String image;//用户头像

            public String getPkView() {
                return pkView;
            }

            public void setPkView(String pkView) {
                this.pkView = pkView;
            }

            public String getPkUser() {
                return pkUser;
            }

            public void setPkUser(String pkUser) {
                this.pkUser = pkUser;
            }

            public String getPkQuestion() {
                return pkQuestion;
            }

            public void setPkQuestion(String pkQuestion) {
                this.pkQuestion = pkQuestion;
            }

            public String getQuestdate() {
                return questdate;
            }

            public void setQuestdate(String questdate) {
                this.questdate = questdate;
            }

            public String getQuestcontent() {
                return questcontent;
            }

            public void setQuestcontent(String questcontent) {
                this.questcontent = questcontent;
            }

            public int getQuestnum() {
                return questnum;
            }

            public void setQuestnum(int questnum) {
                this.questnum = questnum;
            }

            public String getQuestper() {
                return questper;
            }

            public void setQuestper(String questper) {
                this.questper = questper;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }

        public List<Rows> getRows() {
            return rows;
        }

        public void setRows(List<Rows> rows) {
            this.rows = rows;
        }
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
