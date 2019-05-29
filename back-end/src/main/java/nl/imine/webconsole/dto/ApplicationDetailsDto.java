package nl.imine.webconsole.dto;

public class ApplicationDetailsDto {

    private final String id;
    private final String name;
    private final int colorHue;

    public ApplicationDetailsDto(String id, String name, int colorHue) {
        this.id = id;
        this.name = name;
        this.colorHue = colorHue;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getColorHue() {
        return colorHue;
    }
}
