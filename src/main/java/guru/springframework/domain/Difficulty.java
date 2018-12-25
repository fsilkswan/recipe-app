package guru.springframework.domain;

import javax.persistence.Entity;

@Entity
public enum Difficulty
{
    EASY, HARD, KIND_OF_HARD, MODERATE;
}