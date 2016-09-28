/**
 * A representation of a number lerp expression.
 *
 * @author Arthur Nunes-Harwitt
 */
public class NumExp implements Expression {

    private double num;

    /**
     * Construct a number expression.
     *
     *
     * @param num the double that is the number
     */
    public NumExp(double num){
        this.num = num;
    }

    @Override
    public ANFExp toANF(){
        ANFVarExp rtn = new ANFVarExp();
        return new ANFLetExp(rtn, new ANFConstOp(num), rtn);
    }

    @Override
    public Triple<ANFVarExp, ANFOp, Expression> extract(){
        ANFVarExp exp = new ANFVarExp();
        ANFConstOp co = new ANFConstOp(num);
        Holder h = new Holder(exp);
        return new Triple<>(exp, co , h);

    }

    @Override
    public String toString(){
        return ""+this.num;
    }
}
