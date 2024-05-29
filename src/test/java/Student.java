import java.time.LocalDateTime;
import java.util.Date;

public class Student implements Cloneable {
    private String name;
    private String birthDay;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }
}
