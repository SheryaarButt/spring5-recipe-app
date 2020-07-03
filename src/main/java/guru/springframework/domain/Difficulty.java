package guru.springframework.domain;

public enum Difficulty {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard");

    private final String simpleName;

    Difficulty(String simpleName){
        this.simpleName = simpleName;
    }

    public String getSimpleName(){
        return simpleName;
    }

}
