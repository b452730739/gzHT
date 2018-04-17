package com.bairuitech.anychat;

public class AnyChatDefine {
	// ��Ϣ֪ͨ���Ͷ���
	static final int WM_GV = 1224;
	static final int WM_GV_CONNECT			=	WM_GV + 1;
	static final int WM_GV_LOGINSYSTEM		=	WM_GV + 2;
	static final int WM_GV_ENTERROOM		=	WM_GV + 3;
	static final int WM_GV_MICSTATECHANGE	=	WM_GV + 4;
	static final int WM_GV_USERATROOM		=	WM_GV + 5;
	static final int WM_GV_LINKCLOSE		=	WM_GV + 6;
	static final int WM_GV_ONLINEUSER		=	WM_GV + 7;
	static final int WM_GV_CAMERASTATE		=	WM_GV + 11;
	static final int WM_GV_CHATMODECHG		=	WM_GV + 12;
	static final int WM_GV_ACTIVESTATE		=	WM_GV + 13;
	static final int WM_GV_P2PCONNECTSTATE	=	WM_GV + 14;
	static final int WM_GV_VIDEOSIZECHG		=	WM_GV + 15;
	static final int WM_GV_USERINFOUPDATE	=	WM_GV + 16;
	static final int WM_GV_FRIENDSTATUS		=	WM_GV + 17;
	static final int WM_GV_PRIVATEREQUEST	=	WM_GV + 21;
	static final int WM_GV_PRIVATEECHO		=	WM_GV + 22;
	static final int WM_GV_PRIVATEEXIT		=	WM_GV + 23;
	static final int WM_GV_AUDIOPLAYCTRL	=	WM_GV + 100;
	static final int WM_GV_AUDIORECCTRL		=	WM_GV + 101;
	static final int WM_GV_VIDEOCAPCTRL		=	WM_GV + 102;
	
	// ��Ƶͼ���ʽ����
	public static final int BRAC_PIX_FMT_RGB24		= 	0;	///< Packed RGB 8:8:8, 24bpp, RGBRGB...��MEDIASUBTYPE_RGB24��
	public static final int BRAC_PIX_FMT_RGB32		=	1;	///< ��Ӧ�ڣ�MEDIASUBTYPE_RGB32��Packed RGB 8:8:8, 32bpp, (msb)8A 8R 8G 8B(lsb), in cpu endianness
	public static final int BRAC_PIX_FMT_YV12		=	2;	///< ��Ӧ�ڣ�MEDIASUBTYPE_YV12��Planar YUV 4:2:0, 12bpp, (1 Cr & Cb sample per 2x2 Y samples)
	public static final int BRAC_PIX_FMT_YUY2		=	3;	///< ��Ӧ�ڣ�MEDIASUBTYPE_YUY2��Packed YUV 4:2:2, 16bpp, Y0 Cb Y1 Cr
	public static final int BRAC_PIX_FMT_YUV420P	=	4;	///< Planar YUV 4:2:0, 12bpp, (1 Cr & Cb sample per 2x2 Y samples)
	public static final int BRAC_PIX_FMT_RGB565		=	5;	///< ��Ӧ�ڣ�MEDIASUBTYPE_RGB565
	public static final int BRAC_PIX_FMT_RGB555		=	6;	///< ��Ӧ�ڣ�MEDIASUBTYPE_RGB555
	public static final int BRAC_PIX_FMT_NV12		=	7;	///< Planar YUV 4:2:0, 12bpp, Two arrays, one is all Y, the other is U and V
	public static final int BRAC_PIX_FMT_NV21		=	8;	///< Planar YUV 4:2:0, 12bpp, Two arrays, one is all Y, the other is V and U
	public static final int BRAC_PIX_FMT_NV16		=	9;	///< YUV422SP
	
