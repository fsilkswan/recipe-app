package guru.springframework.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.domain.Category;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;

@Controller
public class IndexController
{
    private final CategoryRepository      categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(final CategoryRepository categoryRepository, final UnitOfMeasureRepository unitOfMeasureRepository)
    {
        super();
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({ "", "/", "index" })
    public String getIndexPage()
    {
        final Optional<Category> categoryQueryResult = categoryRepository.findByDescription("American");
        System.out.println("Cat ID is: " + categoryQueryResult.get().getId());

        final Optional<UnitOfMeasure> uomQueryResult = unitOfMeasureRepository.findByDescription("Teaspoon");
        System.out.println("UoM ID is: " + uomQueryResult.get().getId());

        return "index";
    }
}