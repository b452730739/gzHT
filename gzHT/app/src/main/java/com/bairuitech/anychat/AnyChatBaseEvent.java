package com.bairuitech.anychat;

//AnyChat�����¼��ӿ�
public interface AnyChatBaseEvent {
	// ���ӷ�������Ϣ, bSuccess��ʾ�Ƿ����ӳɹ�
    public void OnAnyChatConnectMessage(boolean bSuccess);
	// �û���¼��Ϣ��dwUserId��ʾ�Լ����û�ID�ţ�dwErrorCode��ʾ��¼�����0 �ɹ�������Ϊ�������
    public void OnAnyChatLoginMessage(int dwUserId, int dwErrorCode);
	// �û����뷿����Ϣ��dwRoomId��ʾ�����뷿���ID�ţ�dwErrorCode��ʾ�Ƿ���뷿�䣺0�ɹ����룬����Ϊ�������
    public void OnAnyChatEnterRoomMessage(int dwRoomId, int dwErrorCode);
	// ���������û���Ϣ�����뷿��󴥷�һ�Σ�dwUserNum��ʾ�����û����������Լ�����dwRoomId��ʾ����ID
    public void OnAnyChatOnlineUserMessage(int dwUserNum, int dwRoomId);
	// �û�����/�˳�������Ϣ��dwUserId��ʾ�û�ID�ţ�bEnter��ʾ���û��ǽ��루TRUE�����뿪��FALSE������
    public void OnAnyChatUserAtRoomMessage(int dwUserId, boolean bEnter);
	//����Ͽ���Ϣ������Ϣֻ���ڿͻ������ӷ������ɹ�֮�������쳣�ж�֮ʱ������dwErrorCode��ʾ���ӶϿ���ԭ��
    public void OnAnyChatLinkCloseMessage(int dwErrorCode);	
	
}