	// ��Ƶ�ɼ�����
	public static final int VIDEOCAP_DRIVER_JAVA	=	3;	///< Java��Ƶ�ɼ�����
	// ��Ƶ��ʾ����
	public static final int VIDEOSHOW_DRIVER_JAVA	=	5;	///< Java��Ƶ��ʾ����
	// ��Ƶ�ɼ�����
	public static final int AUDIOREC_DRIVER_JAVA	=	3;	///< Java��Ƶ�ɼ�����
	// ��Ƶ��������
	public static final int AUDIOPLAY_DRIVER_JAVA	=	3;	///< Java��Ƶ��������
	
	// �ں˲�������
	public static final int BRAC_SO_AUDIO_VADCTRL			=	1;	///< ��Ƶ���������ƣ�����Ϊ��int�ͣ�1�򿪣�0�رգ�
	public static final int BRAC_SO_AUDIO_NSCTRL			=	2;	///< ��Ƶ�������ƿ��ƣ�����Ϊ��int�ͣ�1�򿪣�0�رգ�
	public static final int BRAC_SO_AUDIO_ECHOCTRL			=	3;	///< ��Ƶ�����������ƣ�����Ϊ��int�ͣ�1�򿪣�0�رգ�
	public static final int BRAC_SO_AUDIO_AGCCTRL			=	4;	///< ��Ƶ�Զ�������ƣ�����Ϊ��int�ͣ�1�򿪣�0�رգ�
	public static final int BRAC_SO_AUDIO_CPATUREMODE		=	5;	///< ��Ƶ�ɼ�ģʽ���ã�����Ϊ��int�ͣ�0 ����ģʽ��1 �Ÿ�ģʽ��2 ����OKģʽ��3 ��·����ģʽ��
	public static final int BRAC_SO_AUDIO_MICBOOST			=	6;	///< ��Ƶ�ɼ�Mic��ǿ���ƣ�����Ϊ��int�ͣ�0 ȡ����1 ѡ�У�2 �豸������[��ѯʱ����ֵ]��
	public static final int BRAC_SO_AUDIO_AUTOPARAM			=	7;	///< ������Ƶ�ɼ�ģʽ���Զ�ѡ����ʵ���ز������������������������������ʲ����ȣ�����Ϊint�ͣ�1 ���ã�0 �ر�[Ĭ��]��
	public static final int BRAC_SO_AUDIO_MONOBITRATE		=	8;	///< ���õ�����ģʽ����Ƶ����Ŀ�����ʣ�����Ϊ��int�ͣ���λ��bps��
	public static final int BRAC_SO_AUDIO_STEREOBITRATE		=	9;	///< ����˫����ģʽ����Ƶ����Ŀ�����ʣ�����Ϊ��int�ͣ���λ��bps��
	public static final int BRAC_SO_AUDIO_PLAYDRVCTRL		=	70;	///< ��Ƶ��������ѡ�񣨲���Ϊ��int�ͣ�0Ĭ�������� 1 DSound������ 2 WaveOut������ 3 Java����[Androidƽ̨ʹ��]��
	public static final int BRAC_SO_AUDIO_SOFTVOLMODE		=	73;	///< �����������ģʽ���ƣ�����Ϊint�ͣ�1�򿪣�0�ر�[Ĭ��]����ʹ���������ģʽ��������ı�ϵͳ����������
	public static final int BRAC_SO_AUDIO_RECORDDRVCTRL		=	74;	///< ��Ƶ�ɼ��������ƣ�����Ϊint�ͣ�0Ĭ�������� 1 DSound������ 2 WaveIn������ 3 Java�ɼ�[Androidƽ̨ʹ��]��
	public static final int BRAC_SO_AUDIO_ECHODELAY			=	75;	///< ��Ƶ���������ӳٲ������ã�����Ϊint�ͣ���λ��ms��
	
