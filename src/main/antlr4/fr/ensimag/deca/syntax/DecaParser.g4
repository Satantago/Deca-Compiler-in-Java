parser grammar DecaParser;

options {
    // Default language but name it anyway
    //
    language  = Java;

    // Use a superclass to implement all helper
    // methods, instance variables and overrides
    // of ANTLR default methods, such as error
    // handling.
    //
    superClass = AbstractDecaParser;

    // Use the vocabulary generated by the accompanying
    // lexer. Maven knows how to work out the relationship
    // between the lexer and parser and will build the
    // lexer before the parser. It will also rebuild the
    // parser if the lexer changes.
    tokenVocab = DecaLexer;

}

// which packages should be imported?
@header {
    import fr.ensimag.deca.tree.*;
    import fr.ensimag.deca.tools.SymbolTable;
    import java.io.PrintStream;
}

@members {
    @Override
    protected AbstractProgram parseProgram() {
        return prog().tree;
    }
}

prog returns[AbstractProgram tree]
    : list_classes main EOF {
            assert($list_classes.tree != null);
            assert($main.tree != null);
            $tree = new Program($list_classes.tree, $main.tree);
            setLocation($tree, $list_classes.start);
        }
    ;

main returns[AbstractMain tree]
    : /* epsilon */ {
            $tree = new EmptyMain();
        }
    | block {
            assert($block.decls != null);
            assert($block.insts != null);
            $tree = new Main($block.decls, $block.insts);
            setLocation($tree, $block.start);
        }
    ;

block returns[ListDeclVar decls, ListInst insts]
    : OBRACE list_decl list_inst CBRACE {
            assert($list_decl.tree != null);
            assert($list_inst.tree != null);
            $decls = $list_decl.tree;
            $insts = $list_inst.tree;
        }
    ;

list_decl returns[ListDeclVar tree]
@init   {
            $tree = new ListDeclVar();
        }
    : decl_var_set[$tree]*
    ;

decl_var_set[ListDeclVar l]
    : type list_decl_var[$l, $type.tree] SEMI
    ;

list_decl_var[ListDeclVar l, AbstractIdentifier t]
    : dv1=decl_var[$t] {
    assert($dv1.tree != null);
    $l.add($dv1.tree);
    }
     (COMMA dv2=decl_var[$t] {
        assert($dv2.tree != null);
         $l.add($dv2.tree);
        }
      )*
    ;

decl_var[AbstractIdentifier t] returns[AbstractDeclVar tree]
@init   {
            AbstractInitialization initial = new NoInitialization();
        }
    : i=ident {
            assert($i.tree != null);
        }
      (EQUALS e=expr {
            assert($e.tree != null);
            initial = new Initialization($e.tree);
            setLocation(initial, $e.start);  
        }
      )? {
            assert($t != null);
            $tree = new DeclVar($t, $i.tree, initial);
            setLocation($tree, $i.start);
        }
    ;

list_inst returns[ListInst tree]
@init {
            $tree = new ListInst();
}
    : (inst {
            assert($inst.tree != null);
            $tree.add($inst.tree);
        }
      )*
    ;

inst returns[AbstractInst tree]
    : e1=expr SEMI {
            assert($e1.tree != null);
            $tree = $e1.tree;
        }
    | SEMI {
            $tree = new NoOperation();
            setLocation($tree, $SEMI);
        }
    | PRINT OPARENT list_expr CPARENT SEMI {
            assert($list_expr.tree != null);
            $tree = new Print(false, $list_expr.tree);
            setLocation($tree, $PRINT);

        }
    | PRINTLN OPARENT list_expr CPARENT SEMI {
            assert($list_expr.tree != null);
            $tree = new Println(false, $list_expr.tree);
            setLocation($tree, $PRINTLN);
        }
    | PRINTX OPARENT list_expr CPARENT SEMI {
            assert($list_expr.tree != null); 
            $tree = new Print(true, $list_expr.tree);
            setLocation($tree, $PRINTX);
        }
    | PRINTLNX OPARENT list_expr CPARENT SEMI {
            assert($list_expr.tree != null);
            $tree = new Println(true, $list_expr.tree);
            setLocation($tree, $PRINTLNX);
        }
    | if_then_else {
            assert($if_then_else.tree != null);
            $tree = $if_then_else.tree;
        }
    | WHILE OPARENT condition=expr CPARENT OBRACE body=list_inst CBRACE {
            assert($condition.tree != null);
            assert($body.tree != null);
            $tree = new While($condition.tree,$body.tree);
            setLocation($tree, $WHILE);
        }
    | RETURN expr SEMI {
            assert($expr.tree != null);
            $tree = new Return($expr.tree);
            setLocation($tree, $RETURN);
        }
    ;

