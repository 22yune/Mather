package com.galen.program.matcher;

/**
 * Created by baogen.zhang on 2019/9/20
 *
 * @author baogen.zhang
 * @date 2019/9/20
 */
public class Rule implements Matcher.Rule{

    private boolean isCombine;

    private boolean reverse;
    private Condition condition;

    private Matcher.Rule ruleA;
    private Matcher.Rule ruleB;
    private boolean isAnd ;

    protected Rule(){
    }

    protected void initSimple(Condition condition,boolean reverse){
        isCombine = false;
        this.reverse = reverse;
        this.condition = condition;
    }

    public Rule(Condition condition,boolean reverse){
        initSimple(condition, reverse);
    }

    public Rule(Matcher.Rule a,Matcher.Rule b,boolean isAnd){
        isCombine = true;
        this.ruleA = a;
        this.ruleB = b;
        this.isAnd = isAnd;
    }

    @Override
    public long apply(Hold hold) {
        return isCombine ? apply4Combine(hold) : apply4base(hold);
    }

    private long apply4base(Hold hold) {
        boolean match = condition.dimension().match(condition.value(),hold.value(condition.dimension()));
        if(match && !reverse){
            return (long) Math.pow(2,condition.dimension().positive());
        }else if(!match && reverse){
            return (long) Math.pow(2,condition.dimension().negative());
        }else {
            return 0;
        }
    }

    private long apply4Combine(Hold hold) {
        if(isAnd){
            long a = ruleA.apply(hold);
            long b = ruleB.apply(hold);
            if(a > 0 && b > 0){
                return ruleA.apply(hold) + ruleB.apply(hold);
            }else {
                return 0;
            }
        }else {
            return Math.max(ruleA.apply(hold) , ruleB.apply(hold));
        }
    }

    @Override
    public Rule not() {
        if(isCombine){
            return new Rule(ruleA.not(),ruleB.not(),!isAnd);
        }else {
            return new Rule(condition,true);
        }
    }

    @Override
    public Rule and(Matcher.Rule rule) {
        return new Rule( this,rule,true);
    }

    @Override
    public Rule or(Matcher.Rule rule) {
        return new Rule( this,rule,false);
    }


}
