package prosecutor.barrister.languages;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by Fry on 03.07.2016.
 */
public class TokensTest {


    @BeforeClass
    public void setTokensID()
    {
        Tokens.generateIndex();
    }
    @AfterClass
    public void resetTokensID()
    {
        Tokens.resetIndexs();
    }

    @Test
    public void tokengeneration()
    {

    }
}
