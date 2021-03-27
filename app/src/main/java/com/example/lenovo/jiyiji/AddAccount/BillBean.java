package com.example.lenovo.jiyiji.AddAccount;

public class BillBean {

    private int id;
    private Double money;
    private String choose;
    private String sort;
    private String time;
    private String remark;

    public BillBean(Double money, String choose, String sort, String time, String remark)
    {
        this.money = money;
        this.choose = choose;
        this.sort = sort;
        this.time = time;
        this.remark = remark;
    }

    public BillBean(int id, Double money, String choose, String sort, String time, String remark)
    {
        this.id = id;
        this.money = money;
        this.choose = choose;
        this.sort = sort;
        this.time = time;
        this.remark = remark;
    }

    public int getId(){return id;}

    public Double getMoney() {
        return money;
    }

    public String getChoose() {
        return choose;
    }

    public String getSort() {
        return sort;
    }

    public String getTime() {
        return time;
    }

    public String getRemark() {
        return remark;
    }

}
