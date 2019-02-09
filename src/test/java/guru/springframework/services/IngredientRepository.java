package guru.springframework.services;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.domain.Ingredient;

interface IngredientRepository
    extends CrudRepository<Ingredient, Long>
{
}