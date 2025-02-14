package rhymestudio.rhyme.core.entity.ai;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.Mob;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CircleMobSkills<T extends Mob> {
    public T owner;
    protected final List<CircleMobSkill<T>> bossSkills = new ArrayList<>();

    public Map<String, Integer> str2intMap = new HashMap<>();

    public int tick = 0;
    public int index = 0;
    public boolean ifStateInit = false;

    EntityDataAccessor<String> DATA_CAFE_POSE_NAME ;
    public CircleMobSkills(T owner, EntityDataAccessor<String> DATA_CAFE_POSE_NAME){
        this.owner = owner;
        this.DATA_CAFE_POSE_NAME = DATA_CAFE_POSE_NAME;
    }

    public int count(){return bossSkills.size();};



    public boolean pushSkill(CircleMobSkill<T> skill){
        bossSkills.add(skill);
        str2intMap.put(skill.name, bossSkills.size()-1);
        if(bossSkills.size()==1) tick = 0;
        return true;
    }

    public boolean changeSkill(CircleMobSkill<T> skill){
        if(str2intMap.containsKey(skill.name)){
            int index = str2intMap.get(skill.name);
            bossSkills.set(index, skill);
            return true;
        }
        return false;
    }

    public void playSkill(String skillName){
        if(str2intMap.containsKey(skillName)){
            int index = str2intMap.get(skillName);
            forceStartIndex(index);
        }
    }


    public void tick(){
        if(owner.level().isClientSide()) return;
        if(bossSkills.isEmpty()) return ;
        this.tick++;

        if(bossSkills.get(index).stateTick !=null) {
            bossSkills.get(index).stateTick.accept(owner);
        }
        if(bossSkills.isEmpty())return;
        if(bossSkills.get(index).timeContinue < tick)
            forceEnd();
    }


    /** 强制结束当前状态 **/
    public void forceEnd(){
        tick = 0;
        int lastIndex = index;
        index = (index +1) % bossSkills.size();

        //状态结束
        if(bossSkills.get(lastIndex).stateOver!=null) bossSkills.get(lastIndex).stateOver.accept(owner);

        owner.getEntityData().set(DATA_CAFE_POSE_NAME, getCurSkillName());
    }
    /** 强制跳转状态 **/
    public void forceStartIndex(int index){
        tick = 0;

        this.index = index;
        //初次进入状态
        if(bossSkills.get(index).stateInit!=null) bossSkills.get(index).stateInit.accept(owner);

        owner.getEntityData().set(DATA_CAFE_POSE_NAME, getCurSkillName());
    }

    public void forceStart(CircleMobSkill<T> skill){
        int index = bossSkills.indexOf(skill);
        if(index!=-1) forceStartIndex(index);
    }

    public void forceStart(String skill){
        for(CircleMobSkill<T> s:bossSkills){
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
    public CircleMobSkill<T> getCurSkill(){
        if(!bossSkills.isEmpty())
            return bossSkills.get(index);
        return null;
    }
    public String getCurSkillName(){
        if(!bossSkills.isEmpty())
            return bossSkills.get(index).name;
        return "";
    }
    public void removeSkill(CircleMobSkill<T> skill){
        bossSkills.remove(skill);
    }
    public int getCurAnimFullTick(){
        if(!bossSkills.isEmpty())
            return bossSkills.get(index).timeContinue;
        return -1;
    }




}
