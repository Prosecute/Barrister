package language.java.integration_tests.test_1.submissions.submission0;

public class TestedSource {

    language.java.integration_tests.test_1.submissions.submission1.TestedSource previous,next;
    int value=-1;

    public TestedSource()
    {

    }
    public TestedSource(language.java.integration_tests.test_1.submissions.submission1.TestedSource previous)
    {
        this.previous=previous;
    }
    public TestedSource(int value)
    {
        this.value=value;
    }
    public TestedSource(language.java.integration_tests.test_1.submissions.submission1.TestedSource previous, int value)
    {
        this.previous=previous;
        if(value>-1)
            this.value=value;
    }

    public int getValue()
    {
        return value;
    }

    public language.java.integration_tests.test_1.submissions.submission1.TestedSource getNext() {
        return next;
    }

    public language.java.integration_tests.test_1.submissions.submission1.TestedSource getPrevious() {
        return previous;
    }

    public void setNext(language.java.integration_tests.test_1.submissions.submission1.TestedSource next) {
        this.next = next;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setPrevious(language.java.integration_tests.test_1.submissions.submission1.TestedSource previous) {
        this.previous = previous;
    }
}