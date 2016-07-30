package cn.edu.cdut.myfirstapp.Model;

/**
 * 通话记录实体类
 * Created by Administrator on 2016/7/26 0026.
 */

public class CallLogBean {
    private int id;
    private String name; // 名称
    private String number; // 号码
    private String date; // 日期
    private int type; // 来电:1，拨出:2,未接:3
    private int count; // 通话次数
    private int photoId;
    private int contactId;

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public int getContactId() {

        return contactId;
    }

    private String  uri;

    public int getPhotoId() {
        return photoId;
    }

    public String getUri() {
        return uri;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
