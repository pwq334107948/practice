import java.util.Objects;

/*
ChuKun OuYang
 */

/*
This class has no actual meaning, just because Wei Qiang wrote login,
did not add to the chat needs of Firestore inside, so write this class.
 */
public class AccountInfo {


    private String email;
    private String name;
    private String uid;

    public AccountInfo(String email,
                       String name,
                       String uid) {
        this.email = email;
        this.name = name;
        this.uid = uid;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        AccountInfo that = (AccountInfo) o;
        return Objects.equals(email, that.email) && Objects.equals(name, that.name) && Objects.equals(uid, that.uid) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name, uid);
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}
