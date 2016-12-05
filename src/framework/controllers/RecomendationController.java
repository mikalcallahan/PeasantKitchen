package framework.controllers;

import designPatterns.Observer;
import framework.Recipes;
import framework.User;


public interface RecomendationController extends Observer
{
    Recipes recommend(User user);

    @Override
    void update();
}
