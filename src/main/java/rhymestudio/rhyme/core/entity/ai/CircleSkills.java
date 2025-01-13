package rhymestudio.rhyme.core.entity.ai;

import rhymestudio.rhyme.core.entity.AbstractPlant;

import java.util.ArrayList;
import java.util.List;

public class CircleSkills<T extends AbstractPlant> {
    public T owner;
    protected final List<CircleSkill<T>> bossSkills = new ArrayList<>();


    public int tick = 0;
    public int index = 0;
    public boolean ifStateInit = false;

    public CircleSkills(T owner){ this.owner = owner;}

    public int count(){return bossSkills.size();};



    public boolean pushSkill(CircleSkill<T> skill){
        bossSkills.add(skill);

        if(bossSkills.size()==1) tick = 0;
        return true;
    }


    public void tick(){
        if(bossSkills.isEmpty()) return ;
        this.tick++;

        if(bossSkills.get(index).stateTick !=null) {
            bossSkills.get(index).stateTick.accept(owner);
        }
        if(bossSkills.isEmpty())return;
        if(bossSkills.get(index).timeContinue < tick) forceEnd();
    }


    /** 强制结束当前状态 **/
    public void forceEnd(){
        tick = 0;
        int lastIndex = index;
        index = (index +1) % bossSkills.size();

        //状态结束
        if(bossSkills.get(lastIndex).stateOver!=null) bossSkills.get(lastIndex).stateOver.accept(owner);


        owner.getEntityData().set(AbstractPlant.DATA_CAFE_POSE_NAME, getCurSkillName());

    }
    /** 强制跳转状态 **/
    public void forceStartIndex(int index){
        tick = 0;

        this.index = index;
        //初次进入状态
        if(bossSkills.get(index).stateInit!=null) bossSkills.get(index).stateInit.accept(owner);

        owner.getEntityData().set(AbstractPlant.DATA_CAFE_POSE_NAME, getCurSkillName());
        owner.animState.playAnim(bossSkills.get(index).name,owner.tickCount);
    }

    public void forceStart(CircleSkill<T> skill){
        int index = bossSkills.indexOf(skill);
        if(index!=-1) forceStartIndex(index);
    }

    public void forceStart(String skill){
        for(CircleSkill<T> s:bossSkills){
            if(s.name.equals(skill)){
                forceStart(s);
                return;
            }
        }
    }

    /** tick == triggerTime **/
    public boolean canTrigger(){
        if(bossSkills.isEmpty()) return false;
        return bossSkills.get(index).timeTrigger == this.tick;
    }
    /** tick > triggerTime **/
    public boolean canContinue(){
        if(bossSkills.isEmpty()) return false;
        return bossSkills.get(index).timeTrigger < this.tick;
    }
    public CircleSkill<T> getCurSkill(){
        if(!bossSkills.isEmpty())
            return bossSkills.get(index);
        return null;
    }
    public String getCurSkillName(){
        if(!bossSkills.isEmpty())
            return bossSkills.get(index).name;
        return "";
    }
    public void removeSkill(CircleSkill<T> skill){
        bossSkills.remove(skill);
    }
    public int getCurAnimFullTick(){
        if(!bossSkills.isEmpty())
            return bossSkills.get(index).timeContinue;
        return -1;
    }




}