if_then_else returns[IfThenElse tree]
@init {
    AbstractExpr exp;
    ListInst then_list ; 
    ListIfElse elseif = new ListIfElse();
}
    : if1=IF OPARENT condition=expr CPARENT OBRACE li_if=list_inst CBRACE {
            assert($condition.tree != null);
            assert($li_if.tree != null);
            exp =  $condition.tree;
            then_list = $li_if.tree;
        }
      (ELSE elsif=IF OPARENT elsif_cond=expr CPARENT OBRACE elsif_li=list_inst CBRACE {
            assert($elsif_cond.tree != null);
            assert($elsif_li.tree != null);
            IfThenElse treeElseIf = new IfThenElse($elsif_cond.tree, $elsif_li.tree,false,null);
            setLocation(treeElseIf,$ELSE);
            elseif.add(treeElseIf);
        }
      )*
      (ELSE OBRACE li_else=list_inst CBRACE {
            assert($li_else.tree != null);
            AbstractExpr CdtElseTrue = new BooleanLiteral(true);
            setLocation(CdtElseTrue,$ELSE);
            IfThenElse treeElse = new IfThenElse(CdtElseTrue, $li_else.tree,true,null);
            setLocation(treeElse,$ELSE);
            elseif.add(treeElse);
        }
      )?
     {
            if(!elseif.isEmpty()){
                for(int i=elseif.size()-1;i>0;i--){
                    elseif.size();
                    elseif.getIndex(i-1).setArbe( elseif.getIndex(i));
                }
                    $tree = new IfThenElse(exp, then_list, false,elseif.getFirst()) ;
            }
            else $tree = new IfThenElse(exp, then_list, false, null) ;
            setLocation($tree,$if1);     
     }
    ;


list_expr returns[ListExpr tree]
@init   {
            $tree = new ListExpr();
        }
    : (e1=expr {
            assert($e1.tree != null);
            $tree.add($e1.tree);
        }
       (COMMA e2=expr {
            assert($e2.tree != null);
            $tree.add($e2.tree);
        }
       )* )?
    ;

expr returns[AbstractExpr tree]
    : assign_expr {
            assert($assign_expr.tree != null);
            $tree = $assign_expr.tree;
        } 
    ;

assign_expr returns[AbstractExpr tree]
    : e=or_expr (
        /* condition: expression e must be a "LVALUE" */ {
            if (! ($e.tree instanceof AbstractLValue)) {
                throw new InvalidLValue(this, $ctx);
            }
        }
        EQUALS e2=assign_expr {
            assert($e.tree != null);
            assert($e2.tree != null);
            $tree = new Assign( (AbstractLValue) $e.tree,$e2.tree);
            setLocation($tree,$EQUALS);
        }
    | /* epsilon */ {
            assert($e.tree != null);
            $tree = $e.tree;
        }
      )
    ;

or_expr returns[AbstractExpr tree]
    : e=and_expr {
            assert($e.tree != null);
            $tree = $e.tree;
        }
    | e1=or_expr OR e2=and_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
            $tree = new Or($e1.tree,$e2.tree);
            setLocation($tree, $OR);
       }
    ;

