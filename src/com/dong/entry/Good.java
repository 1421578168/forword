package com.dong.entry;

public class Good {
    private Integer id;
    private String gname;
    private String gtype;
    private Double price;
    private String pic;

    public Good() {
    }

    public Good(String gname, String gtype, Double price, String pic) {
        this.gname = gname;
        this.gtype = gtype;
        this.price = price;
        this.pic = pic;
    }

    public Good(Integer id, String gname, String gtype, Double price, String pic) {
        this.id = id;
        this.gname = gname;
        this.gtype = gtype;
        this.price = price;
        this.pic = pic;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getGtype() {
        return gtype;
    }

    public void setGtype(String gtype) {
        this.gtype = gtype;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "Good{" +
                "id=" + id +
                ", gname='" + gname + '\'' +
                ", gtype='" + gtype + '\'' +
                ", price=" + price +
                ", pic='" + pic + '\'' +
                '}';
    }
}
