/**
 * A representation of the multiplication lerp expression.
 *
 * @author Arthur Nunes-Harwitt
 */
public class MulExp extends BinaryExp {

    /**
     * Construct a multiplication expression.
     *
     *
     * @param exp1 the Expression that is the first operand of the
     * multiplication expression
     * @param exp2 the Expression that is the second operand of the
     * multiplication expression
     */
    public MulExp(Expression exp1, Expression exp2){
        super(exp1, exp2);
    }

    @Override
    public Triple<ANFVarExp, ANFOp, Expression> extract(){
        Triple<ANFVarExp, ANFOp, Expression> trp;
        if (getExp1() instanceof Holder && getExp2() instanceof Holder){
            ANFVarExp var = new ANFVarExp();
            ANFMulOp op = new ANFMulOp(((Holder) getExp1()).getVar(), ((Holder) getExp2()).getVar());
            return new Triple<>(var, op, new Holder(var));
        }
        else if (getExp1() instanceof Holder){
            trp = getExp2().extract();
            return new Triple<>(trp.first(), trp.second(), new MulExp(getExp1(), trp.third()));
        }
        else {
            trp = getExp1().extract();
            return new Triple<>(trp.first(), trp.second(), new MulExp(trp.third(), getExp2()));
        }
    }

    @Override
    public String toString(){
        return "(* " + super.toString() + ")";
    }
}
