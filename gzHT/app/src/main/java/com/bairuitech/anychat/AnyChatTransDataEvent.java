package com.bairuitech.anychat;

// ���ݴ���֪ͨ�ӿ�
public interface AnyChatTransDataEvent {
	// �ļ�����ص���������
	public void OnAnyChatTransFile(int dwUserid, String FileName, String TempFilePath, int dwFileLength, int wParam, int lParam, int dwTaskId);
	// ͸��ͨ�����ݻص���������
	public void OnAnyChatTransBuffer(int dwUserid, byte[] lpBuf, int dwLen);
	// ��չ͸��ͨ�����ݻص���������
	public void OnAnyChatTransBufferEx(int dwUserid, byte[] lpBuf, int dwLen, int wparam, int lparam, int taskid);
	// SDK Filter ͨ�����ݻص���������
	public void OnAnyChatSDKFilterData(byte[] lpBuf, int dwLen);
}
