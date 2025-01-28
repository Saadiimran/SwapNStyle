
public class Item {
    private static int idCounter = 1;
    private int id;
    private String name;
    private String size;
    private String condition;
    private String category;
    private String status;

    public Item(String name, String size, String condition, String category) {
        this.id = idCounter++;
        this.name = name;
        this.size = size;
        this.condition = condition;
        this.category = category;
        this.status = "Available";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getCondition() {
        return condition;
    }

    public String getCategory() {
        return category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Size: " + size + ", Condition: " + condition + ", Category: " + category;
    }
    
    
}