	public static final int BRAC_SO_RECORD_VIDEOBR			=	10;	///< ¼����Ƶ�������ã�����Ϊ��int�ͣ���λ��bps��
	public static final int BRAC_SO_RECORD_AUDIOBR			=	11;	///< ¼����Ƶ�������ã�����Ϊ��int�ͣ���λ��bps��
	public static final int BRAC_SO_RECORD_TMPDIR			=	12;	///< ¼���ļ���ʱĿ¼���ã�����Ϊ�ַ���TCHAR���ͣ������������ľ���·����
	public static final int BRAC_SO_SNAPSHOT_TMPDIR			=	13;	///< �����ļ���ʱĿ¼���ã�����Ϊ�ַ���TCHAR���ͣ������������ľ���·����
	public static final int BRAC_SO_CORESDK_TMPDIR			=	14;	///< ����AnyChat Core SDK��ʱĿ¼������Ϊ�ַ���TCHAR���ͣ������������ľ���·����
	public static final int BRAC_SO_CORESDK_LOADCODEC		=	16;	///< �����ⲿ�������������Ϊ�ַ���TCHAR���ͣ������������ľ���·���������ļ�����������ļ����ľ���·����
	public static final int BRAC_SO_CORESDK_USEARMV6LIB		=	17;	///< ǿ��ʹ��ARMv6ָ��Ŀ⣬androidƽ̨ʹ�ã�����Ϊ��int�ͣ�1ʹ��ARMv6ָ��� 0�ں��Զ��ж�[Ĭ��]��
	public static final int BRAC_SO_CORESDK_USEHWCODEC		=	18;	///< ʹ��ƽ̨����Ӳ���������������Ϊint�ͣ�0 ��ʹ��Ӳ���������[Ĭ��]  1 ʹ������Ӳ�����������
	
	public static final int BRAC_SO_CORESDK_PATH			=	20;	///< ����AnyChat Core SDK������·��������Ϊ�ַ���TCHAR���ͣ������������ľ���·����
	public static final int BRAC_SO_CORESDK_DUMPCOREINFO	=	21;	///< ����ں���Ϣ����־�ļ��У����ڷ�������ԭ�򣨲���Ϊ��int�ͣ�1 �����
	public static final int BRAC_SO_CORESDK_MAINVERSION		=	22;	///< ��ѯSDK���汾�źţ�����Ϊint�ͣ�
	public static final int BRAC_SO_CORESDK_SUBVERSION		=	23;	///< ��ѯSDK�Ӱ汾�ţ�����Ϊint�ͣ�
	public static final int BRAC_SO_CORESDK_BUILDTIME		=	24;	///< ��ѯSDK����ʱ�䣨����Ϊ�ַ���TCHAR���ͣ�
	public static final int BRAC_SO_CORESDK_EXTVIDEOINPUT	=	26;	///< �ⲿ��չ��Ƶ������ƣ�����Ϊint�ͣ� 0 �ر��ⲿ��Ƶ����[Ĭ��]�� 1 �����ⲿ��Ƶ���룩
	public static final int BRAC_SO_CORESDK_EXTAUDIOINPUT	=	27;	///< �ⲿ��չ��Ƶ������ƣ�����Ϊint�ͣ� 0 �ر��ⲿ��Ƶ����[Ĭ��]�� 1 �����ⲿ��Ƶ���룩
	
