/**
 * A representation of the negation lerp expression.
 *
 * @author Arthur Nunes-Harwitt
 */
public class NegExp extends UnaryExp {

    /**
     * Construct a negation expression.
     *
     *
     * @param exp the Expression that is the first operand of the
     * negation expression
     */
    public NegExp(Expression exp){
        super(exp);
    }

    @Override
    public Triple<ANFVarExp, ANFOp, Expression> extract(){
        Triple<ANFVarExp, ANFOp, Expression> trp;
        if (getExp() instanceof Holder && getExp() instanceof Holder){
            ANFVarExp var = new ANFVarExp();
            ANFNegOp op = new ANFNegOp(((Holder) getExp()).getVar());
            return new Triple<>(var, op, new Holder(var));
        }
        else if (getExp() instanceof Holder){
            trp = getExp().extract();
            return new Triple<>(trp.first(), trp.second(), new NegExp(getExp()));
        }
        else {
            trp = getExp().extract();
            return new Triple<>(trp.first(), trp.second(), new NegExp(trp.third()));
        }
    }

    @Override
    public String toString(){
        return "(- " + super.toString() + ")";
    }
}
