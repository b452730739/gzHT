package com.bairuitech.anychat;		// �����޸İ�������

/**
 * ͨ�ú������ز�����
 * AnyChat�ں˵���Set�������ò������ϲ�Ӧ��ͨ��Get������ȡֵ
 */
public class AnyChatOutParam{
	private int iValue = 0;
	private String szValue = "";
	private int[] intArray;
	private byte[] byteArray;
	private double fValue = 0.0;
	
	public int GetIntValue()			{ 	return iValue;	}
	public void SetIntValue(int v) 		{	iValue = v;		}
	
	public double GetFloatValue()		{ 	return fValue;	}
	public void SetFloatValue(double f)	{	fValue = f;		}

	public String GetStrValue()			{	return szValue; }
	public void SetStrValue(String s) 	{	szValue = s; 	}
	
	public int[] GetIntArray()			{ 	return intArray;}
	public void SetIntArray(int[] a)	{	intArray = a; 	}
	
	public byte[] GetByteArray()		{ 	return byteArray;}
	public void SetByteArray(byte[] b)	{	byteArray = b;	}
}

