package com.bairuitech.anychat;

// ��������֪ͨ�ӿ�
public interface AnyChatTextMsgEvent {
	// ������Ϣ֪ͨ,dwFromUserid��ʾ��Ϣ�����ߵ��û�ID�ţ�dwToUserid��ʾĿ���û�ID�ţ�����Ϊ-1����ʾ�Դ��˵��bSecret��ʾ�Ƿ�Ϊ���Ļ�
    public void OnAnyChatTextMessage(int dwFromUserid, int dwToUserid, boolean bSecret, String message);
}