package com.yijia.patient.ui.protocol.response;

import com.zero.library.base.retrofit.DefaultResponse;

import java.util.List;

/**
 * Created by ZeroAries on 2016/3/22.
 * 资讯列表
 */
public class InformationPageResponse extends DefaultResponse {
    private Page page;

    public class Page {
        private List<NewsPage> rows;

        public List<NewsPage> getRows() {
            return rows;
        }

        public void setRows(List<NewsPage> rows) {
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
