package com.bairuitech.anychat;

// AnyChat˽����Ϣ֪ͨ�ӿ�
public interface AnyChatPrivateChatEvent {
	// �û�˽��������Ϣ��dwUserId��ʾ�����ߵ��û�ID�ţ�dwRequestId��ʾ˽�������ţ���ʶ������
    public void OnAnyChatPrivateRequestMessage(int dwUserId, int dwRequestId);
	// �û�˽������ظ���Ϣ��dwUserId��ʾ�ظ��ߵ��û�ID�ţ�dwErrorCodeΪ�������
    public void OnAnyChatPrivateEchoMessage(int dwUserId, int dwErrorCode);
	// �û��˳�˽����Ϣ��dwUserId��ʾ�˳��ߵ��û�ID�ţ�dwErrorCodeΪ�������
    public void OnAnyChatPrivateExitMessage(int dwUserId, int dwErrorCode);	
}