package com.downfiles;


import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Download {
    private String urlstr;// ���صĵ�ַ
    private String localfile;// ����·��
    private int threadcount;// �߳���
    private Handler mHandler;// ��Ϣ������ 
    private int fileSize;// ��Ҫ���ص��ļ��Ĵ�С
    private Context context; 
    private List<DownloadInfo> infos;// ���������Ϣ��ļ���
    private static final int INIT = 1;//�����������ص�״̬����ʼ��״̬����������״̬����ͣ״̬
    private static final int DOWNLOADING = 2;
    private static final int PAUSE = 3;
    private int state = INIT;

    public Download(String urlstr, String localfile, int threadcount,
            Context context, Handler mHandler) {
        this.urlstr = urlstr;
        this.localfile = localfile;
        this.threadcount = threadcount;
        this.mHandler = mHandler;
        this.context = context;
    }
    /**
     *�ж��Ƿ��������� 
     */
    public boolean isdownloading() {
        return state == DOWNLOADING;
    }
    /**
     * �õ�downloader�����Ϣ
     * ���Ƚ����ж��Ƿ��ǵ�һ�����أ�����ǵ�һ�ξ�Ҫ���г�ʼ������������������Ϣ���浽���ݿ���
     * ������ǵ�һ�����أ��Ǿ�Ҫ�����ݿ��ж���֮ǰ���ص���Ϣ����ʼλ�ã�����Ϊֹ���ļ���С�ȣ�������������Ϣ���ظ�������
     */
    public LoadInfo getDownloaderInfors() {
        if (isFirst(urlstr)) {
            Log.v("TAG", "isFirst");
            init();
            int range = fileSize / threadcount;
            infos = new ArrayList<DownloadInfo>();
            for (int i = 0; i < threadcount - 1; i++) {
                DownloadInfo info = new DownloadInfo(i, i * range, (i + 1)* range - 1, 0, urlstr);
                infos.add(info);
            }
            DownloadInfo info = new DownloadInfo(threadcount - 1,(threadcount - 1) * range, fileSize - 1, 0, urlstr);
            infos.add(info);
            //����infos�е����ݵ����ݿ�
            GetDownloadInfos.getInstance(context).saveInfos(infos);
            //����һ��LoadInfo��������������ľ�����Ϣ
            LoadInfo loadInfo = new LoadInfo(fileSize, 0, urlstr);
            return loadInfo;
        } else {
            //�õ����ݿ������е�urlstr���������ľ�����Ϣ
            infos = GetDownloadInfos.getInstance(context).getInfos(urlstr);
            Log.v("TAG", "not isFirst size=" + infos.size());
            int size = 0;
            int compeleteSize = 0;
            for (DownloadInfo info : infos) {
                compeleteSize += info.getCompeleteSize();
                size += info.getEndPos() - info.getStartPos() + 1;
            }
            return new LoadInfo(size, compeleteSize, urlstr);
        }
    }

    /**
     * ��ʼ��
     */
    private void init() {
        try {
            URL url = new URL(urlstr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            fileSize = connection.getContentLength();

            File file = new File(localfile);
            if (!file.exists()) {
                file.createNewFile();
            }
            // ���ط����ļ�
            RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
            accessFile.setLength(fileSize);
            accessFile.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }  
    /**
     * �ж��Ƿ��ǵ�һ�� ����
     */
    private boolean isFirst(String urlstr) {
        return GetDownloadInfos.getInstance(context).isHasInfors(urlstr);
    }

    /**
     * �����߳̿�ʼ��������
     */
    public void download() {
        if (infos != null) {
            if (state == DOWNLOADING)
                return;
            state = DOWNLOADING;
            for (DownloadInfo info : infos) {
                new MyThread(info.getThreadId(), info.getStartPos(),
                        info.getEndPos(), info.getCompeleteSize(),
                        info.getUrl()).start();
            }
        }
    }

    public class MyThread extends Thread {
        private int threadId;
        private int startPos;
        private int endPos;
        private int compeleteSize;
        private String urlstr;

        public MyThread(int threadId, int startPos, int endPos,
                int compeleteSize, String urlstr) {
            this.threadId = threadId;
            this.startPos = startPos;
            this.endPos = endPos;
            this.compeleteSize = compeleteSize;
            this.urlstr = urlstr;
        }
        @Override
        public void run() {
            HttpURLConnection connection = null;
            RandomAccessFile randomAccessFile = null;
            InputStream is = null;
            try {
                URL url = new URL(urlstr);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("GET");
                // ���÷�Χ����ʽΪRange��bytes x-y;
                connection.setRequestProperty("Range", "bytes="+(startPos + compeleteSize) + "-" + endPos);

                randomAccessFile = new RandomAccessFile(localfile, "rwd");
                randomAccessFile.seek(startPos + compeleteSize);
                // ��Ҫ���ص��ļ�д�������ڱ���·���µ��ļ���
                is = connection.getInputStream();
                byte[] buffer = new byte[4096];
                int length = -1;
                while ((length = is.read(buffer)) != -1) {
                    randomAccessFile.write(buffer, 0, length);
                    compeleteSize += length;
                    // �������ݿ��е�������Ϣ
                    GetDownloadInfos.getInstance(context).updataInfos(threadId, compeleteSize, urlstr);
                    // ����Ϣ��������Ϣ�������������Խ��������и���
                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = urlstr;
                    message.arg1 = length;
                    mHandler.sendMessage(message);
                    if (state == PAUSE) {
                        return;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }  
        }
    }
    //ɾ�����ݿ���urlstr��Ӧ����������Ϣ
    public void delete(String urlstr) {
   	 GetDownloadInfos.getInstance(context).delete(urlstr);
    }
    //������ͣ
    public void pause() {
        state = PAUSE;
    }
    //��������״̬
    public void reset() {
        state = INIT;
    }
}