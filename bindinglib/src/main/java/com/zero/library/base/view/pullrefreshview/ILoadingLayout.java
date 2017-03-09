package com.zero.library.base.view.pullrefreshview;

public interface ILoadingLayout {
    enum State {

        NONE,

        RESET,

        PULL_TO_REFRESH,

        RELEASE_TO_REFRESH,

        REFRESHING,

        @Deprecated
        LOADING, NO_MORE_DATA,
    }

    void setState(State state);

    State getState();

    int getContentSize();

    void onPull(float scale);
}
