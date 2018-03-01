package br.com.entelgy.commons.expando;

public class ColumnConfiguration {
    
    private Class<?> clazz;
    private String columnName;
    private int columnType;
    private boolean required;
    private boolean hidden;
    
    public ColumnConfiguration(Class<?> clazz, String columnName, 
            int columnType, boolean required, boolean hidden) {
        
        this.clazz = clazz;
        this.columnName = columnName;
        this.columnType = columnType;
        this.required = required;
        this.hidden = hidden;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((clazz.getName() == null) ? 0 : clazz.getName().hashCode());
        result = prime * result
                + ((columnName == null) ? 0 : columnName.hashCode());
        result = prime * result + columnType;
        result = prime * result + (hidden ? 1231 : 1237);
        result = prime * result + (required ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ColumnConfiguration other = (ColumnConfiguration) obj;
        if (clazz == null) {
            if (other.getClazz() != null) {
                return false;
            }
        } else if (!clazz.isAssignableFrom(obj.getClass())) {
            return false;
        } else if (columnName == null) {
            if (other.columnName != null)
                return false;
        } else if (!columnName.equals(other.columnName))
            return false;
        if (columnType != other.columnType)
            return false;
        if (hidden != other.hidden)
            return false;
        if (required != other.required)
            return false;
        return true;
    }

    
    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
    
    public String getColumnName() {
        return columnName;
    }
    
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
    
    public int getColumnType() {
        return columnType;
    }
    
    public void setColumnType(int columnType) {
        this.columnType = columnType;
    }
    
    public boolean isRequired() {
        return required;
    }
    
    public void setRequired(boolean required) {
        this.required = required;
    }
    
    public boolean isHidden() {
        return hidden;
    }
    
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

}