	public static final int BRAC_SO_LOCALVIDEO_BITRATECTRL	=	30;	///< ������Ƶ�����������ã�����Ϊint�ͣ���λbps��ͬ���������ã�VideoBitrate��
	public static final int BRAC_SO_LOCALVIDEO_QUALITYCTRL	=	31;	///< ������Ƶ�����������ӿ��ƣ�����Ϊint�ͣ�ͬ���������ã�VideoQuality��
	public static final int BRAC_SO_LOCALVIDEO_GOPCTRL		=	32;	///< ������Ƶ����ؼ�֡������ƣ�����Ϊint�ͣ�ͬ���������ã�VideoGOPSize��
	public static final int BRAC_SO_LOCALVIDEO_FPSCTRL		=	33;	///< ������Ƶ����֡�ʿ��ƣ�����Ϊint�ͣ�ͬ���������ã�VideoFps��
	public static final int BRAC_SO_LOCALVIDEO_PRESETCTRL	=	34;	///< ������Ƶ����Ԥ��������ƣ�����Ϊint�ͣ�1-5��
	public static final int BRAC_SO_LOCALVIDEO_APPLYPARAM	=	35;	///< Ӧ�ñ�����Ƶ���������ʹ��ǰ���޸ļ�ʱ��Ч������Ϊint�ͣ�1 ʹ���²�����0 ʹ��Ĭ�ϲ�����
	public static final int BRAC_SO_LOCALVIDEO_VIDEOSIZEPOLITIC=36;///< ������Ƶ�ɼ��ֱ��ʿ��Ʋ��ԣ�����Ϊint�ͣ�0 �Զ�������ƥ��[Ĭ��]��1 ʹ�òɼ��豸Ĭ�Ϸֱ��ʣ��������õķֱ��ʲ����ɼ��豸֧��ʱ��Ч
	public static final int BRAC_SO_LOCALVIDEO_DEINTERLACE	=	37;	///< ������Ƶ����֯�������ƣ�����Ϊint�ͣ� 0 �����з���֯����[Ĭ��]��1 ����֯��������������ƵԴ�Ǹ���ɨ��Դ��������źţ�ʱͨ������֯���������߻�������
	public static final int BRAC_SO_LOCALVIDEO_WIDTHCTRL	=	38;	///< ������Ƶ�ɼ��ֱ��ʿ�ȿ��ƣ�����Ϊint�ͣ�ͬ���������ã�VideoWidth��
	public static final int BRAC_SO_LOCALVIDEO_HEIGHTCTRL	=	39;	///< ������Ƶ�ɼ��ֱ��ʸ߶ȿ��ƣ�����Ϊint�ͣ�ͬ���������ã�VideoHeight��
	public static final int BRAC_SO_LOCALVIDEO_FOCUSCTRL	=	90;	///< ������Ƶ����ͷ�Խ����ƣ�����Ϊint�ͣ�1��ʾ�Զ��Խ��� 0��ʾ�ֶ��Խ���
	public static final int BRAC_SO_LOCALVIDEO_PIXFMTCTRL	=	91;	///< ������Ƶ�ɼ����ȸ�ʽ���ƣ�����Ϊint�ͣ�-1��ʾ����ƥ�䣬�������Ȳ���ָ����ʽ���ο���BRAC_PixelFormat��
	public static final int BRAC_SO_LOCALVIDEO_OVERLAY		=	92;	///< ������Ƶ����Overlayģʽ������Ϊint�ͣ�1��ʾ����Overlayģʽ�� 0��ʾ��ͨģʽ[Ĭ��]��
	public static final int BRAC_SO_LOCALVIDEO_CODECID		=	93;	///< ������Ƶ������ID���ã�����Ϊint�ͣ�-1��ʾĬ�ϣ�������õı�����ID�����ڣ����ں˻����Ĭ�ϵı�������
	public static final int BRAC_SO_LOCALVIDEO_ROTATECTRL	=	94;	///< ������Ƶ��ת���ƣ�����Ϊint�ͣ�0��ʾ��������ת��1��ʾ��ֱ��ת��
	public static final int BRAC_SO_LOCALVIDEO_CAPDRIVER	=	95;	///< ������Ƶ�ɼ��������ã�����Ϊint�ͣ�0��ʾ�Զ�ѡ��[Ĭ��]�� 1 Video4Linux, 2 DirectShow, 3 Java�ɼ�[Androidƽ̨ʹ��]��
	public static final int BRAC_SO_LOCALVIDEO_FIXCOLORDEVIA=	96;	///< ������Ƶ�ɼ���ɫƫɫ������Ϊint�ͣ�0��ʾ�ر�[Ĭ��]��1 ������
	public static final int BRAC_SO_LOCALVIDEO_ORIENTATION	=	97;	///< ������Ƶ�豸���򣨲���Ϊ��int�ͣ�����Ϊ������ANYCHAT_DEVICEORIENTATION_XXXX��
	public static final int BRAC_SO_LOCALVIDEO_AUTOROTATION	=	98;	///< ������Ƶ�Զ���ת���ƣ�����Ϊint�ͣ� 0��ʾ�رգ� 1 ����[Ĭ��]����Ƶ��תʱ��Ҫ�ο�������Ƶ�豸���������
	public static final int BRAC_SO_LOCALVIDEO_SURFACEROTATION=	99;	///< ���ñ�����ƵԤ����ʾ��ת�Ƕȣ�����Ϊint�ͣ��Ƕȣ�
	public static final int BRAC_SO_LOCALVIDEO_CAMERAFACE	=	100;///< ��������ͷ����ǰ�á����ã�
	public static final int BRAC_SO_LOCALVIDEO_DEVICEMODE	=	103;///< �豸����
	
