package language.java.integration_tests.test_1.submissions.submission1;

public class TestedSource {

    TestedSource previous,next;
    int value=-1;

    public TestedSource()
    {

    }
    public TestedSource(TestedSource previous)
    {
        this.previous=previous;
    }
    public TestedSource(int value)
    {
        this.value=value;
    }
    public TestedSource(TestedSource previous, int value)
    {
        this.previous=previous;
        if(value>-1)
            this.value=value;
    }

    public int getValue()
    {
        return value;
    }

    public TestedSource getNext() {
        return next;
    }

    public TestedSource getPrevious() {
        return previous;
    }

    public void setNext(TestedSource next) {
        this.next = next;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setPrevious(TestedSource previous) {
        this.previous = previous;
    }
}