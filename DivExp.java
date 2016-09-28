/**
 * A representation of the division lerp expression.
 *
 * @author Arthur Nunes-Harwitt
 */
public class DivExp extends BinaryExp {

    /**
     * Construct an division expression.
     *
     *
     * @param exp1 the Expression that is the first operand of the
     * division expression
     * @param exp2 the Expression that is the second operand of the
     * division expression
     */
    public DivExp(Expression exp1, Expression exp2){
        super(exp1, exp2);
    }

    @Override
    public Triple<ANFVarExp, ANFOp, Expression> extract(){
        Triple<ANFVarExp, ANFOp, Expression> trp;
        if (getExp1() instanceof Holder && getExp2() instanceof Holder){
            ANFVarExp var = new ANFVarExp();
            ANFDivOp op = new ANFDivOp(((Holder) getExp1()).getVar(), ((Holder) getExp2()).getVar());
            return new Triple<>(var, op, new Holder(var));
        }
        else if (getExp1() instanceof Holder){
            trp = getExp2().extract();
            return new Triple<>(trp.first(), trp.second(), new DivExp(getExp1(), trp.third()));
        }
        else {
            trp = getExp1().extract();
            return new Triple<>(trp.first(), trp.second(), new DivExp(trp.third(), getExp2()));
        }
    }

    @Override
    public String toString(){
        return "(/ " + super.toString() + ")";
    }
}