	public static final int BRAC_SO_NETWORK_P2PPOLITIC		=	40;	///< ��������P2P���Կ��ƣ�����Ϊ��int�ͣ�0 ��ֹ����P2P��1 ����������P2P[Ĭ��]��2 �ϲ�Ӧ�ÿ���P2P���ӣ�3 ���轨��P2P���ӣ�
	public static final int BRAC_SO_NETWORK_P2PCONNECT		=	41;	///< ������ָ���û�����P2P���ӣ�����Ϊint�ͣ���ʾĿ���û�ID�������ӽ����ɹ��󣬻�ͨ����Ϣ�������ϲ�Ӧ�ã�P2P���Ʋ���=2ʱ��Ч
	public static final int BRAC_SO_NETWORK_P2PBREAK		=	42;	///< �Ͽ���ָ���û���P2P���ӣ�����Ϊint�ͣ���ʾĿ���û�ID��[�ݲ�֧�֣�����]
	public static final int BRAC_SO_NETWORK_TCPSERVICEPORT	=	43;	///< ���ñ���TCP����˿ڣ�����Ϊint�ͣ������ӷ�����֮ǰ������Ч
	public static final int BRAC_SO_NETWORK_UDPSERVICEPORT	=	44;	///< ���ñ���UDP����˿ڣ�����Ϊint�ͣ������ӷ�����֮ǰ������Ч
	public static final int BRAC_SO_NETWORK_MULTICASTPOLITIC=	45;	///< �鲥���Կ��ƣ�����Ϊint�ͣ�0 ִ�з�����·�ɲ��ԣ���ֹ�鲥����[Ĭ��]�� 1 ���Է�����·�ɲ��ԣ�ֻ���鲥��㲥ý������ 2 ִ�з�����·�ɲ��ԣ�ͬʱ�鲥��
	public static final int BRAC_SO_NETWORK_TRANSBUFMAXBITRATE=	46;	///< ���仺�������ļ�������ʿ��ƣ�����Ϊint�ͣ�0 �����ƣ���������ʴ���[Ĭ��]�� �����ʾ�������ʣ���λΪ��bps��
	public static final int BRAC_SO_NETWORK_AUTORECONNECT	=	47;	///< ��������Զ��������ܿ��ƣ�����Ϊint�ͣ�0 �رգ� 1 ����[Ĭ��]��

	public static final int BRAC_SO_PROXY_FUNCTIONCTRL		=	50;	///< �����û������ܿ��ƣ�������Ϊ��int�ͣ�1��������0�رմ���[Ĭ��]��
	public static final int BRAC_SO_PROXY_VIDEOCTRL			=	51;	///< �����û�������Ƶ���ƣ���������Ƶ��Ϊָ���û�����Ƶ���ⷢ��������Ϊint�ͣ���ʾ�����û���userid��
	public static final int BRAC_SO_PROXY_AUDIOCTRL			=	52;	///< �����û�������Ƶ���ƣ���������Ƶ��Ϊָ���û�����Ƶ���ⷢ��������ͬBRAC_SO_PROXY_VIDEOCTRL��

