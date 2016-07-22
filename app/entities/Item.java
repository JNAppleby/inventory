package entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "desc")
    private String desc;

    public Item() {}

    public Item(Long id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    public Item(String name, String desc) {
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

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDesc());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Item))
            return false;
        Item other = (Item)obj;
        return Objects.equals(getId(), other.getId()) &&
                        Objects.equals(getName(), other.getName()) &&
                        Objects.equals(getDesc(), other.getDesc());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append("Item [id=").append(id)
        .append(", name=").append(name)
        .append(", desc=").append(desc);

        return sb.toString();
    }


}
