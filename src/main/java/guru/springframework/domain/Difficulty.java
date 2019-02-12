package guru.springframework.domain;

public enum Difficulty
{
    /**
     *
     */
    EASY("Easy"),

    /**
     *
     */
    HARD("Hard"),

    /**
     *
     */
    KIND_OF_HARD("Kind-a-Hard"),

    /**
     *
     */
    MODERATE("Moderate");

    private final String text;

    Difficulty(final String text)
    {
        this.text = text;
    }

    @Override
    public String toString()
    {
        return text;
    }
}