	public static final int BRAC_SO_STREAM_BUFFERTIME		=	60;	///< ������ʱ�䣨����Ϊint�ͣ���λ�����룬ȡֵ��Χ��500 ~ 5000��Ĭ�ϣ�800��
	public static final int BRAC_SO_STREAM_SMOOTHPLAYMODE	=	61;	///< ƽ������ģʽ������Ϊint�ͣ�0 �ر�[Ĭ��], 1 �򿪣�����״̬��������Ƶ��֡ʱ��������ţ����ܳ��������ˣ������Ῠס
	
	public static final int BRAC_SO_VIDEOSHOW_MODECTRL		=	80;	///< ��Ƶ��ʾģʽ���ƣ�����Ϊ��int�ͣ�0 ��������ʾ��1 ��Ƶ������ʾ��
	public static final int BRAC_SO_VIDEOSHOW_SETPRIMARYUSER=	81;	///< ��������ʾ�û���ţ�����Ϊ��int�ͣ��û�ID�ţ�
	public static final int BRAC_SO_VIDEOSHOW_SETOVERLAYUSER=	82;	///< ���õ�����ʾ�û���ţ�����Ϊ��int�ͣ��û�ID�ţ�
	public static final int BRAC_SO_VIDEOSHOW_DRIVERCTRL	=	83;	///< ��Ƶ��ʾ�������ƣ�����Ϊ��int�ͣ�0 Ĭ�������� 1 Windows DirectShow��2 Windows GDI��3 SDL, 4 Android2X, 5 Android Java��
	public static final int BRAC_SO_VIDEOSHOW_GPUDIRECTRENDER =	84;	///< ��Ƶ���ݾ���GPUֱ����Ⱦ������������Ƶ����ֱ�Ӵ��䵽GPU�������ַ������Ϊ��int�ͣ� 0 �ر�[Ĭ��]�� 1 �򿪣�����Ӳ��ƽ̨���

	public static final int BRAC_SO_CORESDK_DEVICEMODE		=	130;///< �豸ģʽ���ƣ��������豸֮����Ի���ͨ�ţ�������������������Ϊint�ͣ�0 �ر�[Ĭ��]��1 ������
	
	// ����������Ϣ��������
	public static final int BRAC_TRANSTASK_PROGRESS			=	1;	///< ����������Ȳ�ѯ������Ϊ��int�ͣ�0 ~ 100����
	public static final int BRAC_TRANSTASK_BITRATE			=	2;	///< ��������ǰ�������ʣ�����Ϊ��int�ͣ���λ��bps��
	public static final int BRAC_TRANSTASK_STATUS			=	3;	///< ��������ǰ״̬������Ϊ��int�ͣ�


	// ¼���ܱ�־���壨API��StreamRecordCtrl���������ĿǰAndroidƽ̨��ʱֻ֧�ַ�������¼�ƣ�
	public static final int BRAC_RECORD_FLAGS_VIDEO			=	0x00000001;	///< ¼����Ƶ
	public static final int BRAC_RECORD_FLAGS_AUDIO			=	0x00000002;	///< ¼����Ƶ
	public static final int BRAC_RECORD_FLAGS_SERVER		=	0x00000004;	///< ��������¼��


