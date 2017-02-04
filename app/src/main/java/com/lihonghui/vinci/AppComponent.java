package com.lihonghui.vinci;

import dagger.Component;

/**
 * Created by yq05481 on 2016/10/25.
 */

@Component(modules = {AppMoudle.class})
public interface AppComponent {
    void inject(ExperimentActivity mainActivity);
}
