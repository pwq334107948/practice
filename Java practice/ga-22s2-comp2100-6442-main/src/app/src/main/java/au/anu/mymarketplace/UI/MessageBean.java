import java.util.Objects;

/**
 * ChuKun OuYang
 */
public class MessageBean
{
    public String  content;
    public String  uid;

    public MessageBean()
    {
        // need empty construct
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageBean that = (MessageBean) o;
        return Objects.equals(content, that.content)  && Objects.equals(uid, that.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content,  uid);
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "content='" + content + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }


}


