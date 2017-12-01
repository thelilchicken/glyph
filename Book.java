package main;
public class Book extends Item {
    
    private String content;
    
    public Book(String title, int worth, int weight, int damage) {
        super(title, 65, worth, damage, weight);
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        setBookContent(content);
    }
    
}