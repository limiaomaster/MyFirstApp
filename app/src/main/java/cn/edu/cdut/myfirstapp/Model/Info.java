package cn.edu.cdut.myfirstapp.Model;

/**
 * Created by Administrator on 2016/5/27 0027.
 */

public class Info {
    private int icon;

    private String name;
    private String content;
    private String time;
    @Override
    public String toString() {
        return "Info [name=" + name + ", content=" + content + ", icon=" + icon
                + ", time=" + time + "]";
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getIcon() {
        return icon;
    }
    public void setIcon(int icon) {
        this.icon = icon;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
}
