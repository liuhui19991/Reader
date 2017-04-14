package com.liuhui.fbreadersample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.download.DownloadRequest;
import com.yanzhenjie.nohttp.tools.IOUtils;

import org.geometerplus.android.fbreader.FBReader;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String path = "http://resource.gbxx123.com/book/epubs/2017/3/16/1489654270124/1489654270124.epub";
    private DownloadRequest mDownloadRequest;
    private String mDown;
    private String online = "http://resource.gbxx123.com/book/epubs/2017/3/16/1489654270124/ops/chapter_00001.xhtml";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv).setOnClickListener(this);
        findViewById(R.id.reader).setOnClickListener(this);
        mDown = FileUtil.getRootPath(this).getAbsolutePath() + File.separator + "readerdown";
        IOUtils.createFolder(mDown);
    }

    /**
     * 跳转阅读页
     *
     * @param baseBookEntity
     * @param
     */
    public static void startBookFBReaderActivity(Activity activity, BaseBookEntity baseBookEntity) {
        if (baseBookEntity == null || activity == null)
            return;
        Intent intent = new Intent(activity, FBReader.class);
        intent.putExtra(ConstantValue.BASEBOOK, baseBookEntity);
        intent.setAction(ConstantValue.FB_READER.ACTION_OPEN_FROM_BOOK_STORE);//设置是本地打开还是在线
        activity.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv:
                download();
                break;
            case R.id.reader:
                //                startActivity(new Intent(MainActivity.this, FBReader.class));
                BaseBookEntity baseBookEntity = new BaseBookEntity();
                baseBookEntity.setBook_path(mDown + "/book.epub");
//                baseBookEntity.setBook_path(online);
                startBookFBReaderActivity(MainActivity.this, baseBookEntity);
                break;
            default:

                break;
        }
    }

    /**
     * 开始下载。
     */
    private void download() {
        // 开始下载了，但是任务没有完成，代表正在下载，那么暂停下载。
        if (mDownloadRequest != null && mDownloadRequest.isStarted() && !mDownloadRequest.isFinished()) {
            // 暂停下载。
            mDownloadRequest.cancel();
        } else if (mDownloadRequest == null || mDownloadRequest.isFinished()) {// 没有开始或者下载完成了，就重新下载。

            /**
             * 这里不传文件名称、不断点续传，则会从响应头中读取文件名自动命名，如果响应头中没有则会从url中截取。
             */
            // url 下载地址。
            // fileFolder 文件保存的文件夹。
            // isDeleteOld 在指定的文件夹发现同名的文件是否删除后重新下载，true则删除重新下载，false则直接通知下载成功。
            // mDownloadRequest = NoHttp.createDownloadRequest(Constants.URL_DOWNLOADS[0], AppConfig.getInstance()
            // .APP_PATH_ROOT, true);

            /**
             * 如果使用断点续传的话，一定要指定文件名。
             */
            // url 下载地址。
            // fileFolder 保存的文件夹。
            // fileName 文件名。
            // isRange 是否断点续传下载。
            // isDeleteOld 在指定的文件夹发现同名的文件是否删除后重新下载，true则删除重新下载，false则直接通知下载成功。
            mDownloadRequest = NoHttp.createDownloadRequest(path, mDown, "book.epub", true, true);

            // what 区分下载。
            // downloadRequest 下载请求对象。
            // downloadListener 下载监听。
            NoHttp.getDownloadQueueInstance().add(0, mDownloadRequest, downloadListener);

        }
    }

    private DownloadListener downloadListener = new DownloadListener() {

        @Override
        public void onStart(int what, boolean isResume, long beforeLength, Headers headers, long allCount) {
        }

        @Override
        public void onDownloadError(int what, Exception exception) {

        }

        @Override
        public void onProgress(int what, int progress, long fileCount, long speed) {

        }

        @Override
        public void onFinish(int what, String filePath) {
            Logger.d("Download finish, file path: " + filePath);
            Toast.makeText(MainActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(int what) {
        }

    };
}
