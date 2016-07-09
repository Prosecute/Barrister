package language.java.token;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import prosecutor.barrister.languages.Tokens;
import prosecutor.barrister.languages.java_default.JavaParser;
import prosecutor.barrister.submissions.tokens.TokensMapping;

import java.io.File;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Fry on 01.07.2016.
 */
public class TokenConvertTest {

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
    public void testTokenConvert()
    {
        JavaParser parser=new JavaParser();
        File file=new File("src/test/resources/language/java/tokenTesting/source.java");
        Set<Path> paths=new HashSet<>();
        paths.add(file.toPath()); 
        TokensMapping tokens=parser.parseTokens(paths);

    }
}
