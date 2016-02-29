package models;
import java.awt.Color;

public abstract class TikzComponent {
    private Color color;
    private String label;

    public Color getColor(){
        return color;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
