package com.bairuitech.anychat;

// AnyChat״̬�仯�¼�֪ͨ�ӿ�
public interface AnyChatStateChgEvent {
	// �û�����Ƶ�豸״̬�仯��Ϣ��dwUserId��ʾ�û�ID�ţ�bOpenMic��ʾ���û��Ƿ��Ѵ���Ƶ�ɼ��豸
    public void OnAnyChatMicStateChgMessage(int dwUserId, boolean bOpenMic);
	// �û�����ͷ״̬�ı���Ϣ��dwUserId��ʾ�û�ID�ţ�dwState��ʾ����ͷ�ĵ�ǰ״̬
    public void OnAnyChatCameraStateChgMessage(int dwUserId, int dwState);
	// �û�����ģʽ�ı���Ϣ��dwUserId��ʾ�û�ID�ţ�bPublicChat��ʾ�û��ĵ�ǰ����ģʽ
    public void OnAnyChatChatModeChgMessage(int dwUserId, boolean bPublicChat);
	// �û��״̬�����仯��Ϣ��dwUserId��ʾ�û�ID�ţ�dwState��ʾ�û��ĵ�ǰ�״̬
    public void OnAnyChatActiveStateChgMessage(int dwUserId, int dwState);
	// �����û��������û���P2P��������״̬�����仯��dwUserId��ʾ�����û�ID�ţ�dwState��ʾ�����û��������û��ĵ�ǰP2P��������״̬
    public void OnAnyChatP2PConnectStateMessage(int dwUserId, int dwState);
	// �û���Ƶ�ֱ��ʸı���Ϣ
    //	public void OnAnyChatVideoSizeChgMessage(int dwUserId, int dwWidth, int dwHeight);
}
