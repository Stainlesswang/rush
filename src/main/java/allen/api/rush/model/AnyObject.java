package allen.api.rush.model;


import java.util.List;

/**
 * 一个测试的的实体类
 */

public class AnyObject {
    private Long id;
    private String name;

    private List<SecondBean> secondBeans;
    AnyObject(){

    }
    public AnyObject(Long id, String name){
        this.id=id;
        this.name=name;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SecondBean> getSecondBeans() {
        return secondBeans;
    }

    public void setSecondBeans(List<SecondBean> secondBeans) {
        this.secondBeans = secondBeans;
    }

    @Override
    public String toString() {
        return "AnyObject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", secondBeans=" + secondBeans +
                '}';
    }
}
