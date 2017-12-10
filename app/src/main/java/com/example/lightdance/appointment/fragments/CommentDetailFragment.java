package com.example.lightdance.appointment.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lightdance.appointment.Model.BrowserMsgBean;
import com.example.lightdance.appointment.Model.HistoryBean;
import com.example.lightdance.appointment.Model.UserBean;
import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.activities.CommentActivity;
import com.example.lightdance.appointment.adapters.NoCommentDetailAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @author pope
 *         A simple {@link Fragment} subclass.
 */
public class CommentDetailFragment extends Fragment {


    @BindView(R.id.toolbar_comment_detail_fragment)
    Toolbar toolbar;
    Unbinder unbinder;
    @BindView(R.id.recyclerView_comment_detail_fragment)
    RecyclerView recyclerView;

    private ProgressDialog progressDialog;

    private int xx;
    private boolean isChange = false;
    private String objectId;
    private String userObjectId;
    private String mComment2;
    private List<UserBean> membersBean;

    public CommentDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment_detail, container, false);
        unbinder = ButterKnife.bind(this, view);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginData", Context.MODE_PRIVATE);
        userObjectId = sharedPreferences.getString("userBeanId", "出错啦~");

        progressDialog = new ProgressDialog(getActivity());

        toolbar.setTitle("活动反馈");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setNavigationIcon(R.mipmap.ic_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentActivity commentActivity = (CommentActivity) getActivity();
                commentActivity.changeFragment(1);
            }
        });

        //初始化数据
        initData();

        return view;
    }

    /**
     * 初始化数据方法
     */
    public void initData() {
        progressDialog.setTitle("请稍等");
        progressDialog.setMessage("加载中...");
        progressDialog.show();
        //获取需要显示的BrowserMsgBean的ObjectId
        CommentActivity activity = (CommentActivity) getActivity();
        objectId = activity.getObjectId();
        //获取该活动的Bean数据
        BmobQuery<BrowserMsgBean> query = new BmobQuery<>();
        query.getObject(objectId, new QueryListener<BrowserMsgBean>() {
            @Override
            public void done(BrowserMsgBean browserMsgBean, BmobException e) {
                if (e == null) {
                    //初始化xx和members
                    xx = 0;
                    membersBean = new ArrayList<>();
                    //获取成员数量决定for循环次数
                    final List<String> members = browserMsgBean.getMembers();
                    for (int i = members.size() - 1; i >= 0; i--) {
                        String item = members.get(i);
                        if (userObjectId.equals(item)) {
                            members.remove(item);
                        }
                    }
                    for (int i = 0; i < members.size(); i++) {
                        xx = xx + 1;
                        String memberId = members.get(i);
                        //根据成员数量初始化一个没有数据的membersBean
                        membersBean.add(i, new UserBean());
                        BmobQuery<UserBean> query1 = new BmobQuery<>();
                        final int finalI = i;
                        query1.getObject(memberId, new QueryListener<UserBean>() {
                            @Override
                            public void done(UserBean userBean, BmobException e) {
                                if (e == null) {
                                    //获取到带数据的Bean后将其置换掉对应位置没有数据的Bean
                                    membersBean.set(finalI, userBean);
                                    //异步返回数据时，判断当前是否已经把全部数据置换进去
                                    if (xx == members.size()) {
                                        //初始化列表并显示
                                        initList(membersBean);
                                    }
                                } else {
                                    Toast.makeText(getActivity(), "数据查询出错1", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(getActivity(), "数据查询出错2", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 初始化反馈列表并显示方法
     *
     * @param membersBean 传入被反馈是否赴约的用户的List<UserBean>
     */
    private void initList(List<UserBean> membersBean) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        NoCommentDetailAdapter adapter = new NoCommentDetailAdapter(membersBean);
        recyclerView.setAdapter(adapter);

        progressDialog.dismiss();

        adapter.setSwitchChangeListener(new NoCommentDetailAdapter.SwitchChangeListener() {
            @Override
            public void onCheckedChanged(int position, boolean isChecked) {
                String isShow = "";
                if (isChecked) {
                    isShow = "是";
                } else {
                    isShow = "否";
                }
                changeComment(position, isShow);
            }
        });
    }

    /**
     * 更改我的反馈结果方法
     *
     * @param position 被更改的位置
     * @param isShow   是否出席状态：是/否
     */
    private void changeComment(final int position, final String isShow) {
        BmobQuery<BrowserMsgBean> q1 = new BmobQuery<>();
        q1.getObject(objectId, new QueryListener<BrowserMsgBean>() {
            @Override
            public void done(BrowserMsgBean browserMsgBean, BmobException e) {
                if (e == null) {
                    String mComment1 = null;
                    int a = 0;
                    int p = position;
                    List<String> commentResult = browserMsgBean.getCommentResult();
                    List<String> members = browserMsgBean.getMembers();
                    /**
                     * 检测是否有过未提交前的更改
                     * 若有则读取上一次更改的结果值并在这个值之上进行数据操作
                     * 若没有则读取后台存储的结果值
                     */
                    if (isChange) {
                        mComment1 = mComment2;
                    } else {
                        for (int i = 0; i < members.size(); i++) {
                            String item = members.get(i);
                            if (userObjectId.equals(item)) {
                                mComment1 = commentResult.get(i);
                                a = i;
                            }
                        }
                    }
                    //若该被反馈用户在members列表中排在该用户之后的，那么被反馈的这个用户的position应该+1
                    if (p >= a) {
                        p = p + 1;
                    }
                    //构造新的结果值
                    String s = "";
                    for (int i = 0; i < members.size(); i++) {
                        if (p == i) {
                            s = s + isShow;
                        } else {
                            s = s + mComment1.substring(i, i + 1);
                        }
                    }
                    //备份一个当前更改完成的结果值
                    mComment2 = s;
                    //保存一个未提交数据到后台、已更改结果值的标识
                    isChange = true;
                    Log.i("调试", "我的反馈结果:" + s);
                } else {
                    Log.i("调试", "位置:CommentDetailFragment" + "\n" + "获取正在反馈的活动的相关信息时出错" + e.getMessage());
                }
            }
        });
    }

    public static CommentDetailFragment newInstance() {
        CommentDetailFragment fragment = new CommentDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 提交按钮的点击监听 完成相关数据的存储
     */
    @OnClick(R.id.tv_comment_detail_submit)
    public void onViewClicked() {
        BmobQuery<BrowserMsgBean> q1 = new BmobQuery<>();
        q1.getObject(objectId, new QueryListener<BrowserMsgBean>() {
            @Override
            public void done(BrowserMsgBean browserMsgBean, BmobException e) {
                if (e == null) {
                    List<String> commentResult = browserMsgBean.getCommentResult();
                    List<String> members = browserMsgBean.getMembers();
                    for (int i = 0; i < members.size(); i++) {
                        String item = members.get(i);
                        if (userObjectId.equals(item)) {
                            commentResult.set(i, mComment2);
                        }
                    }
                    browserMsgBean.setValue("commentResult", commentResult);
                    browserMsgBean.update(objectId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e != null) {
                                Toast.makeText(getActivity(), "反馈失败" + e.getMessage(), Toast.LENGTH_LONG).show();
                                Log.i("调试", "位置：CommentDetailFragment\n更新反馈结果时出错" + e.getMessage());
                            } else {
                                Toast.makeText(getActivity(), "提交成功\n感谢您的合作~！", Toast.LENGTH_LONG).show();
                                //提交成功后更新未反馈活动列表数据、计算反馈分数（在全部成员都反馈后） 结束承载该碎片的活动
                                updateNoComment();
                                calculateScore();
                                getActivity().finish();
                            }
                        }
                    });
                } else {
                    Log.i("调试", "位置:CommentDetailFragment" + "\n" + "获取正在反馈的活动的相关信息时出错" + e.getMessage());
                }
            }
        });
    }

    /**
     * 计算反馈分数方法 用来判断当前用户是否赴约
     */
    private void calculateScore() {
        BmobQuery<BrowserMsgBean> q = new BmobQuery<>();
        q.getObject(objectId, new QueryListener<BrowserMsgBean>() {
            @Override
            public void done(final BrowserMsgBean browserMsgBean, BmobException e) {
                if (e == null) {
                    //仅当未反馈用户数量为0，即全部反馈时才计算反馈分数
                    if (browserMsgBean.getNoCommentUser().size() == 0 || browserMsgBean.getNoCommentUser() == null) {
                        final List<String> members = browserMsgBean.getMembers();
                        final List<String> commentResult = browserMsgBean.getCommentResult();
                        final List<String> commentScore = browserMsgBean.getCommentScore();
                        final int personNumber = members.size();
                        //查询用户赴约率判断等级并初始化反馈初始分
                        BmobQuery<UserBean> q2 = new BmobQuery<>();
                        q2.getObject(userObjectId, new QueryListener<UserBean>() {
                            @Override
                            public void done(UserBean userBean, BmobException e) {
                                if (e == null) {
                                    int score;
                                    int mPosition = 0;
                                    double attendance = userBean.getAttendance();
                                    //高于95%的初始分为20
                                    if (attendance >= 95.00) {
                                        score = 20;
                                    } else {
                                        //高于80%的初始分为10
                                        if (attendance >= 80.00) {
                                            score = 10;
                                        } else {
                                            //其余的初始分为0
                                            score = 0;
                                        }
                                    }
                                    //获取我在列表中的位置
                                    for (int i = 0; i < personNumber; i++) {
                                        if (userObjectId.equals(members.get(i))) {
                                            mPosition = i;
                                        }
                                    }
                                    //计算反馈分数
                                    for (int i = 0; i < personNumber; i++) {
                                        String comment = commentResult.get(i);
                                        if (i == mPosition) {
                                            String s = comment.substring(mPosition, mPosition + 1);
                                            if (s.equals("否")) {
                                                score = 0;
                                                break;
                                            }
                                        } else {
                                            String s = comment.substring(mPosition, mPosition + 1);
                                            if (s.equals("是")) {
                                                score = score + 4;
                                            } else {
                                                score = score - 10;
                                            }
                                        }
                                    }
                                    //更新数据并保存后台
                                    commentScore.set(mPosition, String.valueOf(score));
                                    browserMsgBean.setValue("commentScore", commentScore);
                                    browserMsgBean.update(objectId, new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e != null) {
                                                Log.i("调试", "位置:CommentDetailFragment" + "\n" + "计算完总分后，更新数据出错" + e.getMessage());
                                            }
                                        }
                                    });
                                } else {
                                    Log.i("调试", "位置:CommentDetailFragment" + "\n" + "查询用户赴约率时出错" + e.getMessage());
                                }
                            }
                        });
                    }
                } else

                {
                    Log.i("调试", "位置:CommentDetailFragment" + "\n" + "计算总分时 获取活动信息出错" + e.getMessage());
                }
            }
        });
    }

    /**
     * 更新未反馈活动列表方法 当检测到某个活动已反馈时调用该活动
     */
    private void updateNoComment() {
        BmobQuery<HistoryBean> q = new BmobQuery<>();
        q.addWhereEqualTo("userObjectId", userObjectId);
        q.findObjects(new FindListener<HistoryBean>() {
            @Override
            public void done(List<HistoryBean> list, BmobException e) {
                if (e == null) {
                    if (list.size() != 0) {
                        HistoryBean historyBean = list.get(0);
                        List<String> noComment = historyBean.getNoComment();
                        List<String> haveComment = historyBean.getHaveComment();
                        //从未反馈活动列表删除当前活动
                        for (int i = 0; i < noComment.size(); i++) {
                            String item = noComment.get(i);
                            if (objectId.equals(item)) {
                                noComment.remove(item);
                            }
                        }
                        if (haveComment == null) {
                            haveComment = new ArrayList<>();
                        }
                        //将当前活动添加到已反馈活动列表中 并更新数据到后台
                        haveComment.add(objectId);
                        historyBean.setValue("noComment", noComment);
                        historyBean.setValue("haveComment", haveComment);
                        historyBean.update(historyBean.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e != null) {
                                    Log.i("调试", "位置：CommentDetailFragment\n更新未反馈活动列表时，保存新数据出错");
                                }
                            }
                        });
                    }
                } else {
                    Log.i("调试", "位置：CommentDetailFragment\n更新未反馈活动列表时 查询HistoryBean出错" + e.getMessage());
                }
            }
        });
    }
}
