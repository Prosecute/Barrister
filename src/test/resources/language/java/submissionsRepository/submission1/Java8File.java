package language.java.submissionsRepository.submission1;

public class Java8File {

    Java8File previous,next;
    int value=-1;

    public Java8File()
    {

    }
    public Java8File(Java8File previous)
    {
        this.previous=previous;
    }
    public Java8File(int value)
    {
        this.value=value;
    }
    public Java8File(Java8File previous, int value)
    {
        this.previous=previous;
        if(value>-1)
            this.value=value;
    }

    public int getValue()
    {
        return value;
    }
    
    public Java8File getNext() {
        return next;
    }

    public Java8File getPrevious() {
        return previous;
    }

    public void setNext(Java8File next) {
        this.next = next;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setPrevious(Java8File previous) {
        this.previous = previous;
    }
}