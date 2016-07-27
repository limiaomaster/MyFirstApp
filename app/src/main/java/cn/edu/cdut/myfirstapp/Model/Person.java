package cn.edu.cdut.myfirstapp.Model;

/**
 * Created by Administrator on 2016/6/2 0002.
 */

public class Person {
    private int imageId;
    private String name;

    public Person(int imageId, String name) {
        this.imageId = imageId;
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }
}