	// �û�״̬��־���壨API��BRAC_QueryUserState ���������
	public static final int BRAC_USERSTATE_CAMERA			=	1;	///< �û�����ͷ״̬������ΪDWORD�ͣ�
	public static final int BRAC_USERSTATE_HOLDMIC			=	2;	///< �û���Ƶ�豸״̬������ΪDWORD�ͣ�����ֵ��0 ��Ƶ�ɼ��رգ� 1 ��Ƶ�ɼ�������
	public static final int BRAC_USERSTATE_SPEAKVOLUME		=	3;	///< �û���ǰ˵������������ΪDOUBLE���ͣ�0.0 ~ 100.0����
	public static final int BRAC_USERSTATE_RECORDING		=	4;	///< �û�¼������״̬������ΪDWORD�ͣ�
	public static final int BRAC_USERSTATE_LEVEL			=	5;	///< �û����𣨲���ΪDWORD�ͣ�
	public static final int BRAC_USERSTATE_NICKNAME			=	6;	///< �û��ǳƣ�����Ϊ�ַ���TCHAR���ͣ�
	public static final int BRAC_USERSTATE_LOCALIP			=	7;	///< �û�����IP��ַ������������Ϊ�ַ���TCHAR���ͣ�
	public static final int BRAC_USERSTATE_INTERNETIP		=	8;	///< �û�������IP��ַ������Ϊ�ַ���TCHAR���ͣ�
	public static final int BRAC_USERSTATE_VIDEOBITRATE		=	9;	///< �û���ǰ����Ƶ���ʣ�����ΪDWORD���ͣ�Bps��
	public static final int BRAC_USERSTATE_AUDIOBITRATE		=	10;	///< �û���ǰ����Ƶ���ʣ�����ΪDWORD���ͣ�Bps��
	public static final int BRAC_USERSTATE_P2PCONNECT		=	11;	///< ��ѯ�����û���ָ���û��ĵ�ǰP2P����״̬������ΪDWORD���ͣ�����ֵ��0 P2P��ͨ�� 1 P2P���ӳɹ�[TCP]��2 P2P���ӳɹ�[UDP]��3 P2P���ӳɹ�[TCP��UDP]��
	public static final int BRAC_USERSTATE_NETWORKSTATUS	=	12;	///< ��ѯָ���û�������״̬������ΪDWORD���ͣ�����ֵ��0 ������1 �Ϻã�2 һ�㣬3 �ϲ4 �ǳ����ע����ѯ�����Ҫ>1s
	public static final int BRAC_USERSTATE_VIDEOSIZE		=	13;	///< ��ѯָ���û�����Ƶ�ֱ��ʣ�����ΪDWORD���ͣ�����ֵ����16λ��ʾ��ȣ���16λ��ʾ�߶ȣ�
	public static final int BRAC_USERSTATE_PACKLOSSRATE		=	14;	///< ��ѯָ���û���������ý�����ݶ����ʣ�����ΪDWORD���ͣ�����ֵ��0 - 100���磺����ֵΪ5����ʾ������Ϊ5%��
	public static final int BRAC_USERSTATE_DEVICETYPE		=	15;	///< ��ѯָ���û����ն����ͣ�����ΪDWORD���ͣ�����ֵ��0 Unknow�� 1 Windows��2 Android��3 iOS��4 Web��5 Linux��6 Mac��7 Win Phone��8 WinCE��
	public static final int BRAC_USERSTATE_SELFUSERSTATUS	=	16;	///< ��ѯ�����û��ĵ�ǰ״̬������ΪDWORD���ͣ�����ֵ��0 Unknow��1 Connected��2 Logined��3 In Room��4 Logouted��5 Link Closed��
	public static final int BRAC_USERSTATE_SELFUSERID		=	17;	///< ��ѯ�����û���ID������ΪDWORD���ͣ����û���¼�ɹ��������û�ʵ�ʵ�userid�����򷵻�-1��
	public static final int BRAC_USERSTATE_VIDEOROTATION	=	18;	///< ��ѯָ���û��ĵ�ǰ��Ƶ��ת�Ƕȣ�����ΪDWORD���ͣ����ؽǶ�ֵ��
	public static final int BRAC_USERSTATE_VIDEOMIRRORED	=	19;	///< ��ѯָ���û�����Ƶ�Ƿ���Ҫ����ת
	