and_expr returns[AbstractExpr tree]
    : e=eq_neq_expr {
            assert($e.tree != null);
            $tree = $e.tree;
        }
    |  e1=and_expr AND e2=eq_neq_expr {
            assert($e1.tree != null);                         
            assert($e2.tree != null);
            $tree = new And($e1.tree,$e2.tree);
            setLocation($tree, $AND);
        }
    ;

eq_neq_expr returns[AbstractExpr tree]
    : e=inequality_expr {
            assert($e.tree != null);
            $tree = $e.tree;
        }
    | e1=eq_neq_expr EQEQ e2=inequality_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
            $tree = new Equals($e1.tree,$e2.tree);
            setLocation($tree, $EQEQ);            
        }
    | e1=eq_neq_expr NEQ e2=inequality_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
            $tree = new NotEquals($e1.tree,$e2.tree);
            setLocation($tree, $NEQ);
        }
    ;

inequality_expr returns[AbstractExpr tree]
    : e=sum_expr {
            assert($e.tree != null);
            $tree = $e.tree;
        }
    | e1=inequality_expr LEQ e2=sum_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
            $tree = new LowerOrEqual($e1.tree,$e2.tree);
            setLocation($tree, $LEQ);
        }
    | e1=inequality_expr GEQ e2=sum_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
            $tree = new GreaterOrEqual($e1.tree,$e2.tree);
            setLocation($tree, $GEQ);
        }
    | e1=inequality_expr GT e2=sum_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
            $tree = new Greater($e1.tree,$e2.tree);
            setLocation($tree, $GT);
        }
    | e1=inequality_expr LT e2=sum_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
            $tree = new Lower($e1.tree,$e2.tree);
            setLocation($tree, $LT);
        }
    | e1=inequality_expr INSTANCEOF type {
            assert($e1.tree != null);
            assert($type.tree != null);
            $tree = new InstanceOf($e1.tree, $type.tree);
            setLocation($tree, $INSTANCEOF);
        }
    ;


sum_expr returns[AbstractExpr tree]
    : e=mult_expr {
            assert($e.tree != null);
            $tree = $e.tree;
        }
    | e1=sum_expr PLUS e2=mult_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
            $tree = new Plus($e1.tree,$e2.tree);
            setLocation($tree, $PLUS);
        }
    | e1=sum_expr MINUS e2=mult_expr {
            assert($e1.tree != null);
            assert($e2.tree != null);
            $tree = new Minus($e1.tree,$e2.tree);
            setLocation($tree, $MINUS);
        }
    ;

mult_expr returns[AbstractExpr tree]
    : e=unary_expr {
            assert($e.tree != null);
            $tree = $e.tree;
        }
    | e1=mult_expr TIMES e2=unary_expr {
            assert($e1.tree != null);                                         
            assert($e2.tree != null);
            $tree = new Multiply($e1.tree,$e2.tree);
            setLocation($tree, $TIMES);
        }
    | e1=mult_expr SLASH e2=unary_expr {
            assert($e1.tree != null);                                         
            assert($e2.tree != null);
            $tree = new Divide($e1.tree,$e2.tree);
            setLocation($tree, $SLASH);
        }
    | e1=mult_expr PERCENT e2=unary_expr {
            assert($e1.tree != null);                                                                          
            assert($e2.tree != null);
            $tree = new Modulo($e1.tree,$e2.tree);
            setLocation($tree, $PERCENT);
        }
    ;

unary_expr returns[AbstractExpr tree]
    : op=MINUS e=unary_expr {
            assert($e.tree != null);
            $tree = new UnaryMinus($e.tree);
            setLocation($tree, $op);
        }
    | op=EXCLAM e=unary_expr {
            assert($e.tree != null);
            $tree = new Not($e.tree);
            setLocation($tree, $op);
        }
    | select_expr {
            assert($select_expr.tree != null);
            $tree = $select_expr.tree;

        }
    ;

