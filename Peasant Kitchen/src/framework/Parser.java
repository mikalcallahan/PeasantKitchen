package framework;

public interface Parser<Input, Result>
{
    Result parse(Input input) throws Exception;
}
