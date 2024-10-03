package guru.qa.niffler.utils;

import com.github.javafaker.Faker;

public class RandomDataUtils {
    private static final Faker faker = new Faker();

    public static String randomUsername(){
        return faker.name().username();
    }

    public static String randomName(){
        return faker.name().name();
    }

    public static String randomSurname(){
        return faker.name().lastName();
    }

    public static String randomCategoryName(){
        return faker.food().ingredient();
    }

    public static String randomSentence(int wordCount){
        return faker.lorem().sentence(wordCount);
    }
}