select_expr returns[AbstractExpr tree]
    : e=primary_expr {
            assert($e.tree != null);
            $tree = $e.tree;
        }
    | e1=select_expr DOT i=ident {  // Dot il faut cree un fichier .java dans tree 
            assert($e1.tree != null);
            assert($i.tree != null);
            $tree = new Selection($e1.tree, $i.tree);
            setLocation($tree, $DOT);
            
        }
        (o=OPARENT args=list_expr CPARENT {
            // we matched "e1.i(args)"
            assert($args.tree != null);
            $tree = new MethodCall($e1.tree, $i.tree, $args.tree);
            setLocation($tree, $args.start);
        }
        | /* epsilon */ {
            // we matched "e.i"  // Q au prof
            assert($e1.tree != null);
            assert($i.tree != null);
            $tree = new Selection($e1.tree, $i.tree);
            setLocation($tree, $DOT);

        }
        )
    ;

primary_expr returns[AbstractExpr tree]
    : ident {
            assert($ident.tree != null);
            $tree = $ident.tree;
        }
    | m=ident OPARENT args=list_expr CPARENT {
            assert($args.tree != null);
            assert($m.tree != null);
            //ToDo avec objet MethodCall
            $tree = new MethodCall(null, $m.tree, $args.tree);
            setLocation($tree, $m.start);

        }
    | OPARENT expr CPARENT {
            assert($expr.tree != null);
            $tree = $expr.tree;
        }
    | READINT OPARENT CPARENT {
            $tree = new ReadInt();
            setLocation($tree, $READINT);
        }
    | READFLOAT OPARENT CPARENT {
            $tree = new ReadFloat();
            setLocation($tree, $READFLOAT);
        }
    | NEW ident OPARENT CPARENT {
            assert($ident.tree != null);
            $tree = new New($ident.tree);
            setLocation($tree, $NEW);
        }
    | OPARENT type CPARENT OPARENT expr CPARENT {
            assert($type.tree != null);
            assert($expr.tree != null);
            $tree = new Cast($type.tree, $expr.tree);
            setLocation($tree, $type.start);
        }
    | literal {
            assert($literal.tree != null);
            $tree = $literal.tree;
            setLocation($tree, $literal.start);
        }
    ;

type returns[AbstractIdentifier tree]
    : ident {
            assert($ident.tree != null);
            $tree = $ident.tree;
        }
    ;

literal returns[AbstractExpr tree]
    : in=INT {
          $tree = new IntLiteral(Integer.parseInt($in.text));
          setLocation($tree,$in);
        }
    | fd=FLOAT {
           $tree = new FloatLiteral(Float.parseFloat($fd.text));
           setLocation($tree,$fd);
        }
    | st=STRING {
            $tree = new StringLiteral($st.text);
            setLocation($tree,$st);
        }
    | blt=TRUE {
           $tree = new BooleanLiteral(true);
           setLocation($tree,$blt);
        }
    | blf=FALSE {
           $tree = new BooleanLiteral(false);
           setLocation($tree,$blf);
        }
    | t=THIS {
           //$tree = new This(new SymbolTable().create("this"));
           $tree = new This(false);
           setLocation($tree, $t);
        }
    | n= NULL {
           $tree = new Null();
           setLocation($tree, $n);
        }
    ;

ident returns[AbstractIdentifier tree]
    : id=IDENT {
        SymbolTable.Symbol s = new SymbolTable().create($id.text);
        $tree = new Identifier(s);
        setLocation($tree, $id);
        }   
    ;

/****     Class related rules     ****/
list_classes returns[ListDeclClass tree]
@init{
        $tree = new ListDeclClass();
    }
    :
      (c1=class_decl {
        assert($c1.tree != null);
        $tree.add($c1.tree);
        }
      )*
    ;

class_decl returns[AbstractDeclClass tree]
    : CLASS name=ident superclass=class_extension[$CLASS] OBRACE class_body CBRACE {
        assert($name.tree != null);
        assert($superclass.tree != null);
        assert($class_body.lst_decl_field != null);
        assert($class_body.lst_decl_method != null);
        $tree = new DeclClass($name.tree, $superclass.tree, $class_body.lst_decl_method, $class_body.lst_decl_field);
        setLocation($tree, $CLASS);
    }
    ;

