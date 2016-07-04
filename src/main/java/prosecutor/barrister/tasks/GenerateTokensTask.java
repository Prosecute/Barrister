package prosecutor.barrister.tasks;

/**
 * Created by Fry on 02.07.2016.
 */
public class GenerateTokensTask extends Task {
    @Override
    public String[] getTaskIDs() {
        return new String[]{"generateTokens","GenerateTokens"};
    }

    @Override
    public String getTaskName() {
        return "Generate tokens";
    }

    @Override
    public String getShortInfo() {
        return null;
    }

    @Override
    public String getLongInfo() {
        return null;
    }

    @Override
    public void run() {

    }

    @Override
    protected void resolveParams(String... params) {

    }
}
