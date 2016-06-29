package prosecutor.barrister.languages.java_default;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import prosecutor.barrister.grammar.Java8BaseListener;
import prosecutor.barrister.grammar.Java8Parser;
import prosecutor.barrister.languages.Tokens;
import prosecutor.barrister.submissions.SubmissionTokens;

public class JavaDefaultListener extends Java8BaseListener {

    //TODO enterExprMethodExpressionList

    SubmissionTokens.SubmissionFileTokens T;

    public JavaDefaultListener(SubmissionTokens.SubmissionFileTokens tokens)
    {
        this.T=tokens;
    }

    //SEGMENT START: CLASS CONSTRUCTOR

    @Override
    public void enterConstructorDeclaration(Java8Parser.ConstructorDeclarationContext ctx) {
        T.addToken(Tokens.Class.CLASS_CONSTRUCTOR_START, ctx.getStart());
    }

    @Override
    public void exitConstructorDeclaration(Java8Parser.ConstructorDeclarationContext ctx) {
        T.addToken(Tokens.Class.CLASS_CONSTRUCTOR_END,ctx.getStop());
    }
    //SEGMENT END: CLASS CONSTRUCTOR

    //SEGMENT START: EXPRESSION
    @Override
    public void enterConditionalExpression(Java8Parser.ConditionalExpressionContext ctx) {
       T.addToken(Tokens.Expressions.CONDITIONAL_EXPRESSION_START,ctx.getStart());
    }

    @Override
    public void enterAssignmentExpression(Java8Parser.AssignmentExpressionContext ctx) {
        T.addToken(Tokens.Expressions.ASSIGNMENT_EXPRESSION_START,ctx.getStart());
    }
    //SEGMENT END: EXPRESSION

    //SEGMENT START: METHOD

    @Override
    public void enterMethodDeclaration(Java8Parser.MethodDeclarationContext ctx) {
        if (ctx.start.getText().contains("void"))
            T.addToken(Tokens.ReturnTypes.RETURN_VOID,ctx.getStart());  //TODO TEST
        T.addToken(Tokens.Class.CLASS_METHOD_START,ctx.getStart());
    }

    @Override
    public void exitMethodDeclaration(Java8Parser.MethodDeclarationContext ctx) {
        T.addToken(Tokens.Class.CLASS_METHOD_END,ctx.getStop());
    }
    //SEGMENT END: METHOD


    //SEGMENT START: CONDITIONS
    //SEGMENT START: WHILE
    @Override
    public void enterWhileStatement(Java8Parser.WhileStatementContext ctx) {
        T.addToken(Tokens.Conditions.WHILE_START,ctx.getStart());
    }

    @Override
    public void exitWhileStatement(Java8Parser.WhileStatementContext ctx) {
        T.addToken(Tokens.Conditions.WHILE_STOP,ctx.getStop());
    }
    //SEGMENT END: WHILE

    //SEGMENT START: FOR

    @Override
    public void enterForStatement(Java8Parser.ForStatementContext ctx) {
        T.addToken(Tokens.Conditions.FOR_START,ctx.getStart());
    }

    @Override
    public void exitForStatement(Java8Parser.ForStatementContext ctx) {
        T.addToken(Tokens.Conditions.FOR_STOP,ctx.getStop());
    }
    //SEGMENT END: FOR

    //SEGMENT START: SWITCH

    @Override
    public void enterSwitchStatement(Java8Parser.SwitchStatementContext ctx) {
        T.addToken(Tokens.Conditions.SWITCH_START,ctx.getStart());
    }

    @Override
    public void exitSwitchStatement(Java8Parser.SwitchStatementContext ctx) {
        T.addToken(Tokens.Conditions.SWITCH_STOP,ctx.getStop());
    }
    //SEGMENT END: SWITCH

    //SEGMENT START: IF
    //SEGMENT END: IF

    //SEGMENT END: CONDITIONS

    //SEGMENT START: STATEMENTS
    //SEGMENT START: ASSERT

    @Override
    public void enterAssertStatement(Java8Parser.AssertStatementContext ctx) {
        T.addToken(Tokens.Statements.ASSERT,ctx.getStart());
    }

    //SEGMENT END: ASSERT
    //SEGMENT START: CONTINUE

    @Override
    public void enterContinueStatement(Java8Parser.ContinueStatementContext ctx) {
        T.addToken(Tokens.Statements.CONTINUE,ctx.getStart());
    }

    //SEGMENT END: CONTINUE
    //SEGMENT START: RETURN

    @Override
    public void enterReturnStatement(Java8Parser.ReturnStatementContext ctx) {
        T.addToken(Tokens.Statements.RETURN,ctx.getStart());
    }

    //SEGMENT END: RETURN
    //SEGMENT START: SYNCHRONIZED

    @Override
    public void enterSynchronizedStatement(Java8Parser.SynchronizedStatementContext ctx) {
        T.addToken(Tokens.Statements.SYNCHRONIZED_START,ctx.getStart());
    }

    @Override
    public void exitSynchronizedStatement(Java8Parser.SynchronizedStatementContext ctx) {
        T.addToken(Tokens.Statements.SYNCHRONIZED_STOP,ctx.getStop());
    }
    //SEGMENT END: SYNCHRONIZED

    //SEGMENT START: THROW

    @Override
    public void enterThrowStatement(Java8Parser.ThrowStatementContext ctx) {
        T.addToken(Tokens.Statements.THROW,ctx.getStart());
    }

    //SEGMENT END: THROW
    //SEGMENT END: STATEMENTS
}
