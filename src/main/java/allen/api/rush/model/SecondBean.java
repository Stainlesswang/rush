package allen.api.rush.model;

/**
 * @author AllenWong
 * @date 2019/12/20 11:13 AM
 */
public class SecondBean {
    private Integer num;
    private String  name;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SecondBean{" +
                "num=" + num +
                ", name='" + name + '\'' +
                '}';
    }
}
