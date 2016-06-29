package prosecutor.barrister.languages.java_default;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import prosecutor.barrister.grammar.Java8Lexer;
import prosecutor.barrister.grammar.Java8Parser;
import prosecutor.barrister.languages.Parser;
import prosecutor.barrister.submissions.Submission;
import prosecutor.barrister.submissions.SubmissionTokens;

import java.io.*;
import java.nio.file.Path;
import java.util.Set;

public class JavaParser extends Parser {

    @Override
    public SubmissionTokens parseTokens(Set<Path> files){

        //TODO solve storing errors in SubmissionTokens
        SubmissionTokens output=new SubmissionTokens();
        ANTLRInputStream inputStream;
        for(Path file:files) {
            try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(file.toFile()))) {
                inputStream = new ANTLRInputStream(stream);
                Java8Lexer lexer=new Java8Lexer(inputStream);
                CommonTokenStream commonTokenStream=new CommonTokenStream(lexer);
                Java8Parser parser=new Java8Parser(commonTokenStream);
                Java8Parser.CompilationUnitContext unitContext=parser.compilationUnit();
                ParseTreeWalker ptw=new ParseTreeWalker();
                for(int i=0;i<unitContext.getChildCount();i++)
                    ptw.walk(new JavaDefaultListener(file.toString(),output),unitContext.getChild(i));

            } catch (FileNotFoundException e) {
                //TODO
                e.printStackTrace();
                errors++;
            } catch (IOException e) {
                e.printStackTrace();
                errors++;
            }
        }
        return output;
    }
}
