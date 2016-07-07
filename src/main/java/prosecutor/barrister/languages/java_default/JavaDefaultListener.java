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
import prosecutor.barrister.submissions.tokens.TokensMapping;

public class JavaDefaultListener extends Java8BaseListener {

    //TODO enterExprMethodExpressionList

    TokensMapping.TokensMappingInserter T;

    public JavaDefaultListener(TokensMapping.TokensMappingInserter tokens)
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
        T.addToken(Tokens.Class.CLASS_CONSTRUCTOR_STOP,ctx.getStop());
    }
    //SEGMENT END: CLASS CONSTRUCTOR

    //SEGMENT START: EXPRESSION
    @Override
    public void enterConditionalExpression(Java8Parser.ConditionalExpressionContext ctx) {
       T.addToken(Tokens.Expressions.CONDITIONAL,ctx.getStart());
    }

    @Override
    public void enterAssignmentExpression(Java8Parser.AssignmentExpressionContext ctx) {
        T.addToken(Tokens.Expressions.ASSIGNMENT,ctx.getStart());
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
        T.addToken(Tokens.Class.CLASS_METHOD_STOP,ctx.getStop());
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

    //SEGMENT START: DO_WHILE

    @Override
    public void enterDoStatement(Java8Parser.DoStatementContext ctx) {
        T.addToken(Tokens.Conditions.DO_WHILE_START,ctx.getStart());
    }

    @Override
    public void exitDoStatement(Java8Parser.DoStatementContext ctx) {
        T.addToken(Tokens.Conditions.DO_WHILE_STOP,ctx.getStop());
    }
    //SEGMENT END: DO_WHILE

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

    @Override
    public void exitSwitchLabel(Java8Parser.SwitchLabelContext ctx) {
        T.addToken(Tokens.Conditions.SWITCH_CASE,ctx.getStop());
    }
    //SEGMENT END: SWITCH

    //SEGMENT START: IF

    @Override
    public void enterIfThenStatement(Java8Parser.IfThenStatementContext ctx) {
        T.addToken(Tokens.Conditions.IF_START,ctx.getStart());
    }

    @Override
    public void exitIfThenStatement(Java8Parser.IfThenStatementContext ctx) {
        T.addToken(Tokens.Conditions.IF_STOP,ctx.getStop());
    }
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
        T.addToken(Tokens.Blocks.SYNCHRONIZED_START,ctx.getStart());
    }

    @Override
    public void exitSynchronizedStatement(Java8Parser.SynchronizedStatementContext ctx) {
        T.addToken(Tokens.Blocks.SYNCHRONIZED_STOP,ctx.getStop());
    }
    //SEGMENT END: SYNCHRONIZED

    //SEGMENT START: THROW

    @Override
    public void enterThrowStatement(Java8Parser.ThrowStatementContext ctx) {
        T.addToken(Tokens.Statements.THROW,ctx.getStart());
    }

    //SEGMENT END: THROW

    //SEGMENT START: BREAK
    @Override
    public void enterBreakStatement(Java8Parser.BreakStatementContext ctx) {
        T.addToken(Tokens.Statements.BREAK,ctx.getStart());
    }
    //SEGMENT END: BREAK


    //SEGMENT END: STATEMENTS

    //SEGMENT START: INITIALIZATIONS

    @Override
    public void enterArrayInitializer(Java8Parser.ArrayInitializerContext ctx) {
        T.addToken(Tokens.Initializations.ARRAY_INIT_START,ctx.getStart());
    }

    @Override
    public void exitArrayInitializer(Java8Parser.ArrayInitializerContext ctx) {
        T.addToken(Tokens.Initializations.ARRAY_INIT_STOP,ctx.getStop());
    }
    //SEGMENT END: INITIALIZATIONS


    @Override
    public void enterEnumDeclaration(Java8Parser.EnumDeclarationContext ctx) {
        T.addToken(Tokens.Class.ENUM_START,ctx.getStart());
    }

    @Override
    public void exitEnumDeclaration(Java8Parser.EnumDeclarationContext ctx) {
        T.addToken(Tokens.Class.ENUM_STOP, ctx.getStop());
    }

    @Override
    public void enterEnumConstant(Java8Parser.EnumConstantContext ctx) {
        T.addToken(Tokens.Class.ENUM_CONSTANT,ctx.getStart());
    }

    @Override
    public void enterConstantDeclaration(Java8Parser.ConstantDeclarationContext ctx) {
        T.addToken(Tokens.Initializations.CONSTANT,ctx.getStart());
    }

    @Override
    public void enterPackageDeclaration(Java8Parser.PackageDeclarationContext ctx) {
        T.addToken(Tokens.Class.PACKAGE,ctx.getStart());
    }

    @Override
    public void enterImportDeclaration(Java8Parser.ImportDeclarationContext ctx) {
        T.addToken(Tokens.Class.IMPORT,ctx.getStart());
    }

    @Override
    public void enterAnnotation(Java8Parser.AnnotationContext ctx) {
        T.addToken(Tokens.Class.ANNOTATION,ctx.getStart());
    }

    @Override
    public void enterExplicitConstructorInvocation(Java8Parser.ExplicitConstructorInvocationContext ctx) {

    }

    @Override
    public void enterVariableInitializer(Java8Parser.VariableInitializerContext ctx) {
        if(!(ctx.parent instanceof Java8Parser.ArrayInitializerContext))
            T.addToken(Tokens.Expressions.ASSIGNMENT,ctx.getStart());

    }

    @Override
    public void enterNormalInterfaceDeclaration(Java8Parser.NormalInterfaceDeclarationContext ctx) {
        T.addToken(Tokens.Class.INTERFACE_START,ctx.getStart());
    }

    @Override
    public void exitNormalInterfaceDeclaration(Java8Parser.NormalInterfaceDeclarationContext ctx) {
        T.addToken(Tokens.Class.INTERFACE_STOP,ctx.getStop());
    }

    @Override
    public void enterNormalClassDeclaration(Java8Parser.NormalClassDeclarationContext ctx) {
        T.addToken(Tokens.Class.CLASS_START,ctx.getStart());
    }

    @Override
    public void exitNormalClassDeclaration(Java8Parser.NormalClassDeclarationContext ctx) {
        T.addToken(Tokens.Class.CLASS_STOP,ctx.getStop());
    }

    @Override
    public void enterTryWithResourcesStatement(Java8Parser.TryWithResourcesStatementContext ctx) {
        T.addToken(Tokens.Blocks.TRY_WITH_RESOURCE_START,ctx.getStart());
    }

    @Override
    public void exitTryWithResourcesStatement(Java8Parser.TryWithResourcesStatementContext ctx) {
        T.addToken(Tokens.Blocks.TRY_WITH_RESOURCE_STOP,ctx.getStop());
    }

    @Override
    public void enterTryStatement(Java8Parser.TryStatementContext ctx) {
        T.addToken(Tokens.Blocks.TRY_START, ctx.getStart());
    }

    @Override
    public void enterFinally_(Java8Parser.Finally_Context ctx) {
        T.addToken(Tokens.Blocks.FINALLY_START,ctx.getStart());
    }

    @Override
    public void exitFinally_(Java8Parser.Finally_Context ctx) {
        T.addToken(Tokens.Blocks.FINALLY_STOP,ctx.getStop());
    }

    @Override
    public void enterCatchClause(Java8Parser.CatchClauseContext ctx) {
        T.addToken(Tokens.Blocks.CATCH_START, ctx.getStart());
    }

    @Override
    public void exitCatchClause(Java8Parser.CatchClauseContext ctx) {
        T.addToken(Tokens.Blocks.CATCH_STOP, ctx.getStop());
    }

    @Override
    public void exitTryStatement(Java8Parser.TryStatementContext ctx) {
        T.addToken(Tokens.Blocks.TRY_STOP,ctx.getStop());
    }

    @Override
    public void enterTypeArgument(Java8Parser.TypeArgumentContext ctx) {
        //TODO Test
        T.addToken(Tokens.GENERIC_TOKEN,ctx.getStart());
    }
}
