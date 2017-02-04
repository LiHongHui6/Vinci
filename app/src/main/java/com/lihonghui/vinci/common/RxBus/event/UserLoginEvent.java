package com.lihonghui.vinci.common.RxBus.event;

import com.lihonghui.vinci.data.Entity.User;

/**
 * Created by yq05481 on 2016/11/29.
 */

public class UserLoginEvent {
    private User mUser;

    public User getmUser() {
        return mUser;
    }

    public UserLoginEvent(User mUser) {
        this.mUser = mUser;
    }
}
