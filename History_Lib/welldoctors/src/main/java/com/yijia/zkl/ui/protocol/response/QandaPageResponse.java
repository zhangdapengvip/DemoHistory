package com.yijia.zkl.ui.protocol.response;

import com.zero.library.base.retrofit.DefaultResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ZeroAries on 2016/3/22.
 * 专家在线
 */
public class QandaPageResponse extends DefaultResponse {
    private Page page;

    public class Page {
        private List<Rows> rows;

        public class Rows implements Serializable {
            private String pk_qanda;//主键
            private String pk_onlineqa;//在线问答主键
            private String pk_user;//用户主键
            private String question;//问题
            private String answer;//回答

            public String getPk_qanda() {
                return pk_qanda;
            }

            public void setPk_qanda(String pk_qanda) {
                this.pk_qanda = pk_qanda;
            }

            public String getPk_onlineqa() {
                return pk_onlineqa;
            }

            public void setPk_onlineqa(String pk_onlineqa) {
                this.pk_onlineqa = pk_onlineqa;
            }

            public String getPk_user() {
                return pk_user;
            }

            public void setPk_user(String pk_user) {
                this.pk_user = pk_user;
            }

            public String getQuestion() {
                return question;
            }

            public void setQuestion(String question) {
                this.question = question;
            }

            public String getAnswer() {
                return answer;
            }

            public void setAnswer(String answer) {
                this.answer = answer;
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
