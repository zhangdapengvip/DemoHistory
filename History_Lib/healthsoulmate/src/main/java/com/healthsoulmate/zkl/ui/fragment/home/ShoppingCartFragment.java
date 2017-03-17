package com.healthsoulmate.zkl.ui.fragment.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.FragmentShoppingCartBinding;
import com.healthsoulmate.zkl.databinding.ItemSellerBinding;
import com.healthsoulmate.zkl.ui.activity.ConformOrderActivity;
import com.healthsoulmate.zkl.ui.activity.ProductDetailsActivity;
import com.healthsoulmate.zkl.ui.bean.ChangeInfo;
import com.healthsoulmate.zkl.ui.bean.bus.CartBus;
import com.healthsoulmate.zkl.ui.manager.ShoppingCartManager;
import com.healthsoulmate.zkl.ui.manager.UserManager;
import com.healthsoulmate.zkl.ui.protocol.ProtocolImp;
import com.healthsoulmate.zkl.ui.protocol.request.PageShoppingGoodsRequest;
import com.healthsoulmate.zkl.ui.protocol.request.ShoppingcartRemoveRequest;
import com.healthsoulmate.zkl.ui.protocol.request.ShoppingcartUpdateRequest;
import com.healthsoulmate.zkl.ui.protocol.response.LoginResponse;
import com.healthsoulmate.zkl.ui.protocol.response.PageShoppingGoodsResponse;
import com.healthsoulmate.zkl.ui.protocol.response.ProductBean;
import com.healthsoulmate.zkl.ui.protocol.response.SellerBean;
import com.zero.library.base.AppToast;
import com.zero.library.base.bus.RxBus;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseFragment;
import com.zero.library.base.utils.UtilsDate;
import com.zero.library.base.utils.UtilsString;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.utils.picasso.UtilsPicasso;
import com.zero.library.base.view.AppAlertDialog;
import com.zero.library.base.view.AppEmptyDialog;
import com.zero.library.base.view.pullrefreshview.PullToRefreshBase;
import com.zero.library.base.view.pullrefreshview.PullToRefreshExpandableListView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;


/**
 * Created by ZeroAries on 2016/4/21.
 * 购物车
 */
public class ShoppingCartFragment extends AppBaseFragment {
    protected MyAdapter mAdapter;
    protected PullToRefreshExpandableListView mRefreshList;
    private FragmentShoppingCartBinding mBinding;
    private int mTextContentWidth;
    private ExpandableListView mListView;
    private AppAlertDialog mDeleteDialog;

    @Override
    protected int getResLayout() {
        return R.layout.fragment_shopping_cart;
    }

    @Override
    protected void initView(View view, ViewDataBinding inflate) {
        mBinding = (FragmentShoppingCartBinding) inflate;
        mRefreshList = mBinding.pullRefreshList;
        mBinding.titleBar.setTitle(R.string.title_shopping_cart);
        if (null != mArguments) {
            mBinding.titleBar.setLeftText(R.string.back);
            mBinding.titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        }
        mBinding.btnPay.setOnClickListener(v -> {
            List<SellerBean> itemList = mAdapter.getItemList();
            ArrayList<ProductBean> buyList = new ArrayList<>();
            for (SellerBean sellerInfo : itemList) {
                for (ProductBean productInfo : sellerInfo.getItems()) {
                    if (ProductBean.STATE_BUY.equals(productInfo.getIsBuy())) {
                        buyList.add(productInfo);
                    }
                }
            }
            if (buyList.size() <= 0) {
                AppToast.show(mActivity, R.string.toast_product_empty);
                return;
            }
            if (!UserManager.checkLogin(mActivity)) return;
            Intent intent = new Intent(mActivity, ConformOrderActivity.class);
            intent.putParcelableArrayListExtra(AppConstants.PARCELABLE_KEY, buyList);
            JumpManager.doJumpForward(mActivity, intent);
        });
        mBinding.cbCheckAll.setOnClickListener(v -> {
            ChangeInfo info = new ChangeInfo();
            info.setIsBuy(mBinding.cbCheckAll.isChecked() ? ProductBean.STATE_BUY : ProductBean.STATE_UNBUY);
            info.setPkShoppingcart(ProductBean.STATE_ALL);
            List<SellerBean> itemList = mAdapter.getItemList();
            if (!UserManager.isLogin()) {
                for (SellerBean sellerInfo : itemList) {
                    for (ProductBean productInfo : sellerInfo.getItems()) {
                        productInfo.setIsBuy(mBinding.cbCheckAll.isChecked() ? ProductBean.STATE_BUY :
                                ProductBean.STATE_UNBUY);
                    }
                }
            }
            requestNumChange(itemList, info);
        });
        measuredTextContentWidth();
        initExpandList();
    }

