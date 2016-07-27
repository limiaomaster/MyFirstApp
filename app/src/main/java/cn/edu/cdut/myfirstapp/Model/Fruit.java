package cn.edu.cdut.myfirstapp.Model;

/**
 * Created by Administrator on 2016/5/30 0030.
 */

public class Fruit {
    private String name;
    private int imageId;

    public Fruit(String name , int imageId ){
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
