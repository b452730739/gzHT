package com.elegps.javabean;

import android.os.Parcel;
import android.os.Parcelable;

public class DataAnalyzeWraper implements Parcelable{


    /**
     * PrevMonthProduction : 0
     * PrevMonthStock : 0
     * PrevMonthPerHour :
     * NextMonthProduction : 20
     * NextMonthStock : 0
     * NextMonthPerHour : 705.41
     */

    private String PrevMonthProduction;
    private String PrevMonthStock;
    private String PrevMonthPerHour;
    private String NextMonthProduction;
    private String NextMonthStock;
    private String NextMonthPerHour;

    protected DataAnalyzeWraper(Parcel in) {
        PrevMonthProduction = in.readString();
        PrevMonthStock = in.readString();
        PrevMonthPerHour = in.readString();
        NextMonthProduction = in.readString();
        NextMonthStock = in.readString();
        NextMonthPerHour = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(PrevMonthProduction);
        dest.writeString(PrevMonthStock);
        dest.writeString(PrevMonthPerHour);
        dest.writeString(NextMonthProduction);
        dest.writeString(NextMonthStock);
        dest.writeString(NextMonthPerHour);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DataAnalyzeWraper> CREATOR = new Creator<DataAnalyzeWraper>() {
        @Override
        public DataAnalyzeWraper createFromParcel(Parcel in) {
            return new DataAnalyzeWraper(in);
        }

        @Override
        public DataAnalyzeWraper[] newArray(int size) {
            return new DataAnalyzeWraper[size];
        }
    };

    public String getPrevMonthProduction() {
        return PrevMonthProduction;
    }

    public void setPrevMonthProduction(String PrevMonthProduction) {
        this.PrevMonthProduction = PrevMonthProduction;
    }

    public String getPrevMonthStock() {
        return PrevMonthStock;
    }

    public void setPrevMonthStock(String PrevMonthStock) {
        this.PrevMonthStock = PrevMonthStock;
    }

    public String getPrevMonthPerHour() {
        return PrevMonthPerHour;
    }

    public void setPrevMonthPerHour(String PrevMonthPerHour) {
        this.PrevMonthPerHour = PrevMonthPerHour;
    }

    public String getNextMonthProduction() {
        return NextMonthProduction;
    }

    public void setNextMonthProduction(String NextMonthProduction) {
        this.NextMonthProduction = NextMonthProduction;
    }

    public String getNextMonthStock() {
        return NextMonthStock;
    }

    public void setNextMonthStock(String NextMonthStock) {
        this.NextMonthStock = NextMonthStock;
    }

    public String getNextMonthPerHour() {
        return NextMonthPerHour;
    }

    public void setNextMonthPerHour(String NextMonthPerHour) {
        this.NextMonthPerHour = NextMonthPerHour;
    }
}
