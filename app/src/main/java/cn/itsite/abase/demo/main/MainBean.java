package cn.itsite.abase.demo.main;

/**
 * Created by leguang on 2017/3/25 0025.
 * 联系邮箱:langmanleguang@qq.com
 */
public class MainBean {
    public final Class<?> clazz;
    public String title;
    public String description;

    public MainBean(String title, String description, Class<?> clazz) {
        this.title = title;
        this.description = description;
        this.clazz = clazz;
    }
}
