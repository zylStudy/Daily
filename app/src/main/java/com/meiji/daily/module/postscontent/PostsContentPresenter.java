package com.meiji.daily.module.postscontent;

import com.meiji.daily.RetrofitFactory;
import com.meiji.daily.bean.PostsContentBean;
import com.meiji.daily.data.remote.IApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2016/11/23.
 */
@Deprecated
public class PostsContentPresenter implements IPostsContent.Presenter {

    private IPostsContent.View view;

    public PostsContentPresenter(IPostsContent.View view) {
        this.view = view;
    }

    @Override
    public void doRequestData(final int slug) {
        view.onShowLoading();

        RetrofitFactory.getRetrofit().create(IApi.class).getPostsContentBeanRx(slug)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(view.<PostsContentBean>bindToLife())
                .subscribe(new Consumer<PostsContentBean>() {
                    @Override
                    public void accept(@NonNull PostsContentBean bean) throws Exception {
                        view.onHideLoading();
                        view.onSetWebView(getContent(bean.getContent()));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        view.onShowNetError();
                    }
                });
    }

    private String getContent(String content) {
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/master.css\" type=\"text/css\">";
        String html = "<!DOCTYPE html>\n"
                + "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n"
                + "<head>\n"
                + "\t<meta charset=\"utf-8\" />\n</head>\n"
                + css
                + "\n<body>"
                + content
                + "</body>\n</html>";

        return html;
    }
}