package cn.itsite.abase.event;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class EventData {
    public final String TAG = this.getClass().getSimpleName();
    public int position;

    public EventData(int position) {
        this.position = position;
    }
}
