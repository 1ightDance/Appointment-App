package com.example.lightdance.appointment.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lightdance.appointment.R;

/**
 * Created by LightDance on 2017/10/14.
 */

public class UserBriflyInfoDialog extends Dialog {
    public UserBriflyInfoDialog(@NonNull Context context) {
        super(context);
    }
    //TODO 会将这个Dialog和xml文件绑好并添加设置xml中图文的方法

    public UserBriflyInfoDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context,R.style.MyDialog);
    }

    public UserBriflyInfoDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}

