package com.example.lightdance.appointment.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.fragments.CommentDetailFragment;
import com.example.lightdance.appointment.fragments.CommentListFragment;

/**
 * @author pope
 */
public class CommentActivity extends AppCompatActivity {

    private int mCurrentPosition = -1;
    private CommentListFragment mCommentListFragment;
    private CommentDetailFragment mCommentDetailFragment;
    private String objectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        changeFragment(1);

    }

    /**
     * 动态加载碎片的方法
     *
     * @param position 需要跳转到的碎片位置
     */
    public void changeFragment(int position) {
        if (position == mCurrentPosition) {
            return;
        }
        Fragment fragment = getFragment(position);
        if (fragment == null) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        if (fragment.isAdded()) {
            if (fragment.isHidden()) {
                transaction.show(fragment)
                        .hide(getFragment(mCurrentPosition));
            }
        } else {
            if (mCurrentPosition != -1) {
                transaction.add(R.id.container_comment_activity, fragment)
                        .hide(getFragment(mCurrentPosition));
            } else {
                transaction.add(R.id.container_comment_activity, fragment);
            }
        }
        transaction.commit();
        mCurrentPosition = position;
    }

    /**
     * 获取碎片实例方法
     *
     * @param position 传入获取碎片编号
     * @return 返回编号对应的碎片
     */
    public Fragment getFragment(int position) {
        switch (position) {
            case 1:
                if (mCommentListFragment == null) {
                    mCommentListFragment = CommentListFragment.newInstance();
                }
                return mCommentListFragment;
            case 2:
                if (mCommentDetailFragment == null) {
                    mCommentDetailFragment = CommentDetailFragment.newInstance();
                }
                return mCommentDetailFragment;
            default:
                return null;
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (mCurrentPosition == 2){
            changeFragment(1);
        }
        return super.onKeyUp(keyCode, event);
    }

    public void setObjectId(String id){
        objectId = id;
    }

    public String getObjectId(){
        return objectId;
    }

}
