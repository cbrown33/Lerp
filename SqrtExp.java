/**
 * A representation of the square root lerp expression.
 *
 * @author Arthur Nunes-Harwitt
 */
public class SqrtExp extends UnaryExp {

    /**
     * Construct a square root expression.
     *
     *
     * @param exp the Expression that is the first operand of the
     * square root expression
     */
    public SqrtExp(Expression exp){
        super(exp);
    }

    @Override
    public Triple<ANFVarExp, ANFOp, Expression> extract(){
        Triple<ANFVarExp, ANFOp, Expression> trp;
        if (getExp() instanceof Holder && getExp() instanceof Holder){
            ANFVarExp var = new ANFVarExp();
            ANFSqrtOp op = new ANFSqrtOp(((Holder) getExp()).getVar());
            return new Triple<>(var, op, new Holder(var));
        }
        else if (getExp() instanceof Holder){
            trp = getExp().extract();
            return new Triple<>(trp.first(), trp.second(), new SqrtExp(getExp()));
        }
        else {
            trp = getExp().extract();
            return new Triple<>(trp.first(), trp.second(), new SqrtExp(trp.third()));
        }
    }

    @Override
    public String toString(){
        return "(sqrt " + super.toString() + ")";
    }
}
