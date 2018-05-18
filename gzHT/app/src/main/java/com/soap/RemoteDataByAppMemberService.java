package com.soap;

import com.constant.IP_Address;

import rx.Subscriber;

public class RemoteDataByAppMemberService {

    public static final String TAG   = RemoteDataByAppMemberService.class.getName();
    private static final String Service = IP_Address.AppMemberService;

    /**
     * 登录
     * @param strUserID
     * @param strPassword
     * @param s
     */
    public static void AppUserLogon(@Param("strUserID")String strUserID,@Param("strPassword")String strPassword,  Subscriber<String> s){
        String[] values = {strUserID,strPassword};
        ContentTools.getResult(Service,IP_Address.NAMESPACE,values,Thread.currentThread().getStackTrace(),RemoteDataByAppMemberService.class,s);
    }

    /**
     * 生产任务查询
     * @param strMachineNO
     * @param strMachineModel
     * @param strStartDate
     * @param strEndDate
     * @param strStatus
     * @param s
     */
    public static void AppTaskSearch(@Param("strMachineNO")String strMachineNO,@Param("strMachineModel")String strMachineModel,@Param("strStartDate")String strStartDate,@Param("strEndDate")String strEndDate,@Param("strStatus")String strStatus,  Subscriber<String> s){
        String[] values = {strMachineNO,strMachineModel,strStartDate,strEndDate,strStatus};
        ContentTools.getResult(Service,IP_Address.NAMESPACE,values,Thread.currentThread().getStackTrace(),RemoteDataByAppMemberService.class,s);
    }

    /**
     * 已入库机器查询
     * @param strMachineNO
     * @param strMachineModel
     * @param strStartDate
     * @param strEndDate
     * @param s
     */
    public static void AppMachineStock(@Param("strMachineNO")String strMachineNO,@Param("strMachineModel")String strMachineModel,@Param("strStartDate")String strStartDate,@Param("strEndDate")String strEndDate, Subscriber<String> s){
        String[] values = {strMachineNO,strMachineModel,strStartDate,strEndDate};
        ContentTools.getResult(Service,IP_Address.NAMESPACE,values,Thread.currentThread().getStackTrace(),RemoteDataByAppMemberService.class,s);
    }

    /**
     * 待办任务列表
     * @param strUserID
     * @param strMachineNO
     * @param strStartDate
     * @param strEndDate
     * @param s
     */
    public static void AppTaskDaiBanList(@Param("strUserID")String strUserID,@Param("strMachineNO")String strMachineNO,@Param("strStartDate")String strStartDate,@Param("strEndDate")String strEndDate, Subscriber<String> s){
        String[] values = {strUserID,strMachineNO,strStartDate,strEndDate};
        ContentTools.getResult(Service,IP_Address.NAMESPACE,values,Thread.currentThread().getStackTrace(),RemoteDataByAppMemberService.class,s);
    }

    /**
     * 查看工人工时
     * @param strUserID
     * @param strStartDate
     * @param strEndDate
     * @param s
     */
    public static void AppWorkHour(@Param("strUserID")String strUserID,@Param("strStartDate")String strStartDate,@Param("strEndDate")String strEndDate, Subscriber<String> s){
        String[] values = {strUserID,strStartDate,strEndDate};
        ContentTools.getResult(Service,IP_Address.NAMESPACE,values,Thread.currentThread().getStackTrace(),RemoteDataByAppMemberService.class,s);
    }

    /**
     * 获取机器型号信息
     * @param s
     */
    public static void AppMachineModelInfo( Subscriber<String> s){
        String[] values = {};
        ContentTools.getResult(Service,IP_Address.NAMESPACE,values,Thread.currentThread().getStackTrace(),RemoteDataByAppMemberService.class,s);
    }

    /**
     * 获取数据分析数据
     * @param s
     */
    public static void AppDataAnalyze( Subscriber<String> s){
        String[] values = {};
        ContentTools.getResult(Service,IP_Address.NAMESPACE,values,Thread.currentThread().getStackTrace(),RemoteDataByAppMemberService.class,s);
    }
}
