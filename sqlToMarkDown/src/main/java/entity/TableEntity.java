package entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class TableEntity implements Serializable {

    private String field;

    private String type;

    private String snull;

    private String defaultValue;

    private String comment;

    private String  primaryKey;

    private String extra;

    @Override
    public String toString() {
        return new StringBuffer()
                .append(this.field).append(" | ").append(type).append(" | ")
                .append(this.snull).append(" | ").append(defaultValue).append(" | ")
                .append(comment).append(" | ").append(primaryKey).append(" | ").append(extra).append(" ").toString();
    }


}
