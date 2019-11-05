package allen.api.rush.model;


/**
 * 一个测试的的实体类
 */

public class AnyObject {
    private Long id;
    private String name;
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
}
