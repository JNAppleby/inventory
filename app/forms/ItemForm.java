package forms;

import entities.Item;

import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

/**
 * Form for the item with validation
 *
 * @author ymudryy
 *
 */
public class ItemForm {
    @Required
    @MinLength(value = Item.MIN_NAME_LEN)
    private String name;

    private String desc;

    public ItemForm() {}

    public ItemForm(String name, String desc) {
        this.name = name;
        this.desc = desc;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append("ItemForm name=").append(name)
        .append(", desc=").append(desc);

        return sb.toString();
    }
}