	// ����״̬��־���壨API��BRAC_QueryRoomState ���������
	public static final int BRAC_ROOMSTATE_ROOMNAME			=	1;	///< �������ƣ�����Ϊ�ַ���TCHAR���ͣ�
	public static final int BRAC_ROOMSTATE_ONLINEUSERS		=	2;	///< ���������û���������ΪDWORD�ͣ��������Լ���
	
	// ��Ƶ�����¼����Ͷ��壨API��BRAC_VideoCallControl ���������VideoCallEvent�ص�������
	public static final int BRAC_VIDEOCALL_EVENT_REQUEST	=	1;	///< ��������
	public static final int BRAC_VIDEOCALL_EVENT_REPLY		=	2;	///< ��������ظ�
	public static final int BRAC_VIDEOCALL_EVENT_START		=	3;	///< ��Ƶ���лỰ��ʼ�¼�
	public static final int BRAC_VIDEOCALL_EVENT_FINISH		=	4;	///< �Ҷϣ����������лỰ
	
	// ��Ƶ���б�־���壨API��BRAC_VideoCallControl ���������
	public static final int BRAC_VIDEOCALL_FLAGS_AUDIO		= 0x01;	///< ����ͨ��
	public static final int BRAC_VIDEOCALL_FLAGS_VIDEO		= 0x02;	///< ��Ƶͨ��
	public static final int BRAC_VIDEOCALL_FLAGS_FBSRCAUDIO	= 0x10;	///< ��ֹԴ�����жˣ���Ƶ
	public static final int BRAC_VIDEOCALL_FLAGS_FBSRCVIDEO	= 0x20;	///< ��ֹԴ�����жˣ���Ƶ
	public static final int BRAC_VIDEOCALL_FLAGS_FBTARAUDIO	= 0x40;	///< ��ֹĿ�꣨�����жˣ���Ƶ
	public static final int BRAC_VIDEOCALL_FLAGS_FBTARVIDEO	= 0x80;	///< ��ֹĿ�꣨�����жˣ���Ƶ
	
	// ��Ƶ����������־����
	public static final int BRAC_ROTATION_FLAGS_MIRRORED	= 0x1000;///< ͼ����Ҫ����ת
	public static final int BRAC_ROTATION_FLAGS_ROTATION90	= 0x2000;///< ˳ʱ����ת90��
	public static final int BRAC_ROTATION_FLAGS_ROTATION180	= 0x4000;///< ˳ʱ����ת180��
	public static final int BRAC_ROTATION_FLAGS_ROTATION270	= 0x8000;///< ˳ʱ����ת270��

	// �û���Ϣ�������Ͷ��壨API��BRAC_UserInfoControl ���������
	public static final int BRAC_USERINFO_CTRLCODE_ROTATION	=	8;	///< ��ָ�����û���Ƶ����ʾʱ��ת��wParamΪ��ת�ǶȲ���
	
	// ����������붨��
    public static final int BRAC_ERRORCODE_SUCCESS			= 0;	 ///< û�д���
    public static final int BRAC_ERRORCODE_SESSION_QUIT		= 100101;///< Դ�û����������Ự
    public static final int BRAC_ERRORCODE_SESSION_OFFLINE	= 100102;///< Ŀ���û�������
    public static final int BRAC_ERRORCODE_SESSION_BUSY		= 100103;///< Ŀ���û�æ
    public static final int BRAC_ERRORCODE_SESSION_REFUSE	= 100104;///< Ŀ���û��ܾ��Ự
    public static final int BRAC_ERRORCODE_SESSION_TIMEOUT	= 100105;///< �Ự����ʱ
    public static final int BRAC_ERRORCODE_SESSION_DISCONNECT=100106;///< �������
	
	
	
}