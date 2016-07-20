package forms;

import play.data.validation.Constraints.Required;

/**
 * Form for the item with validation
 * @author ymudryy
 *
 */
public class ItemForm {
    private Long id;

    @Required
    private String name;

    private String desc;

    public ItemForm(){};

    public ItemForm(Long id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    public ItemForm(String name, String desc){
        this.name = name;
        this.desc = desc;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }



}