    private void initExpandList() {
        mListView = mRefreshList.getRefreshableView();
        mListView.setGroupIndicator(null);
        mListView.setOnGroupClickListener((parent, v, groupPosition, id) -> true);
        mListView.setDivider(null);
        mListView.setChildDivider(null);
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
        mRefreshList.setPullRefreshEnabled(true);
        mRefreshList.setPullLoadEnabled(false);
        mRefreshList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ExpandableListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                requestList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {

            }
        });
    }

    /**
     * 请求购物车列表
     */
    private void requestList() {
        LoginResponse loginInfo = UserManager.getLoginInfo(mActivity, false);
        PageShoppingGoodsRequest request = new PageShoppingGoodsRequest();
        if (null == loginInfo) {
            request.setItems(ShoppingCartManager.productList);
        } else {
            request.setPkUser(loginInfo.getDatas().getPkUser());
        }
        Observable<PageShoppingGoodsResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class)
                .pageShoppingGoodsRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<PageShoppingGoodsResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(PageShoppingGoodsResponse response) {
                        fillList(response);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinish(boolean isSuccess) {
                        pullDownComplete();
                    }
                });
    }


    /**
     * 修改状态
     *
     * @param itemList
     * @param info
     */
    private void requestNumChange(List<SellerBean> itemList, ChangeInfo info) {
        LoginResponse loginInfo = UserManager.getLoginInfo(mActivity, false);
        ShoppingcartUpdateRequest request = new ShoppingcartUpdateRequest();
        if (null == loginInfo) {
            request.setItems(ShoppingCartManager.getProductList(itemList));
        } else {
            request.setPkUser(loginInfo.getDatas().getPkUser());
            request.setBuynum(info.getBuynum());
            request.setIsBuy(info.getIsBuy());
            request.setPkSeller(info.getPkSeller());
            request.setPkShoppingcart(info.getPkShoppingcart());
        }
        Observable<PageShoppingGoodsResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class)
                .shoppingcartUpdateRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<PageShoppingGoodsResponse>() {
                    @Override
                    public void beforRequest() {
                        showProgressDialog(false);
                    }

                    @Override
                    public void onSuccess(PageShoppingGoodsResponse response) {
                        fillList(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onFinish(boolean isSuccess) {
                        hidenProgressDialog();
                    }
                });
    }

    private void fillList(PageShoppingGoodsResponse response) {
        PageShoppingGoodsResponse.DatasBean datas = response.getDatas();
        boolean isChecked = true;
        UtilsUi.setVisibility(mBinding.tvEmpty, datas.getSellers().size() <= 0);
        UtilsUi.setVisibility(mBinding.llPayContent, datas.getSellers().size() > 0);
        if (datas.getSellers().size() <= 0) isChecked = false;
        for (SellerBean sellerInfo : datas.getSellers()) {
            for (ProductBean productInfo : sellerInfo.getItems()) {
                if (ProductBean.STATE_UNBUY.equals(productInfo.getIsBuy())) {
                    isChecked = false;
                    break;
                }
            }
        }
        mBinding.cbCheckAll.setChecked(isChecked);
        RxBus.getInstance().send(new CartBus(datas.getCountNum()));
        String totalMoney = datas.getTotalMoney();
        if (TextUtils.isEmpty(totalMoney)) totalMoney = "0";
        mBinding.tvTotlePrice.setText(mActivity.getString(R.string.format_total_price,
                UtilsString.formatPrice(Double.valueOf(totalMoney))));
        float textSize = mBinding.tvPrice.getTextSize();
        UtilsUi.resizeTextView(mBinding.tvPrice, mActivity.getString(R.string.format_all_price,
                UtilsString.formatPrice(Double.valueOf(totalMoney))), textSize, mTextContentWidth);

        mAdapter.setItemList(datas.getSellers());
        mAdapter.notifyDataSetChanged();
        int groupCount = mAdapter.getGroupCount();
        for (int item = 0; item < groupCount; item++) {
            mListView.expandGroup(item);
        }
        if (!UserManager.isLogin()) ShoppingCartManager.asyncFromNet(datas.getSellers());
    }

    private void requestDelete(ProductBean productInfo) {
        LoginResponse loginInfo = UserManager.getLoginInfo(mActivity, false);
        ShoppingcartRemoveRequest request = new ShoppingcartRemoveRequest();
        if (null == loginInfo) {
        } else {
            request.setPkUser(loginInfo.getDatas().getPkUser());
            request.setPkShoppingcart(productInfo.getPkShoppingcart());
        }
        Observable<PageShoppingGoodsResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class)
                .shoppingcartRemoveRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<PageShoppingGoodsResponse>() {
                    @Override
                    public void beforRequest() {
                        showProgressDialog(false);
                    }

                    @Override
                    public void onSuccess(PageShoppingGoodsResponse response) {
                        fillList(response);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinish(boolean isSuccess) {
                        hidenProgressDialog();
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        mRefreshList.perfectPullRefresh();
    }


    class MyAdapter extends BaseExpandableListAdapter {

        private List<SellerBean> itemList = new ArrayList<>();
        private int currentNum;
        private static final int MAX_COUNT = 200;
        private static final int MIN_COUNT = 1;

        public List<SellerBean> getItemList() {
            return itemList;
        }

        public void setItemList(List<SellerBean> itemList) {
            this.itemList = itemList;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public int getGroupCount() {
            return itemList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return itemList.get(groupPosition).getItems().size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return itemList.get(groupPosition);
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            ItemSellerBinding group = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.item_seller,
                    null, false);
            SellerBean sellerInfo = (SellerBean) getGroup(groupPosition);
            List<ProductBean> items = sellerInfo.getItems();
            boolean isCheck = true;
            for (ProductBean item : items) {
                if (ProductBean.STATE_UNBUY.equals(item.getIsBuy())) {
                    isCheck = false;
                    break;
                }
            }
            group.cbCheckItem.setChecked(isCheck);
            group.tvSellerName.setText(sellerInfo.getSellerName());
            group.cbCheckItem.setOnClickListener(v -> {
                ChangeInfo info = new ChangeInfo();
                info.setIsBuy(group.cbCheckItem.isChecked() ? ProductBean.STATE_BUY : ProductBean.STATE_UNBUY);
                info.setPkShoppingcart(ProductBean.STATE_ALL);
                info.setPkSeller(sellerInfo.getPkSeller());
                if (!UserManager.isLogin()) {
                    for (ProductBean item : items) {
                        item.setIsBuy(group.cbCheckItem.isChecked() ? ProductBean.STATE_BUY :
                                ProductBean.STATE_UNBUY);
                    }
                }
                requestNumChange(itemList, info);
            });
            return group.getRoot();
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return itemList.get(groupPosition).getItems().get(childPosition);
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                                 View convertView, ViewGroup parent) {
            ExpandHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.item_seller_child, null);
                holder = new ExpandHolder();
                holder.bindingContent = convertView.findViewById(R.id.binding_content);
                holder.cbCheckItem = (CheckBox) convertView.findViewById(R.id.cb_check_item);
                holder.ivNews = (ImageView) convertView.findViewById(R.id.iv_news);
                holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
                holder.btnMinus = (Button) convertView.findViewById(R.id.btn_minus);
                holder.tvCount = (TextView) convertView.findViewById(R.id.tv_count);
                holder.btnAdd = (Button) convertView.findViewById(R.id.btn_add);
                convertView.setTag(holder);
            } else {
                holder = (ExpandHolder) convertView.getTag();
            }
            ProductBean productInfo = (ProductBean) getChild(groupPosition, childPosition);
            UtilsPicasso.loadRoundImage(mActivity, productInfo.getImageurl(), R.drawable.ic_default_img, holder.ivNews,
                    R.dimen.dimen_90, 10);
            holder.bindingContent.setOnClickListener(v -> {
                Intent detailIntent = new Intent(mActivity, ProductDetailsActivity.class);
                detailIntent.putExtra(AppConstants.PARCELABLE_KEY, productInfo);
                detailIntent.putExtra(AppConstants.EXTRA_BOOLEAN, true);
                JumpManager.doJumpForward(mActivity, detailIntent);
            });
            holder.bindingContent.setOnLongClickListener(v -> {
                mDeleteDialog = UtilsUi.getNoticeDialog(mActivity, "提示", "选择您的操作", "删除", "取消", (dialog, view) -> {
                    if (AppEmptyDialog.BtnView.LEFT == view) {
                        if (UserManager.isLogin()) {
                            requestDelete(productInfo);
                        } else {
                            ((SellerBean) getGroup(groupPosition)).getItems().remove(productInfo);
                            requestNumChange(itemList, new ChangeInfo());
                        }
                    }
                    dialog.dismiss();
                });
                mDeleteDialog.show();
                return true;
            });
            String price = mActivity.getString(R.string.format_price,
                    UtilsString.formatPrice(Double.valueOf(productInfo.getPrice())));
            CharSequence charSequence = UtilsString.textLadderString(price, ".", 1.0f, 0.8f);
            holder.tvPrice.setText(charSequence);
            holder.cbCheckItem.setChecked(ProductBean.STATE_BUY.equals(productInfo.getIsBuy()));
            holder.tvTitle.setText(productInfo.getGoodsname());
            holder.tvCount.setText(String.valueOf(productInfo.getBuynum()));

            holder.tvCount.setOnClickListener(null);
            holder.btnAdd.setOnClickListener(v -> {
                currentNum = productInfo.getBuynum();
                if (currentNum < MAX_COUNT) {
                    currentNum++;
                    checkState(currentNum, holder.btnAdd, holder.btnMinus);
                    ChangeInfo info = new ChangeInfo();
                    info.setPkShoppingcart(productInfo.getPkShoppingcart());
                    info.setBuynum(currentNum);
                    info.setIsBuy(productInfo.getIsBuy());
                    info.setPkSeller(productInfo.getPkSeller());
                    if (!UserManager.isLogin()) productInfo.setBuynum(currentNum);
                    requestNumChange(itemList, info);
                }
            });
            holder.btnMinus.setOnClickListener(v -> {
                currentNum = productInfo.getBuynum();
                if (currentNum > MIN_COUNT) {
                    currentNum--;
                    checkState(currentNum, holder.btnAdd, holder.btnMinus);
                    ChangeInfo info = new ChangeInfo();
                    info.setPkShoppingcart(productInfo.getPkShoppingcart());
                    info.setBuynum(currentNum);
                    info.setIsBuy(productInfo.getIsBuy());
                    info.setPkSeller(productInfo.getPkSeller());
                    if (!UserManager.isLogin()) productInfo.setBuynum(currentNum);
                    requestNumChange(itemList, info);
                }
            });
            holder.cbCheckItem.setOnClickListener(v -> {
                ChangeInfo info = new ChangeInfo();
                info.setPkShoppingcart(productInfo.getPkShoppingcart());
                info.setBuynum(currentNum);
                info.setIsBuy(ProductBean.STATE_BUY.equals(productInfo.getIsBuy()) ? ProductBean.STATE_UNBUY :
                        ProductBean.STATE_BUY);
                info.setPkSeller(productInfo.getPkSeller());
                if (!UserManager.isLogin())
                    productInfo.setIsBuy(ProductBean.STATE_BUY.equals(productInfo.getIsBuy()) ?
                            ProductBean.STATE_UNBUY : ProductBean.STATE_BUY);
                requestNumChange(itemList, info);
            });
            currentNum = productInfo.getBuynum();
            checkState(currentNum, holder.btnAdd, holder.btnMinus);
            return convertView;
        }

        private void checkState(int currentCount, Button btnAdd, Button btnMinus) {
            btnAdd.setBackgroundResource(currentCount >= MAX_COUNT ?
                    R.drawable.ic_action_unadd : R.drawable.ic_action_add);
            btnMinus.setBackgroundResource(currentCount <= MIN_COUNT ?
                    R.drawable.ic_action_unminus : R.drawable.ic_action_minus);
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        public boolean hasStableIds() {
            return true;
        }
    }

    class ExpandHolder {
        public View bindingContent;
        public ImageView ivNews;
        public TextView tvPrice;
        public CheckBox cbCheckItem;
        public TextView tvTitle;
        public TextView tvCount;
        public Button btnMinus;
        public Button btnAdd;
    }

    @Override
    protected void initData() {
    }

    protected void pullDownComplete() {
        if (null != mRefreshList) {
            mRefreshList.setLastUpdatedLabel(UtilsDate.getCurrentDate(UtilsDate.FORMAT_DATE_DETAIL));
            mRefreshList.onPullDownRefreshComplete();
        }
    }

    private void measuredTextContentWidth() {
        mBinding.cbCheckAll.measure(0, 0);
        mBinding.btnPay.measure(0, 0);
        mTextContentWidth = UtilsUi.getScreenWidth(mActivity)
                - mBinding.cbCheckAll.getMeasuredWidth()
                - mBinding.btnPay.getMeasuredWidth();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mDeleteDialog) mDeleteDialog.dismiss();
    }
}