class_extension[Token s] returns[AbstractIdentifier tree]
    : EXTENDS ident {
        assert($ident.tree != null);
        $tree = $ident.tree;
        setLocation($tree, $ident.start);
        }
    | /* epsilon */ {
        // SHOULD return tree dyal class Object
        $tree = new Identifier(new SymbolTable().create("Object"));
        setLocation($tree, $s);
        }
    ;

class_body returns[ListDeclField lst_decl_field, ListDeclMethod lst_decl_method]
@init {
    $lst_decl_field = new ListDeclField();
    $lst_decl_method = new ListDeclMethod();
}
    : (m=decl_method {
        assert($m.tree != null);
        $lst_decl_method.add($m.tree);
      }
      | decl_field_set[$lst_decl_field]
      )*
    ;

decl_field_set[ListDeclField list_field]
    : v=visibility t=type list_decl_field[$v.tree, $list_field, $type.tree]
      SEMI
    ;

visibility returns[Visibility tree]
    : /* epsilon */ {
        $tree = Visibility.PUBLIC;
        }
    | PROTECTED {
        $tree = Visibility.PROTECTED;
        }
    ;



list_decl_field[Visibility v, ListDeclField l, AbstractIdentifier t]
    : dv1=decl_field[$v, $l, $t]{
    }
        (COMMA dv2=decl_field[$v, $l, $t]{
        }
      )*
    ;

decl_field[Visibility v, ListDeclField l, AbstractIdentifier t]
@init {
    AbstractInitialization initial;
    AbstractDeclField field;
}
    : i=ident {
        assert($i.tree != null);
        initial = new NoInitialization();
        }
      (EQUALS e=expr {
        assert($e.tree != null);
        initial = new Initialization($e.tree);
        setLocation(initial, $EQUALS);
        }
      )? {
        field = new DeclField($v, $t, $i.tree, initial);
        $l.add(field);
        setLocation(field, $i.start);        
        }
    ;

decl_method returns[AbstractDeclMethod tree]
@init {
        AbstractMethodBody methodBody;
}
    : type ident OPARENT params=list_params CPARENT (block {
        assert($block.decls != null);
        assert($block.insts != null);
        methodBody = new MethodBody($block.decls, $block.insts);
        setLocation(methodBody, $block.start);
        }
      | ASM OPARENT code=multi_line_string CPARENT SEMI {
        assert($code.text != null);
        assert($code.location != null);
        AbstractStringLiteral methodAsmBodyAttribute = new StringLiteral($code.text);
        methodBody = new MethodAsmBody(methodAsmBodyAttribute);
        setLocation(methodBody, $ASM);
        }
      ) {
        assert($type.tree != null);
        assert($ident.tree != null);
        assert($params.tree != null);
        $tree = new DeclMethod($type.tree, $ident.tree, $params.tree, methodBody);
        setLocation($tree, $type.start);
        }
    ;

list_params returns[ListDeclParam tree]
@init {
    $tree = new ListDeclParam();
}
    : (p1=param {
            assert($p1.tree != null);
            $tree.add($p1.tree);
        } 
        (COMMA p2=param {
            assert($p2.tree != null);
            $tree.add($p2.tree);
        }
      )*)?
    ;
    
multi_line_string returns[String text, Location location]
    : s=STRING {
            $text = $s.text;
            $location = tokenLocation($s);
        }
    | s=MULTI_LINE_STRING {
            $text = $s.text;
            $location = tokenLocation($s);
        }
    ;

param returns[AbstractDeclParam tree]
    : type ident {
        assert($type.tree != null);
        assert($ident.tree != null);
        $tree = new DeclParam($type.tree, $ident.tree);
        setLocation($tree, $type.start);
        }
    ;


